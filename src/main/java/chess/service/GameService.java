package chess.service;

import chess.domain.board.Board;
import chess.domain.board.ChessBoardFactory;
import chess.domain.game.Game;
import chess.domain.game.Turn;
import chess.domain.pieces.piece.Piece;
import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;
import chess.dto.SquareRequest;
import chess.repository.BoardRepository;
import chess.repository.RoomRepository;
import java.util.Map;

public class GameService {

    private final BoardRepository boardRepository;
    private final RoomRepository roomRepository;

    public GameService(final BoardRepository boardRepository, final RoomRepository roomRepository) {
        this.boardRepository = boardRepository;
        this.roomRepository = roomRepository;
    }

    public void move(final Game game, final SquareRequest source, final SquareRequest target) {
        Square sourceSquare = Square.of(File.from(source.file()), Rank.from(source.rank()));
        Square targetSquare = Square.of(File.from(target.file()), Rank.from(target.rank()));

        game.movePiece(sourceSquare, targetSquare);
        long pieceId = boardRepository.findPieceIdBySquare(sourceSquare, game.getRoomId()).orElseThrow();
        boardRepository.deleteBySquare(sourceSquare, game.getRoomId());
        boardRepository.save(targetSquare, pieceId, game.getRoomId());
        roomRepository.updateTurn(game.getRoomId(), game.getTurn());
    }

    public Game loadGame(final long roomId) {
        Map<Square, Piece> pieces = boardRepository.findAllByRoomId(roomId);
        if (pieces.isEmpty()) {
            return createGame(roomId);
        }
        Turn turn = roomRepository.findTurnByRoomId(roomId)
                .orElseThrow();
        return Game.load(roomId, pieces, turn);
    }

    private Game createGame(final long roomId) {
        ChessBoardFactory chessBoardFactory = new ChessBoardFactory();
        Board board = chessBoardFactory.createBoard();
        board.getPieces()
                .forEach((key, value) -> boardRepository.save(key, value.type(), value.color(), roomId));
        return new Game(roomId, chessBoardFactory);
    }
}
