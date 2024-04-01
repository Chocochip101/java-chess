package chess.service;

import chess.domain.board.ChessBoardFactory;
import chess.domain.game.Game;
import chess.domain.game.GameResult;
import chess.domain.game.Turn;
import chess.domain.pieces.piece.Piece;
import chess.domain.pieces.piece.Type;
import chess.domain.square.File;
import chess.domain.square.Rank;
import chess.domain.square.Square;
import chess.dto.PieceResponse;
import chess.dto.SquareRequest;
import chess.repository.BoardRepository;
import chess.repository.RoomRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class GameService {

    private final BoardRepository boardRepository;
    private final RoomRepository roomRepository;
    private Game game;

    public GameService(final BoardRepository boardRepository, final RoomRepository roomRepository) {
        this.boardRepository = boardRepository;
        this.roomRepository = roomRepository;
    }

    public void move(final SquareRequest source, final SquareRequest target) {
        Square sourceSquare = Square.of(File.from(source.file()), Rank.from(source.rank()));
        Square targetSquare = Square.of(File.from(target.file()), Rank.from(target.rank()));

        game.movePiece(sourceSquare, targetSquare);
        long pieceId = boardRepository.findPieceIdBySquare(game.getRoomId(), sourceSquare)
                .orElseThrow(IllegalStateException::new);
        boardRepository.deleteBySquares(game.getRoomId(), sourceSquare, targetSquare);
        boardRepository.save(game.getRoomId(), pieceId, targetSquare);
        roomRepository.updateTurn(game.getRoomId(), game.getTurn());
    }

    public void loadGame(final long roomId) {
        Map<Square, Piece> pieces = boardRepository.findAllByRoomId(roomId);
        if (pieces.isEmpty()) {
            game = createGame(roomId);
            return;
        }
        Turn turn = roomRepository.findTurnByRoomId(roomId)
                .orElseThrow(IllegalStateException::new);
        game = Game.load(roomId, pieces, turn);
    }

    private Game createGame(final long roomId) {
        ChessBoardFactory chessBoardFactory = new ChessBoardFactory();
        chessBoardFactory.createBoard().getPieces()
                .forEach((key, value) -> boardRepository.save(roomId, key, Type.findByPiece(value), value.color()));
        return new Game(roomId, chessBoardFactory);
    }

    public GameResult getResult() {
        return game.getResult();
    }

    public List<PieceResponse> getGameStatus() {
        return createBoardResponse(game.getBoardStatus());
    }

    private List<PieceResponse> createBoardResponse(final Map<Square, Piece> pieces) {
        List<PieceResponse> responses = new ArrayList<>();
        for (Entry<Square, Piece> squareToPiece : pieces.entrySet()) {
            responses.add(PieceResponse.of(squareToPiece.getKey(), squareToPiece.getValue()));
        }
        return responses;
    }

    public Turn getTurn() {
        return game.getTurn();
    }
}
