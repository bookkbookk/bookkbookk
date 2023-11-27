package codesquad.bookkbookk.domain.bookclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import codesquad.bookkbookk.domain.bookclub.data.entity.MemberBookClub;

public interface MemberBookClubRepository extends JpaRepository<MemberBookClub, Long> {

    boolean existsByMemberIdAndBookClubId(Long memberId, Long bookClubId);

}
