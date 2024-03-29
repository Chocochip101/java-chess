package chess.repository;

import chess.domain.game.Turn;
import chess.domain.room.Room;
import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    long save(Room room, Turn turn);

    List<Room> findAllByUserId(long userId);

    Optional<Room> findByUserIdAndName(long userId, String name);

    Optional<Turn> findTurnByRoomId(long roomId);

    void updateTurn(long roomId, Turn turn);
}
