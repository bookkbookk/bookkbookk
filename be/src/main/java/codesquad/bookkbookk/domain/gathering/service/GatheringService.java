package codesquad.bookkbookk.domain.gathering.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.bookkbookk.common.error.exception.BookClubNotFoundException;
import codesquad.bookkbookk.common.error.exception.BookNotFoundException;
import codesquad.bookkbookk.common.error.exception.GatheringNotFoundException;
import codesquad.bookkbookk.domain.auth.service.AuthorizationService;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.book.repository.BookRepository;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.gathering.data.dto.CreateGatheringsRequest;
import codesquad.bookkbookk.domain.gathering.data.dto.ReadGatheringResponse;
import codesquad.bookkbookk.domain.gathering.data.dto.UpdateGatheringRequest;
import codesquad.bookkbookk.domain.gathering.data.dto.UpdateGatheringResponse;
import codesquad.bookkbookk.domain.gathering.data.entity.Gathering;
import codesquad.bookkbookk.domain.gathering.repository.GatheringRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GatheringService {

    private final AuthorizationService authorizationService;

    private final GatheringRepository gatheringRepository;
    private final BookRepository bookRepository;
    private final BookClubRepository bookClubRepository;

    @Transactional
    public void createGathering(Long memberId, Long bookClubId, CreateGatheringsRequest request) {
        authorizationService.authorizeBookClubMembershipByBookClubId(bookClubId, memberId);

        Book book = bookRepository.findById(request.getBookId()).orElseThrow(BookNotFoundException::new);
        BookClub bookClub = bookClubRepository.findById(bookClubId).orElseThrow(BookClubNotFoundException::new);

        List<Gathering> gatherings = request.getGatherings().stream()
                .map(gathering -> new Gathering(book, gathering.getDateTime(), gathering.getPlace()))
                .sorted(Comparator.comparing(Gathering::getStartTime))
                .collect(Collectors.toUnmodifiableList());
        gatheringRepository.saveAll(gatherings);

        bookClub.updateUpcomingGatheringDate(gatherings.get(0).getStartTime());
    }

    @Transactional(readOnly = true)
    public List<ReadGatheringResponse> readGatherings(Long memberId, Long bookClubId) {
        authorizationService.authorizeBookClubMembershipByBookClubId(bookClubId, memberId);

        return ReadGatheringResponse.from(gatheringRepository.findAllByBookClubId(bookClubId));
    }

    @Transactional
    public UpdateGatheringResponse updateGathering(Long memberId, Long gatheringId, UpdateGatheringRequest request) {
        authorizationService.authorizeBookClubMembershipByGatheringId(gatheringId, memberId);

        Gathering gathering = gatheringRepository.findById(gatheringId).orElseThrow(GatheringNotFoundException::new);

        gathering.update(request);
        return UpdateGatheringResponse.from(gathering);
    }

    @Transactional
    public void deleteGathering(Long memberId, Long gatheringId) {
        authorizationService.authorizeBookClubMembershipByGatheringId(gatheringId, memberId);

        gatheringRepository.deleteById(gatheringId);
    }

}
