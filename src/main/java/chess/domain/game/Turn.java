package chess.domain.game;

import chess.domain.pieces.piece.Color;
import java.util.Objects;

public class Turn {
    private Color turn;

    private Turn() {
        turn = Color.WHITE;
    }

    public Turn(Color color) {
        turn = color;
    }

    public static Turn first() {
        return new Turn();
    }

    public void next() {
        turn = turn.reverse();
    }

    public boolean isTurn(final Color color) {
        return this.turn == color;
    }

    public String getColor() {
        return turn.name();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Turn turn1 = (Turn) o;
        return turn == turn1.turn;
    }

    @Override
    public int hashCode() {
        return Objects.hash(turn);
    }
}
