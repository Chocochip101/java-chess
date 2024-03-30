package chess.domain.pieces.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.pieces.Bishop;
import chess.domain.pieces.King;
import chess.domain.pieces.Knight;
import chess.domain.pieces.Queen;
import chess.domain.pieces.Rook;
import chess.domain.pieces.pawn.BlackPawn;
import chess.domain.pieces.pawn.WhitePawn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("타입")
class TypeTest {

    @DisplayName("기물별 점수를 검증한다.")
    @Test
    void score() {
        //given & when & then
        assertAll(
                () -> assertThat(Type.getScore(new King(Color.WHITE)).getValue()).isEqualTo(0),
                () -> assertThat(Type.getScore(new Queen(Color.WHITE)).getValue()).isEqualTo(9),
                () -> assertThat(Type.getScore(new Bishop(Color.WHITE)).getValue()).isEqualTo(3),
                () -> assertThat(Type.getScore(new Knight(Color.WHITE)).getValue()).isEqualTo(2.5),
                () -> assertThat(Type.getScore(new Rook(Color.WHITE)).getValue()).isEqualTo(5),
                () -> assertThat(Type.getScore(new WhitePawn()).getValue()).isEqualTo(1),
                () -> assertThat(Type.getScore(new BlackPawn()).getValue()).isEqualTo(1)
        );
    }
}
