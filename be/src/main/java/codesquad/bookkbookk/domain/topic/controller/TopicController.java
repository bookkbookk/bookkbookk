package codesquad.bookkbookk.domain.topic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import codesquad.bookkbookk.common.resolver.MemberId;
import codesquad.bookkbookk.domain.topic.data.dto.CreateTopicRequest;
import codesquad.bookkbookk.domain.topic.data.dto.CreateTopicResponse;
import codesquad.bookkbookk.domain.topic.service.TopicService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;

    @PostMapping
    public ResponseEntity<CreateTopicResponse> createTopic(@MemberId Long memberId,
                                                           @RequestBody CreateTopicRequest request) {
        CreateTopicResponse response = topicService.createTopic(memberId, request);

        return ResponseEntity.ok()
                .body(response);
    }

}
