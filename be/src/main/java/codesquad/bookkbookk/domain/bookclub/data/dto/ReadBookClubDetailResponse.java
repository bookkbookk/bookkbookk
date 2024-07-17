package codesquad.bookkbookk.domain.bookclub.data.dto;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.member.data.entity.Member;

import lombok.Builder;
import lombok.Getter;

public abstract class ReadBookClubDetailResponse {

    public static List<ReadBookClubDetailResponse> from(List<BookClub> bookClubs) {
        return bookClubs.stream()
                .map(ReadBookClubDetailResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    public static ReadBookClubDetailResponse from(BookClub bookClub) {
        return bookClub.getStatus().from(bookClub);
    }

    @Getter
    protected static class ReadBookClubLastBook {

        private final String name;
        private final String author;

        protected ReadBookClubLastBook(String name, String author) {
            this.name = name;
            this.author = author;
        }

        protected static ReadBookClubLastBook from(List<Book> books) {
            return books.stream()
                    .max(Comparator.comparingLong(Book::getId))
                    .map(book -> new ReadBookClubLastBook(book.getTitle(), book.getAuthor()))
                    .orElse(null);
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
                    .profileImgUrl(member.getProfileImageUrl())
                    .email(member.getEmail())
                    .build();
        }

    }

}
