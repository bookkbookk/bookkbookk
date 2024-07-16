package codesquad.bookkbookk.domain.chapter.service;

import java.util.List;

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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChapterService {

    private static final int ALL_STATUS = 0;

    private final AuthorizationService authorizationService;

    private final ChapterRepository chapterRepository;
    private final BookRepository bookRepository;

    @Transactional
    public CreateChapterResponse createChaptersAndTopics(Long memberId, CreateChapterRequest request) {
        authorizationService.authorizeBookClubMembershipByBookId(request.getBookId(), memberId);

        Book book = bookRepository.findById(request.getBookId()).orElseThrow(BookNotFoundException::new);

        List<Chapter> chapters = request.toChaptersAndTopics(book);
        chapterRepository.saveAll(chapters);

        return CreateChapterResponse.from(chapters);
    }

    @Transactional(readOnly = true)
    public List<ReadChapterResponse> readChapters(Long memberId, Long bookId, int chapterStatusId) {
        authorizationService.authorizeBookClubMembershipByBookId(bookId, memberId);

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
        authorizationService.authorizeBookClubMembershipByChapterId(chapterId, memberId);

        Chapter chapter = chapterRepository.findById(chapterId).orElseThrow(ChapterNotFoundException::new);

        Chapter updated = chapter.update(request);
        return UpdateChapterResponse.from(updated);
    }

    @Transactional
    public void deleteChapter(Long memberId, Long chapterId) {
        authorizationService.authorizeBookClubMembershipByChapterId(chapterId, memberId);

        chapterRepository.deleteById(chapterId);
    }

}
