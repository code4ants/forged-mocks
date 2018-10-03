package code4ants.forgedmocks.http.parser;

public interface BodyReader {
    boolean isComplete();

    byte[] getBytes();

    void offer(int readChar);
}
