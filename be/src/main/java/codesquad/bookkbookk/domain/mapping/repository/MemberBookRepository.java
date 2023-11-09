package codesquad.bookkbookk.domain.mapping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import codesquad.bookkbookk.domain.mapping.entity.MemberBook;

public interface MemberBookRepository extends JpaRepository<MemberBook, Long> {

}
