package codesquad.bookkbookk.domain.topic.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.bookkbookk.common.error.exception.ChapterNotFoundException;
import codesquad.bookkbookk.common.error.exception.TopicNotFoundException;
import codesquad.bookkbookk.domain.auth.service.AuthorizationService;
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

    private final AuthorizationService authorizationService;

    private final TopicRepository topicRepository;
    private final ChapterRepository chapterRepository;

    @Transactional
    public CreateTopicResponse createTopic(Long memberId, CreateTopicRequest request) {
        authorizationService.authorizeBookClubMembershipByChapterId(memberId, request.getChapterId());

        Chapter chapter = chapterRepository.findById(request.getChapterId())
                .orElseThrow(ChapterNotFoundException::new);

        Topic topic = new Topic(chapter, request.getTitle());
        topicRepository.save(topic);
        return new CreateTopicResponse(topic.getId());
    }

    public List<ReadTopicResponse> readTopicLIst(Long memberId, Long chapterId) {
        authorizationService.authorizeBookClubMembershipByChapterId(memberId, chapterId);

        List<Topic> topicList = topicRepository.findByChapterId(chapterId);

        return ReadTopicResponse.from(topicList);
    }

    @Transactional(readOnly = false)
    public void updateTitle(Long memberId, Long topicId, UpdateTopicTitleRequest request) {
        authorizationService.authorizeBookClubMembershipByTopicId(memberId, topicId);

        Topic topic = topicRepository.findById(topicId).orElseThrow(TopicNotFoundException::new);

        topic.updateTitle(request.getTitle());
    }

    public void deleteTopic(Long memberId, Long topicId) {
        authorizationService.authorizeBookClubMembershipByTopicId(memberId, topicId);

        topicRepository.deleteById(topicId);
    }

}
