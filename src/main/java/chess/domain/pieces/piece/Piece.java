package chess.domain.pieces.piece;

import chess.domain.score.Score;
import chess.domain.score.ScoreStatus;
import chess.domain.square.Movement;
import java.util.Objects;

public abstract class Piece {
    private final Color color;

    public Piece(final Color color) {
        this.color = color;
    }

    public boolean isSameColor(final Piece piece) {
        if (piece == null) {
            return false;
        }
        return color.equals(piece.color);
    }

    public abstract boolean canMove(final Movement movement, final Piece target);

    public abstract boolean isPawn();

    public abstract boolean isKing();

    public Color color() {
        return color;
    }

    public Score getScore(final ScoreStatus scoreStatus) {
        return scoreStatus.calculate(Type.findByPiece(this).getScore());
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return color.equals(piece.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }
}
