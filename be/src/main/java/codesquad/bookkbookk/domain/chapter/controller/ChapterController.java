package codesquad.bookkbookk.domain.chapter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.domain.chapter.data.dto.CreateChapterRequest;
import codesquad.bookkbookk.domain.chapter.data.dto.CreateChapterResponse;
import codesquad.bookkbookk.domain.chapter.service.ChapterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chapters")
public class ChapterController {

    private final ChapterService chapterService;

    @PostMapping
    public ResponseEntity<CreateChapterResponse> createChapter(@RequestBody CreateChapterRequest request) {
        CreateChapterResponse response = chapterService.createChapter(request);

        return ResponseEntity.ok()
                .body(response);
    }

}
