package chess.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.board.BoardFactory;
import chess.domain.board.ChessBoardFactory;
import chess.domain.game.Game;
import chess.domain.game.Turn;
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

    long roomId;
    BoardFactory boardFactory;
    Game game;
    
    @BeforeEach
    void setUp() {
        moveRepository = new FakeMovementDao();
        gameService = new GameService(moveRepository);

        roomId = 1L;
        boardFactory = new ChessBoardFactory();
        game = new Game(roomId, boardFactory);
    }

    @DisplayName("기물이 움직인다")
    @Test
    void move() {
        //given
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

    @DisplayName("게임을 불러온다")
    @Test
    void loadGame() {
        //given
        Turn expectedTurn = Turn.first();
        expectedTurn.next();

        //when
        Game loadGame = gameService.loadGame(roomId);

        //then
        assertAll(
                () -> assertThat(loadGame.getTurn()).isEqualTo(expectedTurn),
                () -> assertThat(loadGame.getResult().isGameOver()).isFalse()
        );
    }
}
