package codesquad.bookkbookk.unit;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import codesquad.bookkbookk.common.error.exception.StatusNotFoundException;
import codesquad.bookkbookk.common.type.Status;

public class StatusTest {

    @Test
    @DisplayName("아이디에 해당하는 Status를 가져온다.")
    void getStatus() {
        // given
        int id = 1;

        // when
        Status status = Status.of(id);

        // then
        assertThat(status).isEqualTo(Status.BEFORE_READING);
    }

    @Test
    @DisplayName("아이디에 해당하는 Status가 없으면 예외가 발생한다.")
    void getStatusWithInvalidId() {
        // given
        int id = -1;

        // when & then
        assertThatThrownBy(() -> Status.of(id)).isInstanceOf(StatusNotFoundException.class);
    }

}
