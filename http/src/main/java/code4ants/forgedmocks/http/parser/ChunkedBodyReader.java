package code4ants.forgedmocks.http.parser;

import java.io.ByteArrayOutputStream;

public class ChunkedBodyReader implements BodyReader {

    private ByteArrayOutputStream baos = new ByteArrayOutputStream();

    @Override
    public boolean isComplete() {
        return false;
    }

    @Override
    public byte[] getBytes() {
        return new byte[0];
    }

    @Override
    public void offer(int readChar) {

    }
}
