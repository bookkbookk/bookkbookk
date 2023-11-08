package codesquad.bookkbookk.domain.bookclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import codesquad.bookkbookk.domain.bookclub.data.entity.BookClubMember;

public interface MemberBookClubRepository extends JpaRepository<BookClubMember, Long> {

    boolean existsByMemberIdAndBookClubId(Long memberId, Long bookClubId);

}
