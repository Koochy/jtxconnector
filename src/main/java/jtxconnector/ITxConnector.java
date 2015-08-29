package jtxconnector;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.function.Consumer;

public interface ITxConnector {
    static ITxConnector instance() {
        return new TxConnectorJNA();
    }
    void init(File logDir, int loglvl);

    void setCallback(Consumer<ByteBuffer> cb);

    void send(ByteBuffer buffer);

    void close();


}
