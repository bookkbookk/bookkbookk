package codesquad.bookkbookk.error;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import codesquad.bookkbookk.error.exception.ApiException;
import codesquad.bookkbookk.error.exception.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErrorResponse> handleAddressNotFoundException(ApiException exception) {
        log.warn("ApiException handling : {}", exception.toString());
        ErrorResponse response = ErrorResponse.from(exception);

        return ResponseEntity
                .status(response.getStatus())
                .body(response);
    }

}

