package codesquad.bookkbookk.domain.bookmark.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.bookkbookk.common.error.exception.BookmarkNotFoundException;
import codesquad.bookkbookk.common.error.exception.BookmarkReactionExistsException;
import codesquad.bookkbookk.common.error.exception.BookmarkReactionNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberNotFoundException;
import codesquad.bookkbookk.common.error.exception.TopicNotFoundException;
import codesquad.bookkbookk.common.type.Reaction;
import codesquad.bookkbookk.domain.auth.service.AuthorizationService;
import codesquad.bookkbookk.domain.bookmark.data.dto.CreateBookmarkReactionRequest;
import codesquad.bookkbookk.domain.bookmark.data.dto.CreateBookmarkRequest;
import codesquad.bookkbookk.domain.bookmark.data.dto.DeleteBookmarkReactionRequest;
import codesquad.bookkbookk.domain.bookmark.data.dto.ReadReactionsResponse;
import codesquad.bookkbookk.domain.bookmark.data.dto.UpdateBookmarkRequest;
import codesquad.bookkbookk.domain.bookmark.data.entity.Bookmark;
import codesquad.bookkbookk.domain.bookmark.repository.BookmarkRepository;
import codesquad.bookkbookk.domain.mapping.entity.BookmarkReaction;
import codesquad.bookkbookk.domain.mapping.repository.BookmarkReactionRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;
import codesquad.bookkbookk.domain.topic.repository.TopicRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final AuthorizationService authorizationService;

    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final TopicRepository topicRepository;
    private final BookmarkReactionRepository bookmarkReactionRepository;

    @Transactional
    public void createBookmark(Long memberId, CreateBookmarkRequest createBookmarkRequest) {
        authorizationService.authorizeBookClubMembershipByTopicId(memberId, createBookmarkRequest.getTopicId());

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Topic topic = topicRepository.findById(createBookmarkRequest.getTopicId())
                .orElseThrow(TopicNotFoundException::new);
        Bookmark bookmark = Bookmark.builder()
                .writer(member)
                .topic(topic)
                .title(createBookmarkRequest.getTitle())
                .contents(createBookmarkRequest.getContent())
                .build();

        bookmarkRepository.save(bookmark);
    }

    @Transactional
    public void updateBookmark(Long memberId, Long bookmarkId, UpdateBookmarkRequest updateBookmarkRequest) {
        authorizationService.authorizeBookmarkWriter(memberId, bookmarkId);

        Bookmark bookmark = bookmarkRepository.findById(bookmarkId).orElseThrow(BookmarkNotFoundException::new);

        bookmark.updateBookmark(updateBookmarkRequest);
    }

    @Transactional
    public void deleteBookmark(Long memberId, Long bookmarkId) {
        authorizationService.authorizeBookmarkWriter(memberId, bookmarkId);

        bookmarkRepository.deleteById(bookmarkId);
    }

    @Transactional
    public void createBookmarkReaction(Long memberId, Long bookmarkId, CreateBookmarkReactionRequest request) {
        authorizationService.authorizeBookClubMembershipByBookmarkId(memberId, bookmarkId);

        Reaction reaction = Reaction.of(request.getReactionName());
        if (bookmarkReactionRepository.existsByBookmarkIdAndReactorIdAndReaction(bookmarkId, memberId, reaction)) {
            throw new BookmarkReactionExistsException();
        }
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId).orElseThrow(BookmarkNotFoundException::new);

        BookmarkReaction bookmarkReaction = new BookmarkReaction(bookmark, member, reaction);
        bookmarkReactionRepository.save(bookmarkReaction);
        bookmark.getBookmarkReactions().add(bookmarkReaction);
    }

    @Transactional
    public void deleteBookmarkReaction(Long memberId, Long bookmarkId, DeleteBookmarkReactionRequest request) {
        Reaction reaction = Reaction.of(request.getReactionName());

        BookmarkReaction bookmarkReaction = bookmarkReactionRepository
                .findByBookmarkIdAndReactorIdAndReaction(bookmarkId, memberId, reaction)
                .orElseThrow(BookmarkReactionNotFoundException::new);
        bookmarkReaction.getBookmark().getBookmarkReactions().remove(bookmarkReaction);
        bookmarkReactionRepository.delete(bookmarkReaction);
    }

    @Transactional(readOnly = true)
    public ReadReactionsResponse readBookmarkReactions(Long memberId, Long bookmarkId) {
        authorizationService.authorizeBookClubMembershipByBookmarkId(memberId, bookmarkId);

        Bookmark bookmark = bookmarkRepository.findById(bookmarkId).orElseThrow(BookmarkNotFoundException::new);
        List<BookmarkReaction> bookmarkReactions = bookmark.getBookmarkReactions();

        return ReadReactionsResponse.fromBookmarkReactions(bookmarkReactions);
    }

}
