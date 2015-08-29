package jtxconnector.example.command;

import java.nio.ByteBuffer;

abstract public class TCommand {
    abstract public String id();

    abstract protected String body();

    public final String xml() {
        return "<command id=\"" + id() + "\">" + body() + "</command>";
    }

    public ByteBuffer getBuffer() {
        return ByteBuffer.wrap(xml().getBytes());
    }
}
