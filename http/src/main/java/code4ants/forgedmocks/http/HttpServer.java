package code4ants.forgedmocks.http;


import code4ants.forgedmocks.http.model.HttpRequest;
import code4ants.forgedmocks.http.model.HttpResponse;
import code4ants.forgedmocks.http.model.HttpStatus;
import code4ants.forgedmocks.http.parser.HttpRequestParser;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class HttpServer implements AutoCloseable {

    private int serverPort;
    private Map<String, HttpRequestHandler> handlerMap = new LinkedHashMap<>();
    private volatile ServerSocket serverSocket;
    private ServerThread serverThread;
    private volatile boolean shouldStop = false;

    public HttpServer(PortRange portRange) {
        serverPort = listen(portRange);
    }

    public HttpServer handle(String uriPattern, HttpRequestHandler handler) {
        handlerMap.put(uriPattern, handler);
        return this;
    }

    public int getPort() {
        return serverPort;
    }

    public String getUrl(String uri) {
        return "http://localhost:" + serverPort + (uri == null ? "" : uri);
    }

    @Override
    public void close() throws Exception {
        if (serverThread != null) {
            System.out.println("Stopping server thread");
            serverThread.gracefulShutdown();
        }

        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Initializes the TCP server socket by selecting an available port and returns it to the client.
     * If the server cannot be initialized, an exception is thrown.
     *
     * @param portRange the range of ports to be used in order to find an available one
     * @return the port on which the server is currently listening
     */
    private int listen(PortRange portRange) {
        int port;
        do {
            port = portRange.nextPort();
        } while (!startListeningOnPort(port));
        return port;
    }

    private boolean startListeningOnPort(int port) {
        try {
            serverSocket = new ServerSocket(port);
            serverThread = new ServerThread(serverSocket, new DefaultMockHandler());

            serverThread.start();
            System.out.println("Started server thread. Listening on port " + port);
            return true;
        } catch (IOException e) {
            // port is taken
            return false;
        }
    }


    private class DefaultMockHandler implements HttpRequestHandler {
        @Override
        public void handle(HttpRequest request, HttpResponse response) {
            Optional<String> handlerPattern = handlerMap.keySet()
                    .stream()
                    .filter(pattern -> uriMatchesPattern(request.getUri(), pattern))
                    .findFirst();

            if (handlerPattern.isPresent()) {
                handlerMap.get(handlerPattern.get()).handle(request, response);
            } else {
                response.end(HttpStatus.NOT_FOUND);
            }
        }

        private boolean uriMatchesPattern(String uri, String pattern) {
            return pattern.equals(uri);
        }
    }

    private class ServerThread extends Thread {

        private final ServerSocket serverSocket;
        private final HttpRequestHandler handler;

        public ServerThread(ServerSocket serverSocket, HttpRequestHandler handler) {
            super("ForgedMocks HTTP listener thread");
            this.serverSocket = serverSocket;
            this.handler = handler;
        }

        private void writeResponseToSocket(HttpResponse response, Socket client) throws IOException {
            BufferedOutputStream bos = new BufferedOutputStream(client.getOutputStream());
            bos.write(("HTTP/1.1 " + response.getStatusCode().getCode() + " " + response.getStatusCode().getMessage() + "\r\n").getBytes("US-ASCII"));
            bos.write("\r\n".getBytes("US-ASCII"));

            byte[] body = response.getBodyBytes();
            bos.write(body);
            bos.flush();
        }

        @Override
        public void run() {
            while (!shouldStop) {
                try {
                    Socket client = serverSocket.accept();

                    HttpRequest request = new HttpRequestParser(client.getInputStream()).parse();
                    HttpResponse response = new HttpResponse();

                    handler.handle(request, response);
                    writeResponseToSocket(response, client);
                    client.close();

                } catch (Exception e) {
                    shouldStop = true;
//                    e.printStackTrace();
                }
            }
        }

        public void gracefulShutdown() {
            shouldStop = true;
        }
    }
}
