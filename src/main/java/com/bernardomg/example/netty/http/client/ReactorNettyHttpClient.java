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

package com.bernardomg.example.netty.http.client;

import java.util.Objects;
import java.util.function.BiFunction;

import org.reactivestreams.Publisher;

import io.netty.buffer.ByteBuf;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientResponse;

/**
 * Reactor Netty based HTTP client.
 *
 * @author Bernardo Mart&iacute;nez Garrido
 *
 */
@Slf4j
public final class ReactorNettyHttpClient implements Client {

    /**
     * Host for the server to which this client will connect.
     */
    private final String                                                                                   host;

    /**
     * Main client. For sending messages and reacting to responses.
     */
    private HttpClient                                                                                     httpClient;

    /**
     * Transaction listener. Reacts to events during the request.
     */
    private final TransactionListener                                                                      listener;

    /**
     * Port for the server to which this client will connect.
     */
    private final Integer                                                                                  port;

    /**
     * IO response handler for the client.
     */
    private final BiFunction<? super HttpClientResponse, ? super ByteBufFlux, ? extends Publisher<String>> responseHandler;

    /**
     * Wiretap flag.
     */
    @Setter
    @NonNull
    private Boolean                                                                                        wiretap = false;

    public ReactorNettyHttpClient(final String hst, final Integer prt, final TransactionListener lst) {
        super();

        port = Objects.requireNonNull(prt);
        host = Objects.requireNonNull(hst);
        listener = Objects.requireNonNull(lst);

        responseHandler = new ResponseToListenerHandler(listener);
    }

    @Override
    public final void connect() {
        log.trace("Starting client");

        log.debug("Connecting to {}:{}", host, port);

        listener.onStart();

        httpClient = HttpClient.create()
            // Wiretap
            .wiretap(wiretap)
            // Sets connection
            .host(host)
            .port(port);

        log.trace("Started client");
    }

    @Override
    public final void post(final String message) {
        final Publisher<? extends ByteBuf> body;

        log.debug("Sending {}", message);

        // Request data
        body = ByteBufFlux.fromString(Mono.just(message));

        // Sends request
        httpClient.post()
            .send(body)
            .response(responseHandler)
            // Subscribe to run
            .subscribe();
    }

}
