package chess.domain.pieces;

import chess.domain.pieces.piece.Color;
import chess.domain.pieces.piece.Piece;
import chess.domain.square.Movement;

public class Queen extends Piece {

    public Queen(final Color color) {
        super(color);
    }

    @Override
    public boolean canMove(final Movement movement, final Piece target) {
        return movement.isCross() || movement.isDiagonal();
    }

    @Override
    public boolean isPawn() {
        return false;
    }

    @Override
    public boolean isKing() {
        return false;
    }
}
