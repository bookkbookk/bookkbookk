package codesquad.bookkbookk.domain.topic.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.domain.bookmark.data.dto.ReadBookmarkResponse;
import codesquad.bookkbookk.domain.bookmark.data.dto.ReadBookmarkSliceResponse;
import codesquad.bookkbookk.domain.bookmark.service.BookmarkService;
import codesquad.bookkbookk.domain.topic.data.dto.CreateTopicRequest;
import codesquad.bookkbookk.domain.topic.data.dto.CreateTopicResponse;
import codesquad.bookkbookk.domain.topic.data.dto.UpdateTopicTitleRequest;
import codesquad.bookkbookk.domain.topic.service.TopicService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;
    private final BookmarkService bookmarkService;

    @PostMapping
    public ResponseEntity<CreateTopicResponse> createTopic(@RequestBody CreateTopicRequest request) {
        CreateTopicResponse response = topicService.createTopic(request);

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/{topicId}/bookmarks")
    public ResponseEntity<List<ReadBookmarkResponse>> readBookmarks(@PathVariable Long topicId) {
        List<ReadBookmarkResponse> responses = bookmarkService.readBookmarks(topicId);

        return ResponseEntity.ok()
                .body(responses);
    }

    @GetMapping("/{topicId}/bookmarks/slice")
    public ResponseEntity<ReadBookmarkSliceResponse> readBookmarks(@PathVariable Long topicId,
                                                                   @RequestParam Integer cursor,
                                                                   @RequestParam Integer size) {
        Pageable pageable = PageRequest.of(cursor, size);
        ReadBookmarkSliceResponse response = bookmarkService.readBookmarkSlices(topicId, pageable);

        return ResponseEntity.ok()
                .body(response);
    }

    @PatchMapping("/{topicId}")
    public ResponseEntity<Void> updateTitle(@PathVariable Long topicId, @RequestBody UpdateTopicTitleRequest request) {
        topicService.updateTitle(topicId, request);

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{topicId}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long topicId) {
        topicService.deleteTopic(topicId);

        return ResponseEntity.ok()
                .build();
    }

}
