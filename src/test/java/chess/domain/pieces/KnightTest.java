package chess.domain.pieces;

import chess.domain.pieces.piece.Color;
import chess.domain.pieces.piece.Piece;
import chess.domain.square.Movement;
import chess.domain.square.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("나이트")
class KnightTest {

    @DisplayName("움직일 수 있다")
    @Test
    void canMove() {
        //given
        Square source = Square.from("b1");
        Square destination = Square.from("c3");

        Piece knight = new Knight(Color.WHITE);
        Movement movement = new Movement(source, destination);

        //when
        boolean canMove = knight.canMove(movement, null);

        //then
        assertThat(canMove).isTrue();
    }

    @DisplayName("움직일 수 없다.")
    @Test
    void canNotMove() {
        //given
        Square source = Square.from("b1");
        Square destination = Square.from("b3");

        Piece knight = new Knight(Color.WHITE);
        Movement movement = new Movement(source, destination);

        //when
        boolean canMove = knight.canMove(movement, null);

        //then
        assertThat(canMove).isFalse();
    }
}
