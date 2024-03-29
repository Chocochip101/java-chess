package chess.service;

import static chess.fixture.MovementFixture.createMovements;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import chess.domain.board.BoardFactory;
import chess.domain.board.ChessBoardFactory;
import chess.domain.game.Game;
import chess.domain.square.Movement;
import chess.dto.SquareRequest;
import chess.repository.FakeMovementDao;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("게임 로직")
class GameServiceTest {
    FakeMovementDao moveRepository;
    GameService gameService;

    @BeforeEach
    void setUp() {
        moveRepository = new FakeMovementDao();
        gameService = new GameService(moveRepository);
    }

    @DisplayName("게임방 식별자로 보드의 움직임을 모두 찾는다")
    @Test
    void findMoves() {
        //given
        long roomId = 1L;

        //when
        List<Movement> movements = gameService.findAllMoves(roomId);

        //then
        assertArrayEquals(movements.toArray(), createMovements().toArray());
    }

    @DisplayName("기물이 움직인다")
    @Test
    void move() {
        //given
        long roomId = 1L;
        BoardFactory boardFactory = new ChessBoardFactory();
        Game game = new Game(roomId, boardFactory);
        int movementSize = moveRepository.findAllByRoomId(roomId).size();

        SquareRequest source = SquareRequest.from("b2");
        SquareRequest target = SquareRequest.from("b4");

        //when
        gameService.move(game, source, target);

        //then
        List<Movement> movements = moveRepository.findAllByRoomId(roomId);
        assertAll(
                () -> assertThat(movements).isNotNull(),
                () -> assertThat(movements).hasSize(movementSize + 1),
                () -> assertThat(movements.get(movementSize).isCross()).isTrue(),
                () -> assertThat(movements.get(movementSize).calculateMaxDistance()).isEqualTo(2)
        );
    }
}
