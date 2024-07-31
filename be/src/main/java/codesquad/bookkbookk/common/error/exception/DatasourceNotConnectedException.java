package codesquad.bookkbookk.common.error.exception;

import org.springframework.http.HttpStatus;

public class DatasourceNotConnectedException extends ApiException{

    public DatasourceNotConnectedException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Datasource에 연결할 수 없습니다.");
    }

}
