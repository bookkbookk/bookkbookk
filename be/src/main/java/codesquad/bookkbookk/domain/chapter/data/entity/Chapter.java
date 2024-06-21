package codesquad.bookkbookk.domain.chapter.data.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import codesquad.bookkbookk.common.type.Status;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.chapter.data.dto.UpdateChapterRequest;
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
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    private String title;

    @OneToMany(mappedBy = "chapter", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Topic> topics = new ArrayList<>();

    public Chapter(Book book, String title) {
        this.book = book;
        this.title = title;
        this.status = Status.BEFORE_READING;
    }

    public boolean addTopic(Topic topic) {
        return this.topics.add(topic);
    }

    public Chapter update(UpdateChapterRequest request) {
        if (request.getTitle() != null) {
            this.title = request.getTitle();
        }
        if (request.getStatusId() != null) {
            this.status = Status.of(request.getStatusId());
        }

        return this;
    }

}
