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
import java.util.Map.Entry;
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

    public Piece getInstance(final Color color) {
        return constructor.apply(color);
    }

    public static Score getScore(final Piece piece) {
        return Score.of(findByPiece(piece).score);
    }

    public static String getName(final Piece piece) {
        return findByPiece(piece).name();
    }

    public static Type findByPiece(final Piece piece) {
        return TYPE_BY_CLASS.entrySet().stream()
                .filter(entry -> entry.getKey().isInstance(piece))
                .map(Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(INVALID_PIECE_TYPE));
    }
}
