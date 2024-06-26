package codesquad.bookkbookk.domain.member.data.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import codesquad.bookkbookk.domain.auth.data.type.LoginType;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.mapping.entity.BookClubMember;
import codesquad.bookkbookk.domain.mapping.entity.MemberBook;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LoginType loginType;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String profileImageUrl;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookClubMember> memberBookClubs = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberBook> memberBooks = new ArrayList<>();

    @Builder
    private Member(String email, LoginType loginType, String nickname, String profileImageUrl) {
        this.email = email;
        this.loginType = loginType;
        this.nickname = nickname;
        this.profileImageUrl = profileImageUrl;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfileImgUrl(String profileImgUrl){
        this.profileImageUrl = profileImgUrl;
    }

    public List<BookClub> getBookClubs() {
        return memberBookClubs.stream()
                .map(BookClubMember::getBookClub)
                .collect(Collectors.toUnmodifiableList());
    }
    public List<Book> getBooks() {
        return memberBooks.stream()
                .map(MemberBook::getBook)
                .collect(Collectors.toUnmodifiableList());
    }

    public boolean addBookClub(BookClubMember bookClubMember) {
        return this.memberBookClubs.add(bookClubMember);
    }

    public boolean addBook(MemberBook memberBook) {
        return this.memberBooks.add(memberBook);
    }
    public boolean addBooks(List<MemberBook> memberBooks) {
        return this.memberBooks.addAll(memberBooks);
    }

}
