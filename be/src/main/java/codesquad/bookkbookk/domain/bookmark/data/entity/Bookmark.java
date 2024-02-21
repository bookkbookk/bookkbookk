package codesquad.bookkbookk.domain.bookmark.data.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import codesquad.bookkbookk.common.type.Reaction;
import codesquad.bookkbookk.domain.bookmark.data.dto.UpdateBookmarkRequest;
import codesquad.bookkbookk.domain.comment.data.entity.Comment;
import codesquad.bookkbookk.domain.mapping.entity.BookmarkReaction;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contents;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updatedTime;

    @OneToMany(mappedBy = "bookmark")
    private List<BookmarkReaction> bookmarkReactions = new ArrayList<>();
    @OneToMany(mappedBy = "bookmark")
    private List<Comment> comments = new ArrayList<>();

    @Builder
    private Bookmark(Member writer, Topic topic, String title, String contents) {
        this.writer = writer;
        this.topic = topic;
        this.title = title;
        this.contents = contents;
    }

    public void updateBookmark(UpdateBookmarkRequest updateBookmarkRequest) {
        this.title = updateBookmarkRequest.getTitle();
        this.contents = updateBookmarkRequest.getContent();
    }

    public List<Reaction> getReactions() {
        return bookmarkReactions.stream()
                .map(BookmarkReaction::getReaction)
                .collect(Collectors.toUnmodifiableList());
    }

}
