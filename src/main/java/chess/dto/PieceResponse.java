package chess.dto;

import chess.domain.pieces.piece.Piece;
import chess.domain.pieces.piece.Type;
import chess.domain.square.Square;

public record PieceResponse(int fileIndex, int rankIndex, String color, String type) {

    public static PieceResponse of(final Square square, final Piece piece) {
        int fileIndex = square.getFileIndex();
        int rankIndex = square.getRankIndex();
        String color = piece.color().name();
        String type = Type.getName(piece);
        return new PieceResponse(fileIndex, rankIndex, color, type);
    }
}
