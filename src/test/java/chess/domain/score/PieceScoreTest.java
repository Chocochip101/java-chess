package chess.domain.score;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import chess.domain.pieces.Bishop;
import chess.domain.pieces.King;
import chess.domain.pieces.Knight;
import chess.domain.pieces.Queen;
import chess.domain.pieces.Rook;
import chess.domain.pieces.pawn.BlackPawn;
import chess.domain.pieces.pawn.WhitePawn;
import chess.domain.pieces.piece.Color;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("기물의 점수")
class PieceScoreTest {
    @DisplayName("기물별 점수를 검증한다.")
    @Test
    void score() {
        //given & when & then
        assertAll(
                () -> assertThat(PieceScore.getScore(new King(Color.WHITE)).getValue()).isEqualTo(0),
                () -> assertThat(PieceScore.getScore(new Queen(Color.WHITE)).getValue()).isEqualTo(9),
                () -> assertThat(PieceScore.getScore(new Bishop(Color.WHITE)).getValue()).isEqualTo(3),
                () -> assertThat(PieceScore.getScore(new Knight(Color.WHITE)).getValue()).isEqualTo(2.5),
                () -> assertThat(PieceScore.getScore(new Rook(Color.WHITE)).getValue()).isEqualTo(5),
                () -> assertThat(PieceScore.getScore(new WhitePawn()).getValue()).isEqualTo(1),
                () -> assertThat(PieceScore.getScore(new BlackPawn()).getValue()).isEqualTo(1)
        );
    }
}
