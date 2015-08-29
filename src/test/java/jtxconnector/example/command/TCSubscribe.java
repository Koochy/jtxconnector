package jtxconnector.example.command;

public class TCSubscribe extends TCommand {
    public static final int ALLTRADES = (1 << 1);
    public static final int QUOTATIONS = (1 << 2);
    public static final int QUOTES = (1 << 3);

    private int modeMask;
    private String board;
    private String seccode;

    public TCSubscribe(int modeMask, String board, String seccode) {
        this.modeMask = modeMask;
        this.board = board;
        this.seccode = seccode;
    }

    @Override
    public String id() {
        return "subscribe";
    }

    @Override
    protected String body() {
        String ret = "";
        String modeBody = "<security>" +
                "<board>" + board + "</board>" +
                "<seccode>" + seccode + "</seccode>" +
                "</security>";

        if ((modeMask & ALLTRADES) > 0)
            ret = "<alltrades>" + modeBody + "</alltrades>";
        if ((modeMask & QUOTATIONS) > 0)
            ret += "<quotations>" + modeBody + "</quotations>";
        if ((modeMask & QUOTES) > 0)
            ret += "<quotes>" + modeBody + "</quotes>";

        return ret;
    }
}
