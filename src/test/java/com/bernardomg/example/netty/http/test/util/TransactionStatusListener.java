
package com.bernardomg.example.netty.http.test.util;

import com.bernardomg.example.netty.http.client.TransactionListener;

public final class TransactionStatusListener implements TransactionListener {

    private boolean received = false;

    private boolean sent     = false;

    public final boolean hasReceived() {
        return received;
    }

    public final boolean hasSent() {
        return sent;
    }

    @Override
    public final void onReceive(final String message) {
        received = true;
    }

    @Override
    public final void onSend(final String message) {
        sent = true;
    }

    @Override
    public final void onStart() {}

    @Override
    public final void onStop() {}

}
