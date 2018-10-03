package code4ants.forgedmocks.http;

import code4ants.forgedmocks.http.model.HttpRequest;
import code4ants.forgedmocks.http.model.HttpResponse;

@FunctionalInterface
public interface MockHandler {
    void handle(HttpRequest request, HttpResponse response);
}
