package jtxconnector.example;

import jtxconnector.Constants;
import jtxconnector.ITxConnector;
import jtxconnector.example.command.TCConnect;
import jtxconnector.example.command.TCDisconnect;
import jtxconnector.example.command.TCSubscribe;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class Example {

    public static void main(String[] args) throws InterruptedException {
        StringBuilder logd = new StringBuilder();
        logd.append(System.getProperty("java.io.tmpdir"));
        logd.append("/txlogs");
        new Example(logd.toString());
    }

    private final ITxConnector connector;

    public Example(String logdir) throws InterruptedException {
        connector = ITxConnector.instance();
        connector.init(new File(logdir), Constants.DEFAULT_LOGLEVEL);
        connector.setCallback(buffer -> {
            String msg = new String(buffer.array());
            System.out.println(msg);
        });

        connector.send(new TCConnect("%login%", "%password%").getBuffer());

        TimeUnit.SECONDS.sleep(10);
        connector.send(new TCSubscribe(TCSubscribe.ALLTRADES, "FUT", "SiU5").getBuffer());

        TimeUnit.MINUTES.sleep(10);
        connector.send(new TCDisconnect().getBuffer());
        connector.close();
    }
}
