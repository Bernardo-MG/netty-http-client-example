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

import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;

import java.io.PrintWriter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.bernardomg.example.netty.http.cli.TransactionPrinterListener;
import com.bernardomg.example.netty.http.client.ReactorNettyHttpClient;
import com.github.tomakehurst.wiremock.junit5.WireMockRuntimeInfo;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;

@WireMockTest
@DisplayName("ReactorNettyHttpClient")
public final class ITReactorNettyHttpClientPost {

    private static final String LOCALHOST = "localhost";

    private static final String ROUTE     = "/";

    @Test
    @DisplayName("Verify a post request is sent correctly")
    public final void testPost(final WireMockRuntimeInfo wmRuntimeInfo) {
        final ReactorNettyHttpClient client;
        final String                 body;

        // GIVEN
        body = "abc";

        stubFor(post(urlEqualTo(ROUTE)).withHost(equalTo(LOCALHOST))
            .willReturn(ok("ack")));

        client = getClient(wmRuntimeInfo.getHttpPort());

        client.connect();

        // WHEN
        client.post(body);
        // TODO: change for something better
        try {
            Thread.sleep(2000);
        } catch (final InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // THEN
        verify(postRequestedFor(urlEqualTo(ROUTE)).withRequestBody(containing(body)));
    }

    private final ReactorNettyHttpClient getClient(final int port) {
        return new ReactorNettyHttpClient(LOCALHOST, port,
            new TransactionPrinterListener(LOCALHOST, port, new PrintWriter(System.out)));
    }

}
