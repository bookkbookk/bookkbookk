package codesquad.bookkbookk.domain.topic.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.bookkbookk.common.error.exception.ChapterNotFoundException;
import codesquad.bookkbookk.common.error.exception.TopicNotFoundException;
import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;
import codesquad.bookkbookk.domain.chapter.repository.ChapterRepository;
import codesquad.bookkbookk.domain.topic.data.dto.CreateTopicRequest;
import codesquad.bookkbookk.domain.topic.data.dto.CreateTopicResponse;
import codesquad.bookkbookk.domain.topic.data.dto.ReadTopicResponse;
import codesquad.bookkbookk.domain.topic.data.dto.UpdateTopicTitleRequest;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;
import codesquad.bookkbookk.domain.topic.repository.TopicRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final ChapterRepository chapterRepository;

    @Transactional
    public CreateTopicResponse createTopic(CreateTopicRequest request) {
        Chapter chapter = chapterRepository.findById(request.getChapterId())
                .orElseThrow(ChapterNotFoundException::new);

        Topic topic = request.toTopic(chapter);
        topicRepository.save(topic);
        return new CreateTopicResponse(topic.getId());
    }

    @Transactional(readOnly = true)
    public List<ReadTopicResponse> readTopicLIst(Long chapterId) {
        List<Topic> topicList = topicRepository.findByChapterId(chapterId);

        return ReadTopicResponse.from(topicList);
    }

    @Transactional
    public void updateTitle(Long topicId, UpdateTopicTitleRequest request) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(TopicNotFoundException::new);

        topic.updateTitle(request.getTitle());
    }

    public void deleteTopic(Long topicId) {
        topicRepository.deleteById(topicId);
    }

}
