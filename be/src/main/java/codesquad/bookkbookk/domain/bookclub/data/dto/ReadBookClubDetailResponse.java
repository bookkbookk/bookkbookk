package codesquad.bookkbookk.domain.bookclub.data.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.bookclub.data.type.BookClubStatus;
import codesquad.bookkbookk.domain.member.data.entity.Member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadBookClubDetailResponse {

    private final String name;
    private final BookClubStatus status;
    private final String profileImgUrl;
    private final LocalDateTime createdTime;
    private final ReadBookClubLastBook lastBook;
    private final List<ReadBookClubMember> members;

    public ReadBookClubDetailResponse(String name, BookClubStatus status, String profileImgUrl,
                                      LocalDateTime createdTime, ReadBookClubLastBook readBookClubLastBook,
                                      List<ReadBookClubMember> members) {
        this.name = name;
        this.status = status;
        this.profileImgUrl = profileImgUrl;
        this.createdTime = createdTime;
        this.lastBook = readBookClubLastBook;
        this.members = members;
    }

    @Getter
    protected static class ReadBookClubLastBook {

        private final String name;
        private final String author;

        protected ReadBookClubLastBook(String name, String author) {
            this.name = name;
            this.author = author;
        }

        protected static ReadBookClubLastBook from(Book book) {
            return new ReadBookClubLastBook(book.getTitle(), book.getAuthor());
        }

    }

    @Getter
    protected static class ReadBookClubMember {

        private final Long id;
        private final String nickname;
        private final String profileImgUrl;
        private final String email;

        @Builder
        private ReadBookClubMember(Long id, String nickname, String profileImgUrl, String email) {
            this.id = id;
            this.nickname = nickname;
            this.profileImgUrl = profileImgUrl;
            this.email = email;
        }

        protected static List<ReadBookClubMember> from(List<Member> members) {
            return members.stream()
                    .map(ReadBookClubMember::from)
                    .collect(Collectors.toUnmodifiableList());
        }

        private static ReadBookClubMember from(Member member) {
            return ReadBookClubMember.builder()
                    .id(member.getId())
                    .nickname(member.getNickname())
                    .profileImgUrl(member.getProfileImgUrl())
                    .email(member.getEmail())
                    .build();
        }

    }

}
