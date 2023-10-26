package codesquad.bookkbookk.domain.topic.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.bookkbookk.common.error.exception.TopicNotFoundException;
import codesquad.bookkbookk.domain.topic.data.dto.CreateTopicRequest;
import codesquad.bookkbookk.domain.topic.data.dto.CreateTopicResponse;
import codesquad.bookkbookk.domain.topic.data.dto.ReadTopicResponse;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;
import codesquad.bookkbookk.domain.topic.repository.TopicRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    @Transactional
    public CreateTopicResponse createTopic(CreateTopicRequest request) {
        Topic topic = Topic.from(request);
        topicRepository.save(topic);
        return new CreateTopicResponse(topic.getId());
    }

    public List<ReadTopicResponse> readTopicLIst(Long chapterId) {
        List<Topic> topicList = topicRepository.findByChapterId(chapterId);

        return ReadTopicResponse.from(topicList);
    }

    public void updateTitle(Long topicId, String title){
        Topic topic = topicRepository.findById(topicId).orElseThrow(TopicNotFoundException::new);
        topic.updateTitle(title);
    }

    public void deleteTopic(Long topicId){
        topicRepository.deleteById(topicId);
    }

}
