package codesquad.bookkbookk.domain.mapping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import codesquad.bookkbookk.domain.mapping.entity.BookClubMember;

public interface BookClubMemberRepository extends JpaRepository<BookClubMember, Long> {

    boolean existsByBookClubIdAndMemberId(Long bookClubId, Long memberId);

}
