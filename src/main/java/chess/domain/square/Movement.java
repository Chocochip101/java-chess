package chess.domain.square;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;

public class Movement {
    public static final String INVALID_PIECE_MOVEMENT = "해당 기물은 위치로 이동할 수 없습니다.";

    private final Square source;
    private final Square target;

    public Movement(final Square source, final Square target) {
        validate(source, target);
        this.source = source;
        this.target = target;
    }

    private void validate(final Square source, final Square target) {
        if (source.equals(target)) {
            throw new IllegalArgumentException(INVALID_PIECE_MOVEMENT);
        }
    }

    public Set<Square> findRoute() {
        if (!(isCross() || isDiagonal())) {
            return Collections.emptySet();
        }
        int distance = calculateMaxDistance();
        Set<Square> route = new HashSet<>();
        IntStream.range(1, distance)
                .forEach(i -> route.add(calculateRoute(distance, i)));
        return route;
    }

    public boolean isCross() {
        return getFileDifference() * getRankDifference() == 0;
    }

    public boolean isDiagonal() {
        return Math.abs(getFileDifference()) == Math.abs(getRankDifference());
    }

    public int calculateMaxDistance() {
        return Math.max(Math.abs(getFileDifference()), Math.abs(getRankDifference()));
    }

    private Square calculateRoute(final int distance, final int index) {
        int fileMoveUnit = (getFileDifference() / distance) * index;
        int rankMoveUnit = (getRankDifference() / distance) * index;
        return source.move(fileMoveUnit, rankMoveUnit);
    }

    public int getFileDifference() {
        return target.getFileIndex() - source.getFileIndex();
    }

    public int getRankDifference() {
        return target.getRankIndex() - source.getRankIndex();
    }

    public int getSourceRankIndex() {
        return source.getRankIndex();
    }
}
