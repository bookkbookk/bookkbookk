package codesquad.bookkbookk.domain.gathering.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.bookkbookk.common.error.exception.BookClubNotFoundException;
import codesquad.bookkbookk.common.error.exception.BookNotFoundException;
import codesquad.bookkbookk.common.error.exception.GatheringNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberNotInBookClubException;
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
import codesquad.bookkbookk.domain.mapping.repository.BookClubMemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GatheringService {

    private final GatheringRepository gatheringRepository;
    private final BookRepository bookRepository;
    private final BookClubRepository bookClubRepository;
    private final BookClubMemberRepository bookClubMemberRepository;

    @Transactional
    public void createGatherings(Long memberId, Long bookClubId, CreateGatheringsRequest request) {
        Book book = bookRepository.findById(request.getBookId()).orElseThrow(BookNotFoundException::new);
        BookClub bookClub = bookClubRepository.findById(bookClubId).orElseThrow(BookClubNotFoundException::new);
        if (!bookClubMemberRepository.existsByBookClubIdAndMemberId(bookClubId, memberId)) {
            throw new MemberNotInBookClubException();
        }

        List<Gathering> gatherings = request.toGatherings(book);
        gatheringRepository.saveAll(gatherings);

        bookClub.updateUpcomingGatheringDate(gatherings.get(0).getStartTime());
    }

    @Transactional(readOnly = true)
    public List<ReadGatheringResponse> readGatherings(Long memberId, Long bookClubId) {
        return ReadGatheringResponse.from(gatheringRepository.findAllByBookClubId(bookClubId));
    }

    @Transactional
    public UpdateGatheringResponse updateGathering(Long memberId, Long gatheringId, UpdateGatheringRequest request) {
        Gathering gathering = gatheringRepository.findById(gatheringId).orElseThrow(GatheringNotFoundException::new);

        gathering.update(request);
        return UpdateGatheringResponse.from(gathering);
    }

    @Transactional
    public void deleteGathering(Long memberId, Long gatheringId) {
        gatheringRepository.deleteById(gatheringId);
    }

}
