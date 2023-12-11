package codesquad.bookkbookk.domain.member.data.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import codesquad.bookkbookk.domain.auth.data.type.LoginType;
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
    @Column(name = "member_id")
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(name = "login_type", nullable = false)
    private LoginType loginType;
    @Column(unique = true, nullable = false)
    private String nickname;
    @Column(name = "profile_img_url", nullable = false)
    private String profileImgUrl;
    @OneToMany(mappedBy = "member")
    private List<BookClubMember> memberBookClubs = new ArrayList<>();
    @OneToMany(mappedBy = "member")
    private List<MemberBook> memberBooks = new ArrayList<>();

    @Builder
    private Member(String email, LoginType loginType, String nickname, String profileImgUrl) {
        this.email = email;
        this.loginType = loginType;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfileImgUrl(String profileImgUrl){
        this.profileImgUrl = profileImgUrl;
    }

    public List<BookClub> getBookClubs() {
        return memberBookClubs.stream()
                .map(BookClubMember::getBookClub)
                .collect(Collectors.toUnmodifiableList());
    }

}
