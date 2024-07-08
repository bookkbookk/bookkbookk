package codesquad.bookkbookk.common.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EntityType {

    MEMBER("MEMBER_ID", "Member를 찾을 수 없습니다."),
    BOOK_CLUB("BOOK_CLUB_ID", "BookClub을 찾을 수 없습니다."),
    BOOK("BOOK_ID", "Book을 찾을 수 없습니다."),
    GATHERING("GATHERING_ID", "Gathering을 찾을 수 없습니다."),
    CHAPTER("CHAPTER_ID", "Chapter를 찾을 수 없습니다."),
    TOPIC("TOPIC_ID", "Topic을 찾을 수 없습니다."),
    BOOKMARK("BOOKMARK_ID", "Bookmark를 찾을 수 없습니다."),
    COMMENT("COMMENT_ID", "Comment를 찾을 수 없습니다.");

    private final String idColumnName;
    private final String errorMessage;

}
