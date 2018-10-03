package code4ants.forgedmocks.http.model;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpRequest {
    private String uri;
    private HttpMethod method;
    private HttpProtocol protocol;
    private Map<String, String> headers = new LinkedHashMap<>();
    private byte[] body;

    public HttpRequest() {
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public HttpProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(HttpProtocol protocol) {
        this.protocol = protocol;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public byte[] getBody() {
        return body == null ? null : Arrays.copyOf(body, body.length);
    }

    public void setBody(byte[] body) {
        this.body = body == null ? null : Arrays.copyOf(body, body.length);
    }

    public void addHeader(String name, String value) {
        // headers are case insensitive
        headers.put(name.toLowerCase(), value.trim());
    }

    public String getHeader(String name) {
        return headers.getOrDefault(name.toLowerCase(), null);
    }

    public boolean shouldHaveBody() {
        return getHeader("content-length") != null || getHeader("transfer-encoding") != null;
    }

    @Override
    public String toString() {
        return "HttpRequest{" +
                "uri='" + uri + '\'' +
                ", method=" + method +
                ", protocol=" + protocol +
                ", headers=" + headers +
                ", body=" + Arrays.toString(body) +
                '}';
    }
}
