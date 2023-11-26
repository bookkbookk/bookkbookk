package codesquad.bookkbookk.domain.chapter.data.entity;

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

import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.chapter.data.dto.UpdateChapterTitleRequest;
import codesquad.bookkbookk.domain.chapter.data.type.ChapterStatus;
import codesquad.bookkbookk.domain.topic.data.entity.Topic;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chapter_id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    private String title;
    @Column(name = "chapter_status")
    private ChapterStatus status;

    @OneToMany(mappedBy = "chapter")
    private List<Topic> topics = new ArrayList<>();

    public Chapter(Book book, String title) {
        this.book = book;
        this.title = title;
        this.status = ChapterStatus.BEFORE_READING;
    }

    public void updateTitle(UpdateChapterTitleRequest updateChapterTitleRequest) {
        this.title = updateChapterTitleRequest.getTitle();
    }

}
