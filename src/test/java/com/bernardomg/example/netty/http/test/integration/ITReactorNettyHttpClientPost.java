/**
 * The MIT License (MIT)
 * <p>
 * Copyright (c) 2023 the original author or authors.
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.bernardomg.example.netty.http.test.integration;

import static com.github.tomakehurst.wiremock.client.WireMock.anyUrl;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;

import org.junit.jupiter.api.Test;

import com.bernardomg.example.netty.http.client.ReactorNettyHttpClient;
import com.bernardomg.example.netty.http.client.TransactionListener;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

import lombok.extern.slf4j.Slf4j;

@WireMockTest(httpPort=8595)
@Slf4j
public final class ITReactorNettyHttpClientPost {

    @Test
    public final void testPost(final WireMockRuntimeInfo wmRuntimeInfo) {
        final ReactorNettyHttpClient client;

        stubFor(post(anyUrl())
//            .withHost(equalTo("http://localhost"))
            .willReturn(ok("ack")));
        
        client = getClient("localhost", 8595);

        client.setWiretap(true);
        client.connect();

        client.post("abc");

        verify(postRequestedFor(urlEqualTo(""))
            .withHeader("Content-Type", equalTo("text/xml")));
    }

    private final ReactorNettyHttpClient getClient(final String url, final int port) {
        return new ReactorNettyHttpClient(url, port, new TransactionListener() {

            @Override
            public void onReceive(final String message) {}

            @Override
            public void onSend(final String message) {
                log.debug("Test send");
            }

            @Override
            public void onStart() {}

            @Override
            public void onStop() {}
        });
    }

}
