package codesquad.bookkbookk.domain.bookmark.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.bookkbookk.common.error.exception.BookmarkNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberIsNotBookmarkWriterException;
import codesquad.bookkbookk.common.error.exception.MemberNotFoundException;
import codesquad.bookkbookk.common.error.exception.TopicNotFoundException;
import codesquad.bookkbookk.domain.bookmark.data.dto.CreateBookmarkRequest;
import codesquad.bookkbookk.domain.bookmark.data.dto.UpdateBookmarkRequest;
import codesquad.bookkbookk.domain.bookmark.data.entity.Bookmark;
import codesquad.bookkbookk.domain.bookmark.repository.BookmarkRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;
import codesquad.bookkbookk.domain.topic.repository.TopicRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final TopicRepository topicRepository;

    @Transactional
    public void createBookmark(Long memberId, CreateBookmarkRequest createBookmarkRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Topic topic = topicRepository.findById(createBookmarkRequest.getTopicId())
                .orElseThrow(TopicNotFoundException::new);
        Bookmark bookmark = Bookmark.builder()
                .writer(member)
                .topic(topic)
                .title(createBookmarkRequest.getTitle())
                .content(createBookmarkRequest.getContent())
                .build();

        bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void updateBookmark(Long memberId, Long bookmarkId, UpdateBookmarkRequest updateBookmarkRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId).orElseThrow(BookmarkNotFoundException::new);

        if (member != bookmark.getWriter()) {
            throw new MemberIsNotBookmarkWriterException();
        }
        bookmark.updateBookmark(updateBookmarkRequest);
    }

}
