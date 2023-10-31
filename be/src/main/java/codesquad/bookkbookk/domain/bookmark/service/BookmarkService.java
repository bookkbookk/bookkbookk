package codesquad.bookkbookk.domain.bookmark.service;

import org.springframework.stereotype.Service;

import codesquad.bookkbookk.common.error.exception.MemberNotFoundException;
import codesquad.bookkbookk.common.error.exception.TopicNotFoundException;
import codesquad.bookkbookk.domain.bookmark.data.dto.CreateBookmarkRequest;
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



}
