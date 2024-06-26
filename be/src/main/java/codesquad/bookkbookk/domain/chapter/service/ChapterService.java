package codesquad.bookkbookk.domain.chapter.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.bookkbookk.common.error.exception.BookNotFoundException;
import codesquad.bookkbookk.common.error.exception.ChapterNotFoundException;
import codesquad.bookkbookk.common.type.Status;
import codesquad.bookkbookk.domain.auth.service.AuthorizationService;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.book.repository.BookRepository;
import codesquad.bookkbookk.domain.chapter.data.dto.CreateChapterRequest;
import codesquad.bookkbookk.domain.chapter.data.dto.CreateChapterResponse;
import codesquad.bookkbookk.domain.chapter.data.dto.ReadChapterResponse;
import codesquad.bookkbookk.domain.chapter.data.dto.UpdateChapterRequest;
import codesquad.bookkbookk.domain.chapter.data.dto.UpdateChapterResponse;
import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;
import codesquad.bookkbookk.domain.chapter.repository.ChapterRepository;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;
import codesquad.bookkbookk.domain.topic.repository.TopicRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChapterService {

    private static final int ALL_STATUS = 0;

    private final AuthorizationService authorizationService;

    private final ChapterRepository chapterRepository;
    private final TopicRepository topicRepository;
    private final BookRepository bookRepository;

    @Transactional
    public CreateChapterResponse createChapter(Long memberId, CreateChapterRequest request) {
        authorizationService.authorizeBookClubMembershipByBookId(memberId, request.getBookId());

        Book book = bookRepository.findById(request.getBookId()).orElseThrow(BookNotFoundException::new);
        List<CreateChapterRequest.ChapterDataDTO> dataList = request.toEntities(book);

        List<Chapter> chapterList = dataList.stream()
                .map(CreateChapterRequest.ChapterDataDTO::getChapter)
                .collect(Collectors.toUnmodifiableList());
        chapterRepository.saveAll(chapterList);

        List<Topic> topicList = dataList.stream()
                .flatMap(dto -> dto.getTopicList().stream())
                .collect(Collectors.toUnmodifiableList());
        topicRepository.saveAll(topicList);

        return new CreateChapterResponse(chapterList.stream()
                .map(Chapter::getId)
                .collect(Collectors.toUnmodifiableList()));
    }

    @Transactional(readOnly = true)
    public List<ReadChapterResponse> readChapters(Long memberId, Long bookId, int chapterStatusId) {
        authorizationService.authorizeBookClubMembershipByBookId(memberId, bookId);

        Status chapterStatus;
        if (chapterStatusId == ALL_STATUS) {
            chapterStatus = null;
        } else {
            chapterStatus = Status.of(chapterStatusId);
        }

        return ReadChapterResponse.from(chapterRepository.findAllByBookIdAndStatus(bookId, chapterStatus));
    }

    @Transactional
    public UpdateChapterResponse updateChapter(Long memberId, Long chapterId, UpdateChapterRequest request) {
        authorizationService.authorizeBookClubMembershipByChapterId(memberId, chapterId);

        Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(ChapterNotFoundException::new);

        Chapter updated = chapter.update(request);
        return UpdateChapterResponse.from(updated);
    }

    @Transactional
    public void deleteChapter(Long memberId, Long chapterId) {
        authorizationService.authorizeBookClubMembershipByChapterId(memberId, chapterId);

        chapterRepository.deleteById(chapterId);
    }

}
