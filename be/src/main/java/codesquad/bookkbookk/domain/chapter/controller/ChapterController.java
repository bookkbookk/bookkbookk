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

import codesquad.bookkbookk.common.resolver.MemberId;
import codesquad.bookkbookk.domain.chapter.data.dto.CreateChapterRequest;
import codesquad.bookkbookk.domain.chapter.data.dto.CreateChapterResponse;
import codesquad.bookkbookk.domain.chapter.data.dto.UpdateChapterRequest;
import codesquad.bookkbookk.domain.chapter.data.dto.UpdateChapterResponse;
import codesquad.bookkbookk.domain.chapter.service.ChapterService;
import codesquad.bookkbookk.domain.topic.data.dto.ReadTopicResponse;
import codesquad.bookkbookk.domain.topic.service.TopicService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chapters")
public class ChapterController {

    private final ChapterService chapterService;
    private final TopicService topicService;

    @PostMapping
    public ResponseEntity<CreateChapterResponse> createChapter(@RequestBody CreateChapterRequest request) {
        CreateChapterResponse response = chapterService.createChaptersAndTopics(request);

        return ResponseEntity.ok()
                .body(response);
    }

    @PatchMapping("/{chapterId}")
    public ResponseEntity<UpdateChapterResponse> updateChapter(@PathVariable Long chapterId,
                                                               @RequestBody UpdateChapterRequest request) {
        UpdateChapterResponse response = chapterService.updateChapter(chapterId, request);

        return ResponseEntity.ok()
                .body(response);
    }

    @DeleteMapping("/{chapterId}")
    public ResponseEntity<Void> deleteChapter(@MemberId Long memberId, @PathVariable Long chapterId) {
        chapterService.deleteChapter(memberId, chapterId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{chapterId}/topics")
    public ResponseEntity<List<ReadTopicResponse>> readTopicList(@PathVariable Long chapterId) {
        List<ReadTopicResponse> responses = topicService.readTopicLIst(chapterId);

        return ResponseEntity.ok()
                .body(responses);
    }

}
