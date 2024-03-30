package chess.repository;

import chess.domain.pieces.piece.Color;
import chess.domain.pieces.piece.Piece;
import chess.domain.pieces.piece.Type;
import chess.domain.square.Square;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class FakeBoardDao implements BoardRepository {

    List<FakeBoard> boards = new ArrayList<>();

    @Override
    public void save(final long roomId, final Square square, final Type type, final Color color) {
        boards.add(new FakeBoard(boards.size(), roomId, square.getName(), roomId));
    }

    @Override
    public void save(final long roomId, final long pieceId, final Square square) {
        boards.add(new FakeBoard(boards.size() + 1, roomId, square.getName(), pieceId));
    }

    @Override
    public Optional<Long> findPieceIdBySquare(final long roomId, final Square square) {
        return boards.stream()
                .filter(board -> Objects.equals(board.square, square.getName()) && board.room_id == roomId)
                .map(fakeBoard -> fakeBoard.piece_id).findAny();
    }

    @Override
    public void deleteBySquares(final long roomId, final Square... squares) {
        for (Square square : squares) {
            boards.removeIf(board -> board.square.equals(square.getName()) && board.room_id == roomId);
        }
    }

    @Override
    public Map<Square, Piece> findAllByRoomId(final long roomId) {
        return new HashMap<>();
    }

    record FakeBoard(long board_id, long room_id, String square, long piece_id) {
    }
}
