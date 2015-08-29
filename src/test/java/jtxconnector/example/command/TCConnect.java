package jtxconnector.example.command;

import jtxconnector.Constants;

public class TCConnect extends TCommand {
    final String login, password;

    public TCConnect(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public final String id() {
        return "connect";
    }

    @Override
    protected final String body() {
        return "<login>" + login + "</login>" +
                "<password>" + password + "</password>" +
                "<host>" + Constants.HFT_HOST + "</host>" +
                "<milliseconds>" + Constants.DEFAULT_MILLS + "</milliseconds>" +
                "<rqdelay>" + Constants.HFT_RQ_DELAY + "</rqdelay>" +
                "<port>" + Constants.HFT_PORT + "</port>";
    }
}
