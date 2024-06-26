package codesquad.bookkbookk.domain.book.data.entity;

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

import codesquad.bookkbookk.common.error.exception.MalformedIsbnException;
import codesquad.bookkbookk.common.type.Status;
import codesquad.bookkbookk.domain.book.data.dto.UpdateBookStatusRequest;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.chapter.data.entity.Chapter;
import codesquad.bookkbookk.domain.gathering.data.entity.Gathering;
import codesquad.bookkbookk.domain.mapping.entity.MemberBook;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Book {

    private static final List<Long> ISBN_10_VALIDATE_NUMBERS = List.of(10L, 9L, 8L, 7L, 6L, 5L, 4L, 3L, 2L);

    private static final List<Long> ISBN_13_VALIDATE_NUMBERS = List.of(1L, 3L, 1L, 3L, 1L, 3L, 1L, 3L, 1L, 3L, 1L, 3L);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_club_id", nullable = false)
    private BookClub bookClub;

    @Column(name = "book_club_id", nullable = false, insertable = false, updatable = false)
    private Long bookClubId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String cover;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String category;

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Gathering> gatherings = new ArrayList<>();

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Chapter> chapters = new ArrayList<>();

    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberBook> bookMembers = new ArrayList<>();

    @Builder
    private Book(String isbn, BookClub bookClub, String title, String cover, String author, String category) {
        this.isbn = validateAndFormatISBN(isbn);
        this.bookClub = bookClub;
        if (bookClub != null) this.bookClubId = bookClub.getId();
        this.title = title;
        this.cover = cover;
        this.author = author;
        this.category = category;
        this.status = Status.BEFORE_READING;
    }

    public boolean addGathering(Gathering gathering) {
        return this.getGatherings().add(gathering);
    }

    public boolean addChapter(Chapter chapter) {
        return this.getChapters().add(chapter);
    }

    public boolean addMember(MemberBook bookMember) {
        return this.getBookMembers().add(bookMember);
    }

    private String validateAndFormatISBN(String isbn) {
        String impliedISBN = isbn.replace("-", "");
        if (impliedISBN.length() != 13 && impliedISBN.length() != 10) {
            throw new MalformedIsbnException();
        }

        long checkDigit = extractCheckDigit(impliedISBN);

        if (impliedISBN.length() == 10) {
            validateISBN(impliedISBN, ISBN_10_VALIDATE_NUMBERS, 11, checkDigit);
        } else validateISBN(impliedISBN, ISBN_13_VALIDATE_NUMBERS, 10, checkDigit);

        // 데이터를 통일하기 위해 x로 끝나는 isbn10인 경우 끝문자를 대문자로 변환
        return impliedISBN.toUpperCase();
    }

    private long extractCheckDigit(String isbn) {
        char end = isbn.charAt(isbn.length() - 1);

        if (end == 'x' || end == 'X') {
            return 10;
        }
        if (Character.isDigit(end)) {
            return Character.getNumericValue(end);
        }
        throw new MalformedIsbnException();
    }

    private void validateISBN(String isbn, List<Long> validateNumbers, long divisor, long checkDigit) {
        long target = 0;
        // isbn의 뒤에서 두번째부터 계산하기 위해서 index를 isbn 길이의 -2로 초기화
        int index = isbn.length() - 2;
        long sum = 0;

        // isbn의 끝자리 - 1 까지 숫자를 추출
        try {
            target = Long.parseLong(isbn.substring(0, isbn.length() - 1));
        } catch (NumberFormatException e) {
            throw new MalformedIsbnException();
        }

        // 계산해서 구한 target과 validate numbers의 곱의 합을 구함.
        while (target > 0) {
            sum += (target % 10) * validateNumbers.get(index);
            target /= 10;
            index -= 1;
        }

        // sum을 divisor로 나눈 나머지와 diver 와 checkDigit이 동일 하지 않으면 검증 실패
        if ((sum + checkDigit) % divisor != 0) {
            throw new MalformedIsbnException();
        }
    }

    public void updateStatus(UpdateBookStatusRequest request) {
        this.status = Status.of(request.getStatusId());
    }

}
