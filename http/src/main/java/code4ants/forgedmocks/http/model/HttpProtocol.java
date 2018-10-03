package code4ants.forgedmocks.http.model;

public enum HttpProtocol {
    HTTP_1_0("HTTP/1.0"),
    HTTP_1_1("HTTP/1.1"),
    HTTP_2_0("HTTP/2.0");

    private String text;

    HttpProtocol(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static HttpProtocol fromString(String text) {
        for (HttpProtocol protocol : values()) {
            if (protocol.getText().equals(text)) {
                return protocol;
            }
        }
        throw new IllegalArgumentException("Unsupported HTTP protocol " + text);
    }
}
