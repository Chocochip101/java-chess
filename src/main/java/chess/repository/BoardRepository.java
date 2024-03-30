package chess.repository;

import chess.domain.pieces.piece.Color;
import chess.domain.pieces.piece.Piece;
import chess.domain.pieces.piece.Type;
import chess.domain.square.Square;
import java.util.Map;
import java.util.Optional;

public interface BoardRepository {
    void save(long roomId, Square square, Type type, Color color);

    void save(long roomId, long pieceId, Square square);

    Optional<Long> findPieceIdBySquare(long roomId, Square square);

    void deleteBySquares(final long roomId, final Square... squares);

    Map<Square, Piece> findAllByRoomId(long roomId);
}
