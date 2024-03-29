package chess.domain.pieces.piece;

import chess.domain.pieces.Bishop;
import chess.domain.pieces.King;
import chess.domain.pieces.Knight;
import chess.domain.pieces.Queen;
import chess.domain.pieces.Rook;
import chess.domain.pieces.pawn.Pawn;
import chess.domain.score.Score;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public enum Type {
    KING(0, King.class, King::new),
    QUEEN(9, Queen.class, Queen::new),
    ROOK(5, Rook.class, Rook::new),
    BISHOP(3, Bishop.class, Bishop::new),
    KNIGHT(2.5, Knight.class, Knight::new),
    PAWN(1, Pawn.class, Pawn::of),
    ;

    private static final String INVALID_PIECE_TYPE = "기물이 존재하지 않습니다.";

    private static final Map<Class<? extends Piece>, Type> TYPE_BY_CLASS = new HashMap<>();

    static {
        for (Type type : values()) {
            TYPE_BY_CLASS.put(type.classType, type);
        }
    }

    private final double score;
    private final Class<? extends Piece> classType;
    private final Function<Color, Piece> constructor;

    Type(final double score, final Class<? extends Piece> classType,
         final Function<Color, Piece> constructor) {
        this.score = score;
        this.classType = classType;
        this.constructor = constructor;
    }

    public static Type findByPiece(final Piece piece) {
        for (Map.Entry<Class<? extends Piece>, Type> entry : TYPE_BY_CLASS.entrySet()) {
            if (entry.getKey().isInstance(piece)) {
                return entry.getValue();
            }
        }
        throw new IllegalStateException(INVALID_PIECE_TYPE);
    }

    public Piece getInstance(final Color color) {
        return constructor.apply(color);
    }

    public Score getScore() {
        return Score.of(score);
    }
}
