package chess.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("사용자")
class UserTest {
    @DisplayName("생성에 성공한다")
    @Test
    void user() {
        //given
        long userId = 1L;
        String name = "choco";

        //when
        User noIdUser = User.from(name);
        User idUser = User.of(userId, name);

        //then
        assertAll(
                () -> assertThat(noIdUser.getName()).isEqualTo(name),
                () -> assertThat(idUser.getId()).isEqualTo(userId),
                () -> assertThat(idUser.getName()).isEqualTo(name)
        );
    }

    @DisplayName("부적절한 이름에 예외가 발생한다")
    @ParameterizedTest
    @ValueSource(strings = {"", "    ", "!@asdr32"})
    void invalidName(final String invalidName) {
        //given & when & then
        assertThatThrownBy(() -> User.from(invalidName))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
