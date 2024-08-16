package codesquad.bookkbookk.domain.bookmark.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.bookkbookk.common.error.exception.BookmarkNotFoundException;
import codesquad.bookkbookk.common.error.exception.BookmarkReactionExistsException;
import codesquad.bookkbookk.common.error.exception.BookmarkReactionNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberIsNotBookmarkWriterException;
import codesquad.bookkbookk.common.error.exception.MemberNotFoundException;
import codesquad.bookkbookk.common.error.exception.TopicNotFoundException;
import codesquad.bookkbookk.common.type.Reaction;
import codesquad.bookkbookk.domain.bookmark.data.dto.BookmarkFilter;
import codesquad.bookkbookk.domain.bookmark.data.dto.CreateBookmarkReactionRequest;
import codesquad.bookkbookk.domain.bookmark.data.dto.CreateBookmarkRequest;
import codesquad.bookkbookk.domain.bookmark.data.dto.DeleteBookmarkReactionRequest;
import codesquad.bookkbookk.domain.bookmark.data.dto.ReadBookmarkResponse;
import codesquad.bookkbookk.domain.bookmark.data.dto.ReadBookmarkSliceResponse;
import codesquad.bookkbookk.domain.bookmark.data.dto.ReadReactionsResponse;
import codesquad.bookkbookk.domain.bookmark.data.dto.UpdateBookmarkRequest;
import codesquad.bookkbookk.domain.bookmark.data.entity.Bookmark;
import codesquad.bookkbookk.domain.bookmark.repository.BookmarkRepository;
import codesquad.bookkbookk.domain.comment.data.dto.ReadCommentSliceResponse;
import codesquad.bookkbookk.domain.comment.repository.CommentRepository;
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

    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final TopicRepository topicRepository;
    private final BookmarkReactionRepository bookmarkReactionRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public void createBookmark(Long memberId, CreateBookmarkRequest createBookmarkRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Topic topic = topicRepository.findById(createBookmarkRequest.getTopicId())
                .orElseThrow(TopicNotFoundException::new);
        Bookmark bookmark = createBookmarkFromRequest(createBookmarkRequest, member, topic);

        bookmarkRepository.save(bookmark);
    }

    private Bookmark createBookmarkFromRequest(CreateBookmarkRequest request, Member member, Topic topic) {
        return Bookmark.builder()
                .writer(member)
                .topic(topic)
                .page(request.getPage())
                .contents(request.getContent())
                .build();
    }

    @Transactional(readOnly = true)
    public List<ReadBookmarkResponse> readBookmarks(Long topicId) {
        List<Bookmark> bookmarks = bookmarkRepository.findAllByTopicId(topicId);

        return ReadBookmarkResponse.from(bookmarks);
    }

    @Transactional(readOnly = true)
    public List<ReadBookmarkResponse> readBookmarksWithFilter(Long bookId, BookmarkFilter bookmarkFilter) {
        List<Bookmark> bookmarks = bookmarkRepository.findAllByFilter(bookId, bookmarkFilter);

        return ReadBookmarkResponse.from(bookmarks);
    }

    @Transactional(readOnly = true)
    public ReadBookmarkSliceResponse readBookmarkSlices(Long topicId, Pageable pageable) {
        Slice<Bookmark> bookmarkSlice = bookmarkRepository.findSliceByTopicId(topicId, pageable);
        List<Long> bookmarkIds = bookmarkSlice.getContent().stream()
                .map(Bookmark::getId)
                .collect(Collectors.toUnmodifiableList());
        Map<Long, ReadCommentSliceResponse> commentSliceMap =
                commentRepository.findSlicesByBookmarkIds(bookmarkIds, pageable.getPageSize());

        return ReadBookmarkSliceResponse.from(bookmarkSlice, commentSliceMap);
    }

    @Transactional
    public void updateBookmark(Long memberId, Long bookmarkId, UpdateBookmarkRequest updateBookmarkRequest) {
        Bookmark bookmark = bookmarkRepository.findById(bookmarkId).orElseThrow(BookmarkNotFoundException::new);
        if (!bookmarkRepository.existsByIdAndWriterId(bookmarkId, memberId)) {
            throw new MemberIsNotBookmarkWriterException();
        }

        bookmark.updateBookmark(updateBookmarkRequest);
    }

    @Transactional
    public void deleteBookmark(Long memberId, Long bookmarkId) {
        if (!bookmarkRepository.existsByIdAndWriterId(bookmarkId, memberId)) {
            throw new MemberIsNotBookmarkWriterException();
        }

        bookmarkRepository.deleteById(bookmarkId);
    }

    @Transactional
    public void createBookmarkReaction(Long memberId, Long bookmarkId, CreateBookmarkReactionRequest request) {
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
    public ReadReactionsResponse readBookmarkReactions(Long bookmarkId) {
        return ReadReactionsResponse.fromBookmarkReactions(bookmarkReactionRepository.findAllByBookmarkId(bookmarkId));
    }

}
