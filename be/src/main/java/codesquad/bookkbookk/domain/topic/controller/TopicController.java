package codesquad.bookkbookk.domain.topic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public ResponseEntity<CreateTopicResponse> createTopic(@RequestBody CreateTopicRequest request) {
        CreateTopicResponse response = topicService.createTopic(request);

        return ResponseEntity.ok()
                .body(response);
    }

    @PatchMapping("/{topicId}")
    public ResponseEntity<Void> updateTitle(@PathVariable Long topicId,
                                              @RequestBody UpdateTopicTitleRequest request) {
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
