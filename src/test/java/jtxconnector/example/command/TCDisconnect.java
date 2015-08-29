package jtxconnector.example.command;


public class TCDisconnect extends TCommand {
    @Override
    public final String id() {
        return "disconnect";
    }

    @Override
    protected final String body() {
        return "";
    }
}
