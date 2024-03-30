package chess.domain.score;

import chess.domain.pieces.Bishop;
import chess.domain.pieces.King;
import chess.domain.pieces.Knight;
import chess.domain.pieces.Queen;
import chess.domain.pieces.Rook;
import chess.domain.pieces.pawn.Pawn;
import chess.domain.pieces.piece.Piece;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public enum PieceScore {
    KING(King.class, Score.of(0)),
    QUEEN(Queen.class, Score.of(9)),
    ROOK(Rook.class, Score.of(5)),
    BISHOP(Bishop.class, Score.of(3)),
    KNIGHT(Knight.class, Score.of(2.5)),
    PAWN(Pawn.class, Score.of(1)),
    ;

    private static final String INVALID_PIECE_TYPE = "기물이 존재하지 않습니다.";

    private static final Map<Class<? extends Piece>, Score> SCORE_BY_CLASS = new HashMap<>();

    static {
        for (PieceScore pieceScore : values()) {
            SCORE_BY_CLASS.put(pieceScore.classType, pieceScore.score);
        }
    }

    private final Class<? extends Piece> classType;
    private final Score score;

    PieceScore(final Class<? extends Piece> classType, final Score score) {
        this.classType = classType;
        this.score = score;
    }

    public static Score getScore(final Piece piece) {
        return SCORE_BY_CLASS.entrySet().stream()
                .filter(entry -> entry.getKey().isInstance(piece))
                .map(Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(INVALID_PIECE_TYPE));
    }
}
