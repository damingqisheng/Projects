package chess.action;

public class MoveAction extends Action {
    private final String from;
    private final String to;

    public MoveAction(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
