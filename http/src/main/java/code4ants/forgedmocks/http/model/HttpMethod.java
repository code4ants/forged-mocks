package code4ants.forgedmocks.http.model;

public enum HttpMethod {
    POST,
    GET,
    PUT,
    PATCH,
    DELETE;

    public static HttpMethod fromString(String name) {
        for (HttpMethod method : values()) {
            if (method.name().equals(name)) {
                return method;
            }
        }
        throw new IllegalArgumentException("Invalid HTTP method name: " + name);
    }
}
