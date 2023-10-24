package codesquad.bookkbookk.domain.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import codesquad.bookkbookk.domain.auth.data.entity.MemberRefreshToken;

public interface MemberRefreshTokenRepository extends JpaRepository<MemberRefreshToken, Long> {

    Optional<MemberRefreshToken> findByRefreshToken(String refreshToken);
    void deleteByMemberId(Long memberId);

}
