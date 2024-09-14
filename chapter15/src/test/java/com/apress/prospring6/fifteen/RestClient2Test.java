package com.apress.prospring6.fifteen;

import com.apress.prospring6.fifteen.entities.Singer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

//@Disabled("Start Apache Tomcat and deploy the app first, then comment this line, then run each test manually and notice the result!")
public class RestClient2Test {
    final Logger LOGGER = LoggerFactory.getLogger(RestClient2Test.class);
    private static final String URI_SINGER2_ROOT = "http://localhost:8080/ch15/singer2/";

    RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testPositionFindById() throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        RequestEntity<HttpHeaders> req = new RequestEntity<>(headers, HttpMethod.GET, new URI(URI_SINGER2_ROOT + 1));
        LOGGER.info("--> Testing retrieve a singer by id : 1");
        ResponseEntity<Singer> response = restTemplate.exchange(req, Singer.class);
        assertAll("findById",
                () -> assertEquals(HttpStatus.OK, response.getStatusCode()),
                () -> assertTrue(Objects.requireNonNull(response.getHeaders().get(HttpHeaders.CONTENT_TYPE)).contains(MediaType.APPLICATION_JSON_UTF8_VALUE)),
                () -> assertNotNull(response.getBody()),
                () -> assertEquals(Singer.class, response.getBody().getClass())
        );
    }

    @Test
    public void testNegativeFindById() throws URISyntaxException {
        LOGGER.info("--> Testing retrieved a singer by id : 99");
        RequestEntity<HttpHeaders> req = new RequestEntity<>(HttpMethod.GET, new URI(URI_SINGER2_ROOT + 99));

        assertThrowsExactly(HttpClientErrorException.NotFound.class, () -> restTemplate.exchange(req, HttpStatus.class));
    }
}
