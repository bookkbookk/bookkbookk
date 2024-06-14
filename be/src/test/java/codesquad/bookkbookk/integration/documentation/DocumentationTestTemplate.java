package codesquad.bookkbookk.integration.documentation;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;

import codesquad.bookkbookk.util.IntegrationTest;

import io.restassured.RestAssured;

public class DocumentationTestTemplate extends IntegrationTest {

    public void template() {
        RestAssured
                .given(this.spec).log().all()
                .pathParam("path", 1)
                .queryParam("query", 1)
                .filter(document("{class-name}/{method-name}",
                        pathParameters(
                                parameterWithName("path").description("path")
                        ),
                        requestParameters(
                                parameterWithName("query").description("query")
                        ),
                        requestFields(
                                fieldWithPath("request").type(JsonFieldType.STRING).description("설명")
                        ),
                        responseFields(
                                fieldWithPath("response").type(JsonFieldType.STRING).description("응답")
                        )))
        ;
    }

}
