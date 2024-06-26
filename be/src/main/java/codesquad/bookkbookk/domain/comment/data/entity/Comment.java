package codesquad.bookkbookk.domain.comment.data.entity;

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

import codesquad.bookkbookk.domain.bookmark.data.entity.Bookmark;
import codesquad.bookkbookk.domain.comment.data.dto.UpdateCommentRequest;
import codesquad.bookkbookk.domain.mapping.entity.CommentReaction;
import codesquad.bookkbookk.domain.member.data.entity.Member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bookmark_id", nullable = false)
    private Bookmark bookmark;

    @Column(name = "bookmark_id", nullable = false, insertable = false, updatable = false)
    private Long bookmarkId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;

    @Column(name = "writer_id", nullable = false, insertable = false, updatable = false)
    private Long writerId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contents;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private Instant createdTime;
    @UpdateTimestamp

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private Instant updatedTime;

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    List<CommentReaction> commentReactions = new ArrayList<>();

    public Comment(Bookmark bookmark, Member writer, String contents) {
        this.bookmark = bookmark;
        if (bookmark != null) this.bookmarkId = bookmark.getId();
        this.writer = writer;
        if (writer != null) this.writerId = writer.getId();
        this.contents = contents;
    }

    public boolean addReaction(CommentReaction commentReaction) {
        return this.commentReactions.add(commentReaction);
    }

    public void updateComment(UpdateCommentRequest updateCommentRequest) {
        this.contents = updateCommentRequest.getContent();
    }

}
