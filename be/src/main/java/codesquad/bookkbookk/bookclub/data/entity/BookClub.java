package codesquad.bookkbookk.bookclub.data.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_club_id")
    private Long id;
    @Column(name = "creator_id", nullable = false)
    private Long creatorId;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(name = "profile_img_url", nullable = false)
    private String profileImgUrl;
    @OneToMany(mappedBy = "bookClub")
    private List<MemberBookClub> memberBookClub;

    @Builder
    private BookClub(Long id, Long creatorId, String name, String profileImgUrl) {
        this.id = id;
        this.creatorId = creatorId;
        this.name = name;
        this.profileImgUrl = profileImgUrl;
    }

    public static BookClub from(Long creatorId, String name, String profileImgUrl){
        return BookClub.builder()
                .creatorId(creatorId)
                .name(name)
                .profileImgUrl(profileImgUrl)
                .build();
    }

}
