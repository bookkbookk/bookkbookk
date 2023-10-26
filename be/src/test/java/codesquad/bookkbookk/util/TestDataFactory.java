package codesquad.bookkbookk.util;

import codesquad.bookkbookk.domain.auth.data.type.LoginType;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.topic.data.dto.CreateTopicRequest;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;

public class TestDataFactory {

    public static Member createMember() {
        return Member.builder()
                .email("nag@email.com")
                .loginType(LoginType.GOOGLE)
                .nickname("nag")
                .profileImgUrl("profile")
                .build();
    }

    public static BookClub createBookClub() {
        return BookClub.builder()
                .creatorId(1L)
                .name("Test Book Club")
                .profileImgUrl("image.url")
                .build();
    }

    public static Book createBook1(BookClub bookClub) {
        return Book.builder()
                .title("신데렐라")
                .bookClub(bookClub)
                .author("감귤")
                .category("미스테리")
                .cover("Cinderella")
                .isbn("1231231231231")
                .build();

    }

    public static Book createBook2(BookClub bookClub) {
        return Book.builder()
                .title("개똥벌레")
                .bookClub(bookClub)
                .author("나그")
                .category("스릴러")
                .cover("gaeddong")
                .isbn("1231231231232")
                .build();

    }

    public static Topic createTopic(Long chapterId, String title){
        CreateTopicRequest request = new CreateTopicRequest(chapterId, title);
        return Topic.from(request);
    }

}
