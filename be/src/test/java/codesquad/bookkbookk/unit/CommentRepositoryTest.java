package codesquad.bookkbookk.unit;

import static org.assertj.core.api.SoftAssertions.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import codesquad.bookkbookk.common.config.QueryDslConfig;
import codesquad.bookkbookk.domain.comment.data.dto.ReadCommentSliceResponse;
import codesquad.bookkbookk.domain.comment.repository.CommentRepository;
import codesquad.bookkbookk.util.DatabaseCleaner;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({QueryDslConfig.class, DatabaseCleaner.class})
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @AfterEach
    void cleanUp() {
        databaseCleaner.execute();
    }

    @Test
    @DisplayName("Comment의 Slice를 가져온다.")
    @Sql("classpath:sql/readCommentSlice.sql")
    void readBookmarkSlice() {
        // given
        List<Long> bookmarkIds = List.of(1L, 2L, 3L, 4L, 5L);
        int size = 5;

        // when
        Map<Long, ReadCommentSliceResponse> commentSlicesMap = commentRepository.findSlicesByBookmarkIds(bookmarkIds, size);

        // then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(commentSlicesMap.get(1L).isHasNext()).isTrue();
            softAssertions.assertThat(commentSlicesMap.get(2L).isHasNext()).isFalse();
            softAssertions.assertThat(commentSlicesMap.get(5L).isHasNext()).isFalse();
            softAssertions.assertThat(commentSlicesMap.get(5L).getComments().size()).isEqualTo(3);
        });
    }

}
