package codesquad.bookkbookk.domain.bookmark.data.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;

import lombok.Builder;

@Entity
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Builder
    private Bookmark(Member writer, Topic topic, String title, String content) {
        this.writer = writer;
        this.topic = topic;
        this.title = title;
        this.content = content;
    }

}
