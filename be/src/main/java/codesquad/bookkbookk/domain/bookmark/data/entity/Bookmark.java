package codesquad.bookkbookk.domain.bookmark.data.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
    @Column(name = "bookmark_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member writer;
    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;
    private String title;
    @Lob
    private String content;
    @CreationTimestamp
    @Column(name = "create_at")
    private LocalDateTime createAt;
    @UpdateTimestamp
    @Column(name = "update_at")
    private LocalDateTime updateAt;

    @OneToMany(mappedBy = "bookmark")
    List<BookmarkReaction> bookmarkReactions = new ArrayList<>();
    @OneToMany(mappedBy = "bookmark")
    List<Comment> comments = new ArrayList<>();

    @Builder
    private Bookmark(Member writer, Topic topic, String title, String content) {
        this.writer = writer;
        this.topic = topic;
        this.title = title;
        this.content = content;
    }

    public void updateBookmark(UpdateBookmarkRequest updateBookmarkRequest) {
        this.title = updateBookmarkRequest.getTitle();
        this.content = updateBookmarkRequest.getContent();
    }

}
