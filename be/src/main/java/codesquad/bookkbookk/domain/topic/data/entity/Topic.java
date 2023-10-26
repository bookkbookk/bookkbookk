package codesquad.bookkbookk.domain.topic.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import codesquad.bookkbookk.domain.topic.data.dto.CreateTopicRequest;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_id")
    private Long id;

    private Long chapterId;

    private String title;

    private Topic(Long chapterId, String title) {
        this.chapterId = chapterId;
        this.title = title;
    }

    public static Topic from(CreateTopicRequest request) {
        return new Topic(request.getChapterId(), request.getTitle());
    }
}
