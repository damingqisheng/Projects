package chess.exceptions;

public class Checkmate extends ChessException {
    private String winner;
    public Checkmate(String winner) {
        this.winner = winner;
    }

    public String getWinner() {
        return winner;
    }
}
