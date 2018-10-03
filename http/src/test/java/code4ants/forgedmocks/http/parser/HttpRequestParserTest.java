package code4ants.forgedmocks.http.parser;

import code4ants.forgedmocks.http.model.HttpProtocol;
import code4ants.forgedmocks.http.model.HttpRequest;
import org.junit.Test;

import java.io.ByteArrayInputStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsNull.nullValue;

public class HttpRequestParserTest {

    @Test
    public void testParseRequest_noBody() throws Exception {
        String requestAsString = "GET /uri HTTP/1.1\n" +
                "Host: forgedmocks.code4ants.org\n" +
                "Accept-Language: en\n\n";
        ByteArrayInputStream input = new ByteArrayInputStream(requestAsString.getBytes("US-ASCII"));

        HttpRequest request = new HttpRequestParser(input).parse();

        assertThat(request, is(not(nullValue())));
        assertThat(request.getUri(), is(equalTo("/uri")));
        assertThat(request.getProtocol(), is(equalTo(HttpProtocol.HTTP_1_1)));
        assertThat(request.getBody(), is(nullValue()));
        assertThat(request.getHeader("host"), is(equalTo("forgedmocks.code4ants.org")));
        assertThat(request.getHeader("accept-language"), is(equalTo("en")));
    }

    @Test
    public void testParseRequest_withBody() throws Exception {
        String requestAsString = "POST /contact_form.php HTTP/1.1\n" +
                "Host: developer.mozilla.org\n" +
                "Content-Length: 64\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "\n" +
                "name=Joe%20User&request=Send%20me%20one%20of%20your%20catalogue\n";
        ByteArrayInputStream input = new ByteArrayInputStream(requestAsString.getBytes("US-ASCII"));

        HttpRequest request = new HttpRequestParser(input).parse();

        assertThat(request, is(not(nullValue())));
        assertThat(request.getUri(), is(equalTo("/contact_form.php")));
        assertThat(request.getProtocol(), is(equalTo(HttpProtocol.HTTP_1_1)));
        assertThat(request.getBody().length, is(64));
        assertThat(request.getHeader("host"), is(equalTo("developer.mozilla.org")));
        assertThat(request.getHeader("content-length"), is(equalTo("64")));
    }

}