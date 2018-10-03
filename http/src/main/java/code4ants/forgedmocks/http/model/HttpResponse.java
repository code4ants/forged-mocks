package code4ants.forgedmocks.http.model;

import org.apache.commons.codec.Charsets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class HttpResponse {

    private ByteArrayOutputStream body = new ByteArrayOutputStream();
    private HttpStatus httpStatus = HttpStatus.NOT_IMPLEMENTED;
    private Map<String, String> headers = new LinkedHashMap<>();

    public HttpResponse body(String content) {
        try {
            // Use a monospace encoding here in order to get the bytes.
            // The semantic of the bytes will be provided in the Content-Type header
            body.write(content.getBytes("US-ASCII"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void end(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpResponse header(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public HttpStatus getStatusCode() {
        return httpStatus;
    }

    public byte[] getBodyBytes() {
        return body.toString().getBytes(Charsets.UTF_8);
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "body=" + body +
                ", httpStatus=" + httpStatus +
                ", headers=" + headers +
                '}';
    }
}
