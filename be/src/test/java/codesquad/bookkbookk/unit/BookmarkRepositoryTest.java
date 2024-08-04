package codesquad.bookkbookk.unit;

import static org.assertj.core.api.SoftAssertions.*;

import java.time.Instant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import codesquad.bookkbookk.common.config.QueryDslConfig;
import codesquad.bookkbookk.domain.bookmark.data.entity.Bookmark;
import codesquad.bookkbookk.domain.bookmark.repository.BookmarkRepository;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(QueryDslConfig.class)
public class BookmarkRepositoryTest {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Test
    @DisplayName("Bookmark의 Slice를 가져온다.")
    @Sql("classpath:sql/readBookmarkSlice.sql")
    void readBookmarkSlice() {
        // given
        Pageable pageable = PageRequest.of(0, 5);

        // when
        Slice<Bookmark> bookmarkSlice = bookmarkRepository.findSliceByTopicId(1L, pageable);

        // then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(bookmarkSlice.hasNext()).isTrue();
            softAssertions.assertThat(bookmarkSlice.getContent().get(0).getCreatedTime())
                    .isEqualTo(Instant.parse("2000-01-07T00:00:00Z"));
        });
    }

    @Test
    @DisplayName("Bookmark의 마지막 Slice를 가져온다.")
    @Sql("classpath:sql/readBookmarkSlice.sql")
    void readBookmarkLastSlice() {
        // given
        Pageable pageable = PageRequest.of(1, 5);

        // when
        Slice<Bookmark> bookmarkSlice = bookmarkRepository.findSliceByTopicId(1L, pageable);

        // then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(bookmarkSlice.hasNext()).isFalse();
            softAssertions.assertThat(bookmarkSlice.getContent().get(0).getCreatedTime())
                    .isEqualTo(Instant.parse("2000-01-02T00:00:00Z"));
        });
    }

}
