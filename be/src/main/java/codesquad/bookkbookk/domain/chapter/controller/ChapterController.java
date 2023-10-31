package codesquad.bookkbookk.domain.chapter.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.domain.chapter.data.dto.CreateChapterRequest;
import codesquad.bookkbookk.domain.chapter.data.dto.CreateChapterResponse;
import codesquad.bookkbookk.domain.chapter.data.dto.ReadChapterResponse;
import codesquad.bookkbookk.domain.chapter.data.dto.UpdateChapterTitleRequest;
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

    @GetMapping("/{bookId}")
    public ResponseEntity<List<ReadChapterResponse>> readChapters(@PathVariable Long bookId) {
        List<ReadChapterResponse> response = chapterService.readChapters(bookId);

        return ResponseEntity.ok()
                .body(response);
    }

    @PatchMapping("/{chapterId}")
    public ResponseEntity<Void> updateChapter(@PathVariable Long chapterId,
                                              @RequestBody UpdateChapterTitleRequest updateChapterTitleRequest) {
         chapterService.updateChapter(chapterId, updateChapterTitleRequest);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{chapterId}")
    public ResponseEntity<Void> deleteChapter(@PathVariable Long chapterId) {
        chapterService.deleteChapter(chapterId);

        return ResponseEntity.ok().build();
    }

}
