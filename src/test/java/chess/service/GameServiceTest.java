package chess.service;

import static chess.fixture.RoomFixture.createChessRoom;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.board.BoardFactory;
import chess.domain.board.ChessBoardFactory;
import chess.domain.game.Game;
import chess.domain.game.Turn;
import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;
import chess.dto.SquareRequest;
import chess.repository.BoardRepository;
import chess.repository.FakeBoardDao;
import chess.repository.FakeRoomDao;
import chess.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("게임 로직")
class GameServiceTest {
    BoardRepository boardRepository;
    RoomRepository roomRepository;
    GameService gameService;

    long roomId;
    long pieceId;
    Square square;
    BoardFactory boardFactory;
    Game game;

    @BeforeEach
    void setUp() {
        boardRepository = new FakeBoardDao();
        roomRepository = new FakeRoomDao();

        roomId = roomRepository.save(createChessRoom(), Turn.first());

        pieceId = 1L;
        square = Square.of(File.A, Rank.TWO);
        boardRepository.save(square, pieceId, roomId);

        gameService = new GameService(boardRepository, roomRepository);

        boardFactory = new ChessBoardFactory();
        game = new Game(roomId, boardFactory);
    }

    @DisplayName("기물이 움직인다")
    @Test
    void move() {
        //given
        SquareRequest source = SquareRequest.from("a2");
        SquareRequest target = SquareRequest.from("a4");

        //when
        gameService.move(game, source, target);

        //then
        assertThat(boardRepository.findPieceIdBySquare(Square.of(File.A, Rank.FOUR), roomId)).isNotNull();
    }

    @DisplayName("게임을 불러온다")
    @Test
    void loadGame() {
        //given
        Turn expectedTurn = Turn.first();

        //when
        Game loadGame = gameService.loadGame(roomId);

        //then
        assertAll(
                () -> assertThat(loadGame.getTurn()).isEqualTo(expectedTurn),
                () -> assertThat(loadGame.getResult().isGameOver()).isFalse()
        );
    }
}
