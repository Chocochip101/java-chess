package chess.domain.game;

import chess.domain.board.Board;
import chess.domain.board.BoardFactory;
import chess.domain.pieces.piece.Piece;
import chess.domain.square.Square;
import java.util.Map;

public class Game {

    private static final String INVALID_TURN = "현재 %s 색의 턴입니다.";
    private final long roomId;
    private final Board board;
    private final Turn turn;

    private Game(final long roomId, final Board board, final Turn turn) {
        this.roomId = roomId;
        this.board = board;
        this.turn = turn;
    }

    public Game(final long roomId, final BoardFactory boardFactory) {
        this.roomId = roomId;
        this.turn = Turn.first();
        this.board = boardFactory.createBoard();
    }

    public static Game load(final long roomId, final Map<Square, Piece> pieces, final Turn turn) {
        Board board = new Board(pieces);
        return new Game(roomId, board, turn);
    }

    public void movePiece(final Square source, final Square target) {
        validateTurn(source);
        board.move(source, target);
        turn.next();
    }

    private void validateTurn(final Square square) {
        if (!board.checkTurn(square, turn)) {
            throw new IllegalArgumentException(String.format(INVALID_TURN, turn.getColor()));
        }
    }

    public GameResult getResult() {
        return new GameResult(board.getPieces());
    }

    public long getRoomId() {
        return roomId;
    }

    public Map<Square, Piece> getBoardStatus() {
        return board.getPieces();
    }

    public Turn getTurn() {
        return turn;
    }
}
