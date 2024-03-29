package chess.domain.game;

import chess.domain.pieces.piece.Color;
import chess.domain.score.Score;

public class Winner {
    private final WinnerResult result;

    private Winner(final WinnerResult result) {
        this.result = result;
    }

    public static Winner from(final Color color) {
        return new Winner(WinnerResult.from(color));
    }

    public static Winner of(final Score blackScore, final Score whiteScore) {
        return new Winner(getCurrentWinner(blackScore, whiteScore));
    }

    public static WinnerResult getCurrentWinner(final Score blackScore, final Score whiteScore) {
        if (blackScore.isBiggerThan(whiteScore)) {
            return WinnerResult.BLACK;
        }
        if (whiteScore.isBiggerThan(blackScore)) {
            return WinnerResult.WHITE;
        }
        return WinnerResult.TIE;
    }

    public WinnerResult getResult() {
        return result;
    }
}
