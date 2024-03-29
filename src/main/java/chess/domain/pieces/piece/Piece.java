package chess.domain.pieces.piece;

import chess.domain.pieces.Bishop;
import chess.domain.pieces.King;
import chess.domain.pieces.Knight;
import chess.domain.pieces.Queen;
import chess.domain.pieces.Rook;
import chess.domain.pieces.pawn.Pawn;
import chess.domain.score.Score;
import chess.domain.score.ScoreStatus;
import chess.domain.square.Movement;
import java.util.Objects;

public abstract class Piece {
    private final Color color;
    private final Type type;

    public Piece(final Color color, final Type type) {
        this.color = color;
        this.type = type;
    }

    public static Piece of(final Color color, final Type type) {
        if (type == Type.KING) {
            return new King(color);
        }
        if (type == Type.QUEEN) {
            return new Queen(color);
        }
        if (type == Type.BISHOP) {
            return new Bishop(color);
        }
        if (type == Type.ROOK) {
            return new Rook(color);
        }
        if (type == Type.KNIGHT) {
            return new Knight(color);
        }
        if (type == Type.PAWN) {
            return Pawn.of(color);
        }
        throw new IllegalArgumentException();
    }

    public abstract boolean canMove(final Movement movement, final Piece target);

    public boolean isSameColor(final Piece piece) {
        if (piece == null) {
            return false;
        }
        return color.equals(piece.color);
    }

    public boolean isPawn() {
        return this.type.equals(Type.PAWN);
    }

    public boolean isKing() {
        return this.type.equals(Type.KING);
    }

    public Color color() {
        return color;
    }

    public Type type() {
        return type;
    }

    public Score getScore(ScoreStatus scoreStatus) {
        return scoreStatus.calculate(type.getScore());
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
