package codesquad.bookkbookk.domain.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import codesquad.bookkbookk.domain.auth.data.entity.MemberRefreshToken;
import codesquad.bookkbookk.domain.member.data.entity.Member;

public interface MemberRefreshTokenRepository extends JpaRepository<MemberRefreshToken, Long> {

    Optional<Member> findMemberByRefreshToken(String refreshToken);

}
