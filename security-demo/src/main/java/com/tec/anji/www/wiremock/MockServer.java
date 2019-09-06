package com.tec.anji.www.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.removeAllMappings;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

/**
 * TODO see WireMock site
 */
public class MockServer {

    public static void main(String[] args) {
        configureFor(8090);
        removeAllMappings();

        String content = "{\"id\":\"1\",\"username\":\"jojo\",\"password\":null}";
        stubFor(get(urlEqualTo("/user/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody(content)));
    }
}
