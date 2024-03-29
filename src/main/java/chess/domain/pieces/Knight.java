package chess.domain.pieces;

import chess.domain.pieces.piece.Color;
import chess.domain.pieces.piece.Piece;
import chess.domain.square.Movement;

public class Knight extends Piece {

    public Knight(final Color color) {
        super(color);
    }

    @Override
    public boolean canMove(final Movement movement, final Piece target) {
        int fileDiff = Math.abs(movement.getFileDifference());
        int rankDiff = Math.abs(movement.getRankDifference());

        return (fileDiff == 2 && rankDiff == 1) || (fileDiff == 1 && rankDiff == 2);
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
