package chess.repository;

import chess.domain.Name;
import chess.domain.game.Turn;
import chess.domain.room.Room;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakeRoomDao implements RoomRepository {
    private final Map<Long, Room> rooms = new HashMap<>();

    @Override
    public long save(final Room room, final Turn turn) {
        long roomId = rooms.size() + 1;
        rooms.put(roomId, new Room(roomId, room.getUserId(), new Name(room.getName())));
        return roomId;
    }

    @Override
    public List<Room> findAllByUserId(final long userId) {
        return rooms.values().stream()
                .filter(room -> room.getUserId() == userId)
                .toList();
    }

    @Override
    public Optional<Room> findByUserIdAndName(final long userId, final String name) {
        return rooms.values().stream()
                .filter(room -> room.getUserId() == userId && room.getName().equals(name))
                .findAny();
    }

    @Override
    public Optional<Turn> findTurnByRoomId(final long roomId) {
        return Optional.empty();
    }

    @Override
    public void updateTurn(final long roomId, final Turn turn) {

    }
}
