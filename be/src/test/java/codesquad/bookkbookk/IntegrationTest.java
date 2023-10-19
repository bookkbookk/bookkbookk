package codesquad.bookkbookk;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import codesquad.bookkbookk.util.DatabaseCleaner;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @LocalServerPort
    private int port;
    @Autowired
    private DatabaseCleaner databaseCleaner;

    @BeforeEach
    public void setUp() {
        databaseCleaner.execute();
        RestAssured.port = port;
    }

}
