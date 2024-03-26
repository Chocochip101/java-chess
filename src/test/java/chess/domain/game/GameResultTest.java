package chess.domain.game;

import chess.domain.pieces.*;
import chess.domain.pieces.pawn.Pawn;
import chess.domain.pieces.piece.Color;
import chess.domain.pieces.piece.Piece;
import chess.domain.score.Score;
import chess.domain.square.Square;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("게임 결과")
class GameResultTest {

    @DisplayName("기본 점수의 합을 반환한다")
    @Test
    void gameResultScore() {
        //given
        Color black = Color.BLACK;

        Map<Square, Piece> pieces = Map.of(
                Square.from("c8"), new Rook(black),
                Square.from("d7"), new Bishop(black),
                Square.from("e6"), new Queen(black),
                Square.from("a7"), Pawn.of(black),
                Square.from("b6"), Pawn.of(black),
                Square.from("c7"), Pawn.of(black),
                Square.from("b8"), new King(black)
        );

        //when
        GameResult gameResult = new GameResult(pieces);

        //then
        assertThat(gameResult.calculateScore(black)).isEqualTo(Score.of(20));
    }

    @DisplayName("세로줄에 같은 색의 폰이 있는 경우 1점이 아닌 0.5점으로 계산한다")
    @Test
    void gameResultScoreWithVertical() {
        //given
        Color white = Color.WHITE;

        Map<Square, Piece> pieces = Map.of(
                Square.from("e1"), new Rook(white),
                Square.from("f4"), new Knight(white),
                Square.from("g4"), new Queen(white),
                Square.from("f2"), Pawn.of(white),
                Square.from("f3"), Pawn.of(white),
                Square.from("g2"), Pawn.of(white),
                Square.from("h3"), Pawn.of(white),
                Square.from("f1"), new King(white)
        );

        //when
        GameResult gameResult = new GameResult(pieces);

        //then
        assertThat(gameResult.calculateScore(white)).isEqualTo(Score.of(19.5));
    }

    @DisplayName("게임이 끝났는지 검증한다")
    @Test
    void finishGame() {
        //given
        Color white = Color.WHITE;

        Map<Square, Piece> pieces = Map.of(
                Square.from("e1"), new Rook(white),
                Square.from("f4"), new Knight(white),
                Square.from("g4"), new Queen(white),
                Square.from("f2"), Pawn.of(white),
                Square.from("f3"), Pawn.of(white),
                Square.from("g2"), Pawn.of(white),
                Square.from("h3"), Pawn.of(white),
                Square.from("f1"), new King(white)
        );

        //when
        GameResult gameResult = new GameResult(pieces);

        //then
        assertThat(gameResult.isGameOver()).isTrue();
    }
}