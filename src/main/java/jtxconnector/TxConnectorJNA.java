package jtxconnector;


import com.sun.jna.Callback;
import com.sun.jna.Native;
import com.sun.jna.NativeHack;
import com.sun.jna.Pointer;
import org.apache.log4j.Logger;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.function.Consumer;

public final class TxConnectorJNA implements ITxConnector {

    private static Consumer<ByteBuffer> onData;

    static {
        Native.register("txcn64");  // main/resources/txconnector.dll | txcn64.dll
    }

    interface tcallback extends Callback {
        byte apply(Pointer xml);
    }

    private final Logger log = Logger.getLogger(TxConnectorJNA.class.getSimpleName());

    public static native Pointer SendCommand(ByteBuffer BYTEPtr1);

    public static native byte FreeMemory(Pointer BYTEPtr1);

    public static native byte SetCallback(tcallback pCallback);

    public static native Pointer Initialize(byte logPath[], int logLevel);

    public static native Pointer UnInitialize();

    public static native Pointer SetLogLevel(int int1);

    private static byte txcCallback(Pointer ptr) {
        byte[] bytes = NativeHack.getStringBytes(Pointer.nativeValue(ptr));
        onData.accept(ByteBuffer.wrap(bytes));
//        ptr.clear(bytes.length);
//        Native.free(Pointer.nativeValue(ptr));
        return (byte) 1;
    }

    @Override
    public void init(File logDir, int loglvl) {
        byte[] ldb = (logDir.getAbsolutePath() + "\0").getBytes();

        log.info("Lib::Initialize lod_dir:" + logDir.getAbsolutePath() + "\n logLevel:" + loglvl);

        Pointer ptr = Initialize(ldb, loglvl);

        if (null != ptr) {
            TxjException e = TxjException.fromPointer(ptr);
            log.error("Oops! Lib::Initialize error", e);
            e.printStackTrace();
        }

        if (SetCallback(TxConnectorJNA::txcCallback) == 1)
            log.info("Lib::SetCallback - OK!");
        else {
            TxjException e = new TxjException("Chased geese. Lib::SetCallback returns false");
            log.error("Oops! Lib::SetCallback error", e);
            e.printStackTrace();
        }

        log.info("Lib::Initialize OK");
    }

    @Override
    public void setCallback(Consumer<ByteBuffer> cb) {
        onData = cb;
    }

    @Override
    public void send(ByteBuffer buffer) {
        Pointer ptr = SendCommand(buffer);

        //<result success=�true�/>
        if (!ptr.getString(0).contains("true")) {
            TxjException e = TxjException.fromPointer(ptr);
            log.error("Lib::SendCommand error", e);
            e.printStackTrace();
        } else
            log.info("Lib::SendCommand - OK!");

        FreeMemory(ptr);
    }

    @Override
    public void close() {
        log.info("Lib:UnInitialize");
        UnInitialize();
    }
}
