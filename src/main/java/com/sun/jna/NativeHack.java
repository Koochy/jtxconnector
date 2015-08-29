package com.sun.jna;

import com.sun.jna.Native;

// access to package-private methods
public final class NativeHack {
    public static byte[] getStringBytes(long addr) {
        return Native.getStringBytes(addr);
    }
}
