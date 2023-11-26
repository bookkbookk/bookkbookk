package codesquad.bookkbookk.domain.topic.data.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import codesquad.bookkbookk.domain.bookmark.data.entity.Bookmark;
import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;

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
    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;
    private String title;

    @OneToMany(mappedBy = "topic")
    private List<Bookmark> bookmarks = new ArrayList<>();

    public Topic(Chapter chapter, String title) {
        this.chapter = chapter;
        this.title = title;
    }

    public void updateTitle(String title){
        this.title = title;
    }

}
