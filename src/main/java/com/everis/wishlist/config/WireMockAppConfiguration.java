package com.everis.wishlist.config;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.any;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import org.springframework.stereotype.Component;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

@Component
public class WireMockAppConfiguration {

	private static final String STUBS_FOLDER = "wiremock";

    public WireMockAppConfiguration() {
        WireMockServer server = new WireMockServer(WireMockConfiguration.options().port(8081).usingFilesUnderClasspath(STUBS_FOLDER));

        server.stubFor(any(urlEqualTo("/product/1")).willReturn(aResponse().withBodyFile(("product1.json"))));
        server.stubFor(any(urlEqualTo("/product/2")).willReturn(aResponse().withBodyFile(("product2.json"))));
        server.stubFor(any(urlEqualTo("/product/3")).willReturn(aResponse().withBodyFile(("product3.json"))));
        server.stubFor(any(urlEqualTo("/product/4")).willReturn(aResponse().withBodyFile(("product4.json"))));
        server.stubFor(any(urlEqualTo("/product/5")).willReturn(aResponse().withBodyFile(("product5.json"))));
        server.stubFor(any(urlEqualTo("/product/6")).willReturn(aResponse().withBodyFile(("product6.json"))));
        server.stubFor(any(urlEqualTo("/product/7")).willReturn(aResponse().withBodyFile(("product7.json"))));
        server.stubFor(any(urlEqualTo("/product/8")).willReturn(aResponse().withBodyFile(("product8.json"))));
        server.stubFor(any(urlEqualTo("/product/9")).willReturn(aResponse().withBodyFile(("product9.json"))));
        server.stubFor(any(urlEqualTo("/product/10")).willReturn(aResponse().withBodyFile(("product10.json"))));
        server.stubFor(any(urlEqualTo("/product/11")).willReturn(aResponse().withBodyFile(("product11.json"))));
        server.stubFor(any(urlEqualTo("/product/12")).willReturn(aResponse().withBodyFile(("product12.json"))));
        server.stubFor(any(urlEqualTo("/product/13")).willReturn(aResponse().withBodyFile(("product13.json"))));
        server.stubFor(any(urlEqualTo("/product/14")).willReturn(aResponse().withBodyFile(("product14.json"))));
        server.stubFor(any(urlEqualTo("/product/15")).willReturn(aResponse().withBodyFile(("product15.json"))));


        server.start();
    }

}
