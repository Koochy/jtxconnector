package jtxconnector;

import com.sun.jna.Pointer;

public class TxjException extends Exception {
    private static final long serialVersionUID = 1L;

    public TxjException(String msg) {
        super(msg);
    }

    public static TxjException fromPointer(Pointer ptr) {
        String msg = ptr.getString(0);
        return new TxjException(msg);
    }
}
