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
    @DisplayName("타입으로 인스턴스 변수를 반환한다")
    @Test
    void getInstance() {
        //given
        Color white = Color.WHITE;
        Color black = Color.BLACK;

        //when & then
        assertAll(
                () -> assertThat(Type.KING.getInstance(white)).isEqualTo(new King(white)),
                () -> assertThat(Type.QUEEN.getInstance(white)).isEqualTo(new Queen(white)),
                () -> assertThat(Type.ROOK.getInstance(white)).isEqualTo(new Rook(white)),
                () -> assertThat(Type.BISHOP.getInstance(white)).isEqualTo(new Bishop(white)),
                () -> assertThat(Type.KNIGHT.getInstance(white)).isEqualTo(new Knight(white)),
                () -> assertThat(Type.PAWN.getInstance(white)).isEqualTo(new WhitePawn()),
                () -> assertThat(Type.PAWN.getInstance(black)).isEqualTo(new BlackPawn())
        );
    }

    @DisplayName("인스턴스로 타입 반환에 성공한다")
    @Test
    void findByPiece() {
        //given & when & then
        assertAll(
                () -> assertThat(Type.findByPiece(new King(Color.WHITE))).isEqualTo(Type.KING),
                () -> assertThat(Type.findByPiece(new Queen(Color.WHITE))).isEqualTo(Type.QUEEN),
                () -> assertThat(Type.findByPiece(new Rook(Color.WHITE))).isEqualTo(Type.ROOK),
                () -> assertThat(Type.findByPiece(new Bishop(Color.WHITE))).isEqualTo(Type.BISHOP),
                () -> assertThat(Type.findByPiece(new Knight(Color.WHITE))).isEqualTo(Type.KNIGHT),
                () -> assertThat(Type.findByPiece(new BlackPawn())).isEqualTo(Type.PAWN),
                () -> assertThat(Type.findByPiece(new WhitePawn())).isEqualTo(Type.PAWN)
        );
    }
}
