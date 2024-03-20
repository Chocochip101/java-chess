package chess.domain.square;

import java.util.Arrays;

public enum File {
    A,
    B,
    C,
    D,
    E,
    F,
    G,
    H,
    ;

    private static final String INVALID_FILE = "일치하지 않은 파일 입니다.";

    public static File from(String file) {
        return Arrays.stream(File.values())
                .filter(value -> value.name().equals(file.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(INVALID_FILE));
    }
}