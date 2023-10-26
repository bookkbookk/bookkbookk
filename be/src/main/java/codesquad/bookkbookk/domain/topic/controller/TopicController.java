package codesquad.bookkbookk.domain.topic.controller;

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

import codesquad.bookkbookk.domain.topic.data.dto.CreateTopicRequest;
import codesquad.bookkbookk.domain.topic.data.dto.CreateTopicResponse;
import codesquad.bookkbookk.domain.topic.data.dto.ReadTopicResponse;
import codesquad.bookkbookk.domain.topic.data.dto.UpdateTopicTitleRequest;
import codesquad.bookkbookk.domain.topic.service.TopicService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;

    @PostMapping
    public ResponseEntity<CreateTopicResponse> createTopic(@RequestBody CreateTopicRequest request) {
        CreateTopicResponse response = topicService.createTopic(request);

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/{chapterId}")
    public ResponseEntity<List<ReadTopicResponse>> readTopicList(@PathVariable Long chapterId) {
        List<ReadTopicResponse> responses = topicService.readTopicLIst(chapterId);

        return ResponseEntity.ok()
                .body(responses);
    }

    @PatchMapping("/{topicId}")
    public ResponseEntity<String> updateTitle(@PathVariable Long topicId,
                                              @RequestBody UpdateTopicTitleRequest request) {
        topicService.updateTitle(topicId, request);

        return ResponseEntity.ok()
                .build();
    }

    @DeleteMapping("/{topicId}")
    public ResponseEntity<String> deleteTopic(@PathVariable Long topicId) {
        topicService.deleteTopic(topicId);

        return ResponseEntity.ok()
                .build();
    }

}
