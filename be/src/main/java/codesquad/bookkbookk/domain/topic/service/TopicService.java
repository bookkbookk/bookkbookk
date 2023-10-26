package codesquad.bookkbookk.domain.topic.service;

import org.springframework.stereotype.Service;

import codesquad.bookkbookk.domain.topic.data.dto.CreateTopicRequest;
import codesquad.bookkbookk.domain.topic.data.dto.CreateTopicResponse;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;
import codesquad.bookkbookk.domain.topic.repository.TopicRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;

    public CreateTopicResponse createTopic(Long memberId, CreateTopicRequest request) {
        Topic topic = Topic.from(request);
        topicRepository.save(topic);
        return new CreateTopicResponse(topic.getId());
    }

}
