package code4ants.forgedmocks.http.parser;

import code4ants.forgedmocks.http.model.HttpMethod;
import code4ants.forgedmocks.http.model.HttpProtocol;
import code4ants.forgedmocks.http.model.HttpRequest;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class HttpRequestParser {

    enum ParserState {
        StartLine,
        Headers,
        Body
    }

    private static final char LF = '\n';
    private final InputStream input;
    private HttpRequest request = new HttpRequest();
    private BodyReader bodyReader;

    public HttpRequestParser(InputStream input) {
        this.input = input;
    }

    public HttpRequest parse() throws Exception {
        ParserState state = ParserState.StartLine;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int readChar;
        boolean continueParsing = true;
//        while (input.available() > 0 && ((readChar = input.read()) != -1) && continueParsing) {
        while (continueParsing && ((readChar = input.read()) != -1)) {

            switch (state) {
                case StartLine:
                    baos.write(readChar);
                    if (readChar == LF) {
                        String line = baos.toString("US-ASCII").trim();
                        baos.reset();
                        if (!line.isEmpty()) {
                            interpretStartLine(line);
                            state = ParserState.Headers;
                        } else {
                            // https://tools.ietf.org/html/rfc7230#section-3.5 states:
                            //   "In the interest of robustness, a server that is expecting to receive
                            //   and parse a request-line SHOULD ignore at least one empty line (CRLF)
                            //   received prior to the request-line."
                            continue;
                        }
                    }
                    break;
                case Headers:
                    baos.write(readChar);
                    if (readChar == LF) {
                        String line = baos.toString("US-ASCII").trim();
                        baos.reset();
                        if (!line.isEmpty()) {
                            interpretHeaderLine(line);
                        } else {
                            if (request.shouldHaveBody()) {
                                if (request.getHeader("content-length") != null) {
                                    bodyReader = new RegularBodyReader(Integer.valueOf(request.getHeader("content-length")));
                                    state = ParserState.Body;
                                } else if (isSupportedTransferEncoding(request.getHeader("transfer-encoding"))) {
                                    // TODO: support different body readers for different transfer encoders
                                    bodyReader = new ChunkedBodyReader();
                                    state = ParserState.Body;
                                } else {
                                    throw new UnsupportedOperationException("Unsupported transfer encoding");
                                }
                            } else {
                                continueParsing = false;
                            }
                        }
                    }
                    break;
                case Body:
                    bodyReader.offer(readChar);
                    if (bodyReader.isComplete()) {
                        request.setBody(bodyReader.getBytes());
                        continueParsing = false;
                    }
                    break;
            }
        }

        if (request.shouldHaveBody() && !bodyReader.isComplete()) {
            throw new IllegalStateException("Invalid request body");
        }

        return request;
    }

    private boolean isSupportedTransferEncoding(String transferEncodingValue) {
        return transferEncodingValue.trim().equalsIgnoreCase("chunked");
    }

    private void interpretHeaderLine(String line) {
        String[] pieces = line.split("[:]", 2);
        if (pieces.length != 2) {
            throw new IllegalArgumentException("Header line invalid");
        }

        request.addHeader(pieces[0], pieces[1]);
    }

    private void interpretStartLine(String line) {
        String[] pieces = line.split("[ ]");
        if (pieces.length != 3) {
            throw new IllegalArgumentException("Start line invalid");
        }

        request.setMethod(HttpMethod.fromString(pieces[0]));
        request.setUri(pieces[1]);
        request.setProtocol(HttpProtocol.fromString(pieces[2]));
    }
}
