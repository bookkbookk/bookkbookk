package codesquad.bookkbookk.domain.bookclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import codesquad.bookkbookk.domain.bookclub.data.entity.BookClubMember;

public interface BookClubMemberRepository extends JpaRepository<BookClubMember, Long> {

    boolean existsByBookClubIdAndMemberId(Long bookClubId, Long memberId);

}
