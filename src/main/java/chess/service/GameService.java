package chess.service;

import chess.domain.game.Game;
import chess.domain.square.File;
import chess.domain.square.Movement;
import chess.domain.square.Rank;
import chess.domain.square.Square;
import chess.dto.SquareRequest;
import chess.repository.MovementRepository;
import java.util.List;

public class GameService {

    private final MovementRepository movementRepository;

    public GameService(final MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

    public List<Movement> findMoves(final long roomId) {
        return movementRepository.findAllByRoomId(roomId);
    }

    public void move(final Game game, final SquareRequest source, final SquareRequest target) {
        File sourceFile = File.from(source.file());
        Rank sourceRank = Rank.from(source.rank());

        File targetFile = File.from(target.file());
        Rank targetRank = Rank.from(target.rank());

        game.movePiece(Square.of(sourceFile, sourceRank), Square.of(targetFile, targetRank));
        createMove(game.getRoomId(), source, target);
    }

    private void createMove(final long roomId, final SquareRequest source, final SquareRequest target) {
        movementRepository.save(roomId, Movement.of(source, target));
    }
}
