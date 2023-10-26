package codesquad.bookkbookk.domain.topic.data.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    public Topic(Long chapterId, String title) {
        this.chapterId = chapterId;
        this.title = title;
    }

    public void updateTitle(String title){
        this.title = title;
    }

}
