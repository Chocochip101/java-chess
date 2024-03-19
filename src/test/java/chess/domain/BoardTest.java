package chess.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import chess.domain.piece.Color;
import chess.domain.piece.Pawn;
import chess.domain.piece.Piece;
import chess.domain.piece.Rook;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("게임판")
class BoardTest {
    @DisplayName("초기화에 성공한다.")
    @Test
    void initialize() {
        //given
        Board board = new Board();

        int expectedSize = 64;

        //when
        List<Piece> pieces = board.getPieces();

        //then
        assertThat(pieces).hasSize(expectedSize);
        assertThat(pieces).contains(new Pawn(Color.WHITE, new Square(Rank.A, File.TWO)));
    }
}