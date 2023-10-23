package codesquad.bookkbookk.domain.book.service;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import codesquad.bookkbookk.domain.book.data.dto.ReadAladinBooksResponse;

@Service
public class AladinService {

    public List<ReadAladinBooksResponse> readAladinBooks(String searchTerm) {
        Map<String, Object> response = WebClient.create()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("www.aladin.co.kr")
                        .path("/ttb/api/ItemSearch.aspx")
                        .queryParam("ttbkey", "ttbineedyou_u1803001")
                        .queryParam("QueryType", "Title")
                        .queryParam("MaxResults", 20)
                        .queryParam("start", 1)
                        .queryParam("SearchTarget", "Book")
                        .queryParam("output", "js")
                        .queryParam("Version", 20131101)
                        .queryParam("CategoryId", 0)
                        .queryParam("Query", searchTerm)
                        .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
                })
                .block();

        List<Map<String, Object>> datum = (List<Map<String, Object>>) response.get("item");
        return ReadAladinBooksResponse.from(datum);
    }

}
