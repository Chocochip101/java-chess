package chess.domain.game;

import chess.domain.pieces.piece.Color;
import java.util.Arrays;

public enum WinnerResult {
    WHITE(Color.WHITE),
    BLACK(Color.BLACK),
    TIE(Color.NONE),
    ;

    private static final String INVALID_COLOR = "부적절한 진영 색입니다.";

    private final Color color;

    WinnerResult(final Color color) {
        this.color = color;
    }

    public static WinnerResult from(final Color color) {
        return Arrays.stream(WinnerResult.values())
                .filter(winnerResult -> winnerResult.color.equals(color))
                .findAny()
                .orElseThrow(() -> new IllegalStateException(INVALID_COLOR));
    }
}
