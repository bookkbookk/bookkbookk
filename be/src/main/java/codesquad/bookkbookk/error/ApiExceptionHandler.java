package codesquad.bookkbookk.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import codesquad.bookkbookk.error.exception.ApiException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiException> handleAddressNotFoundException(ApiException exception) {
        log.warn("ApiException handling : {}", exception.toString());

        return ResponseEntity.status(exception.getStatus()).body(exception);
    }
}

