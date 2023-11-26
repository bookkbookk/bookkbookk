package codesquad.bookkbookk.domain.chapter.data.type;

public enum ChapterStatus {

    BEFORE_READING("독서 전", 1),
    READING("독서 중", 2),
    AFTER_READING("독서 완료", 3);

    private final String name;
    private final int id;

    ChapterStatus(String name, int id) {
        this.name = name;
        this.id = id;
    }
}

