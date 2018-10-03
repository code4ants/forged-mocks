package code4ants.forgedmocks.http.parser;

import java.io.ByteArrayOutputStream;

public class RegularBodyReader implements BodyReader {
    private int desiredLength;
    private ByteArrayOutputStream baos = new ByteArrayOutputStream();

    public RegularBodyReader(int contentLength) {
        this.desiredLength = contentLength;
    }

    @Override
    public boolean isComplete() {
        return baos.size() == desiredLength;
    }

    @Override
    public byte[] getBytes() {
        return baos.toByteArray();
    }

    @Override
    public void offer(int readChar) {
        baos.write(readChar);
    }
}
