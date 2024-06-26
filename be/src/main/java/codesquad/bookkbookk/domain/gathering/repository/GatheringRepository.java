package codesquad.bookkbookk.domain.gathering.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import codesquad.bookkbookk.domain.gathering.data.entity.Gathering;

public interface GatheringRepository extends JpaRepository<Gathering, Long>, GatheringCustomRepository {

}
