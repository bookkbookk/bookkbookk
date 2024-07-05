package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class EntityNotFountException extends ApiException {

    private static final String PREDICATE = "을(를) 찾을 수 없습니다.";

    public EntityNotFountException(Class<?> entity) {
        super(HttpStatus.NOT_FOUND, entity.getSimpleName() + PREDICATE);
    }

}
