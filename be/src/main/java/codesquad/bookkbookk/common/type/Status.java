package codesquad.bookkbookk.common.type;

import codesquad.bookkbookk.common.error.exception.StatusNotFoundException;

import lombok.Getter;

@Getter
public enum Status {

    ALL(0, ""),
    BEFORE_READING(1, "독서 전"),
    READING(2, "독서 중"),
    AFTER_READING(3, "독서 완료");

    private final int id;
    private final String name;

    Status(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Status of(int id) {
        for (Status status : Status.values()) {
            if (status.id == id) {
                return status;
            }
        }
        throw new StatusNotFoundException();
    }
}
