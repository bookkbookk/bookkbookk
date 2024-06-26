package codesquad.bookkbookk.domain.gathering.repository;

import java.util.List;

import codesquad.bookkbookk.domain.gathering.data.entity.Gathering;

public interface GatheringCustomRepository {

    List<Gathering> findAllByBookClubId(Long bookClubId);

}
