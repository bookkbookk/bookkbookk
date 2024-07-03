package codesquad.bookkbookk.domain.auth.repository;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import codesquad.bookkbookk.domain.auth.data.dto.BookClubMemberAuthInfo;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AuthorizationJdbcRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<BookClubMemberAuthInfo> authRowMapper = (resultSet, rowNum) -> {
        Long bookClubMemberId = resultSet.getLong("book_club_member_id");
        if (resultSet.wasNull()) {
            bookClubMemberId = null;
        }

        Long bookClubId = resultSet.getLong("book_club_id");
        if (resultSet.wasNull()) {
            bookClubId = null;
        }

        Long memberId = resultSet.getLong("member_id");
        if (resultSet.wasNull()) {
            memberId = null;
        }

        return new BookClubMemberAuthInfo(bookClubMemberId, bookClubId, memberId);
    };

    public List<BookClubMemberAuthInfo> findBookClubMemberAuthsByMemberIdAndBookClubId(Long bookClubId, Long memberId) {
        String nativeQuery = "(SELECT m.member_id, bc.book_club_id, bcm.book_club_member_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN book_club bc ON bcm.book_club_id = bc.book_club_id " +
                "INNER JOIN member m ON bcm.member_id = m.member_id " +
                "WHERE m.member_id = :memberId AND bc.book_club_id = :bookClubId " +
                "LIMIT 1) " +
                "UNION ALL " +
                "(SELECT m.member_id, bc.book_club_id, book_club_member_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN member m ON bcm.member_id = m.member_id " +
                "RIGHT JOIN book_club bc ON bcm.book_club_id = bc.book_club_id  " +
                "WHERE bc.book_club_id = :bookClubId " +
                "LIMIT 1) " +
                "UNION ALL " +
                "(SELECT m.member_id, bc.book_club_id, bcm.book_club_member_id " +
                "FROM book_club_member bcm " +
                "INNER JOIN book_club bc ON bcm.book_club_id = bc.book_club_id " +
                "RIGHT JOIN member m ON bcm.member_id = m.member_id " +
                "WHERE m.member_id = :memberId " +
                "LIMIT 1)";

        Map<String, Long> paramSource = Map.of("bookClubId", bookClubId, "memberId", memberId);

        return jdbcTemplate.query(nativeQuery, paramSource, authRowMapper);
    }

}
