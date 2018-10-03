package code4ants.forgedmocks.http;

import code4ants.forgedmocks.http.model.HttpStatus;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class HttpServerMockTest {

    @Test
    public void testReceiveCorrectStatus() throws Exception {
        try (HttpServerMock server = new HttpServerMock(new PortRange(20000, 30000))) {
            server.handle("/no-content-endpoint", (request, response) -> response.end(HttpStatus.NO_CONTENT));

            HttpResponse<String> response = Unirest.get(server.getUrl("/no-content-endpoint")).asString();

            assertThat(response.getStatus(), is(equalTo(HttpStatus.NO_CONTENT.getCode())));
            assertThat(response.getBody(), is(nullValue()));
        }
        System.out.println("End of test 1");
    }

    @Test
    public void testReceiveCorrectBody() throws Exception {
        try (HttpServerMock server = new HttpServerMock(new PortRange(20000, 30000))) {
            // given
            server.handle("/content-endpoint",
                    (request, response) ->
                            response.body("This be content!")
                                    .end(HttpStatus.OK));

            // when
            HttpResponse<String> response = Unirest.get(server.getUrl("/content-endpoint")).asString();

            // then
            assertThat(response.getStatus(), is(equalTo(HttpStatus.OK.getCode())));
            assertThat(response.getBody(), is(equalTo("This be content!")));
        }
    }

    @Test
    public void testNotFoundIsReturnedOnUnhandledEndpoint() throws Exception {
        try (HttpServerMock server = new HttpServerMock(new PortRange(20000, 30000))) {
            // when
            HttpResponse<String> response = Unirest.get(server.getUrl("/not-existent-endpoint")).asString();
            // then
            assertThat(response.getStatus(), is(equalTo(HttpStatus.NOT_FOUND.getCode())));
        }
    }
}