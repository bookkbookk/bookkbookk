package codesquad.bookkbookk.domain.bookmark.data.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
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
    @Column(name = "bookmark_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "writer_id", nullable = false)
    private Member writer;

    @Column(name = "writer_id", nullable = false, insertable = false, updatable = false)
    private Long writerId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Column(name = "topic_id", nullable = false, insertable = false, updatable = false)
    private Long topicId;

    @Column(nullable = false)
    private Integer page;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contents;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private Instant createdTime;

    @UpdateTimestamp
    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private Instant updatedTime;

    @OneToMany(mappedBy = "bookmark", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookmarkReaction> bookmarkReactions = new ArrayList<>();

    @OneToMany(mappedBy = "bookmark", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder
    private Bookmark(Member writer, Topic topic, Integer page, String contents) {
        this.writer = writer;
        if (writer != null) this.writerId = writer.getId();
        this.topic = topic;
        if (topic != null) this.topicId = topic.getId();
        this.page = page;
        this.contents = contents;
    }

    public boolean addComment(Comment comment) {
        return this.comments.add(comment);
    }

    public boolean addReaction(BookmarkReaction bookmarkReaction) {
        return this.bookmarkReactions.add(bookmarkReaction);
    }

    public void updateBookmark(UpdateBookmarkRequest updateBookmarkRequest) {
        this.page = updateBookmarkRequest.getPage();
        this.contents = updateBookmarkRequest.getContent();
    }

}
