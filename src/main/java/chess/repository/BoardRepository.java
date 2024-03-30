package chess.repository;

import chess.domain.pieces.piece.Color;
import chess.domain.pieces.piece.Piece;
import chess.domain.pieces.piece.Type;
import chess.domain.square.Square;
import java.util.Map;
import java.util.Optional;

public interface BoardRepository {
    void save(Square square, Type type, Color color, long roomId);

    void save(Square square, long pieceId, long roomId);

    Optional<Long> findPieceIdBySquare(Square square, long roomId);

    void deleteBySquares(final long roomId, final Square... squares);

    Map<Square, Piece> findAllByRoomId(long roomId);
}
