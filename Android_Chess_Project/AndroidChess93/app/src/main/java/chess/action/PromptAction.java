package chess.action;

public class PromptAction extends Action {
    private final String to;
    public PromptAction(String to) {
        this.to = to;
    }

    public String getTo() {
        return to;
    }
}
