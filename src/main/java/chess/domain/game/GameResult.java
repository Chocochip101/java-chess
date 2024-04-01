package chess.domain.game;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;

import chess.domain.pieces.piece.Color;
import chess.domain.pieces.piece.Piece;
import chess.domain.score.Score;
import chess.domain.score.ScoreStatus;
import chess.domain.square.Square;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class GameResult {
    private static final String NO_KING_FOUND = "살아있는 왕이 없습니다.";
    private static final int KING_COUNT = 2;

    private final Map<Square, Piece> pieces;

    public GameResult(final Map<Square, Piece> pieces) {
        this.pieces = pieces;
    }

    private static double findDefaultPawnScore(final Map<Integer, List<Piece>> fileToPawn) {
        return fileToPawn.values().stream()
                .filter(list -> list.size() == 1)
                .flatMap(List::stream)
                .mapToDouble(piece -> piece.getScore(ScoreStatus.DEFAULT).getValue())
                .sum();
    }

    private static double findHalfPawnScore(final Map<Integer, List<Piece>> fileToPawn) {
        return fileToPawn.values().stream()
                .filter(list -> list.size() > 1)
                .flatMap(List::stream)
                .mapToDouble(piece -> piece.getScore(ScoreStatus.HALF).getValue())
                .sum();
    }

    public Score calculateScore(final Color color) {
        double pieceScore = calculatePieceScore(color);
        double pawnScore = calculatePawnScore(color);
        return Score.of(pieceScore + pawnScore);
    }

    private double calculatePieceScore(final Color color) {
        return pieces.values().stream()
                .filter(piece -> piece.isSameColor(color) && !piece.isPawn())
                .mapToDouble(piece -> piece.getScore(ScoreStatus.DEFAULT).getValue())
                .sum();
    }

    private double calculatePawnScore(final Color color) {
        Map<Integer, List<Piece>> fileToPawn = pieces.entrySet().stream()
                .filter(it -> it.getValue().isSameColor(color) && it.getValue().isPawn())
                .collect(groupingBy(it -> it.getKey().getFileIndex(), mapping(Entry::getValue, Collectors.toList())));

        return findDefaultPawnScore(fileToPawn) + findHalfPawnScore(fileToPawn);
    }

    public boolean isGameOver() {
        long kingCount = pieces.values().stream()
                .filter(Piece::isKing)
                .count();
        return kingCount != KING_COUNT;
    }

    public WinnerResult getWinnerResult() {
        return determineWinner().getResult();
    }

    private Winner determineWinner() {
        if (isGameOver()) {
            return Winner.from(findKing().color());
        }
        return Winner.of(calculateScore(Color.BLACK), calculateScore(Color.WHITE));
    }

    private Piece findKing() {
        return pieces.values().stream()
                .filter(Piece::isKing)
                .findAny()
                .orElseThrow(() -> new IllegalStateException(NO_KING_FOUND));
    }
}
