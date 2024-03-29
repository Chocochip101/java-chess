package chess.domain.game;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.pieces.piece.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("승리")
class WinnerResultTest {

    @DisplayName("승리자의 색을 반환한다")
    @Test
    void getWinner() {
        //given
        Color blackColor = Color.BLACK;
        Color whiteColor = Color.WHITE;

        //when
        WinnerResult blackWinner = WinnerResult.from(blackColor);
        WinnerResult whiteWinner = WinnerResult.from(whiteColor);

        //then
        assertAll(
                () -> assertThat(blackWinner).isEqualTo(WinnerResult.BLACK),
                () -> assertThat(whiteWinner).isEqualTo(WinnerResult.WHITE)
        );
    }
}
