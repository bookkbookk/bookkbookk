package codesquad.bookkbookk.domain.gathering.service;

import org.springframework.stereotype.Service;

import codesquad.bookkbookk.common.error.exception.BookNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberNotInBookClubException;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.book.repository.BookRepository;
import codesquad.bookkbookk.domain.bookclub.repository.MemberBookClubRepository;
import codesquad.bookkbookk.domain.gathering.data.dto.CreateGatheringRequest;
import codesquad.bookkbookk.domain.gathering.data.entity.Gathering;
import codesquad.bookkbookk.domain.gathering.repository.GatheringRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GatheringService {

    private final GatheringRepository gatheringRepository;
    private final BookRepository bookRepository;
    private final MemberBookClubRepository memberBookClubRepository;

    public void createGathering(CreateGatheringRequest createGatheringRequest, Long memberId) {
        Book book = bookRepository.findById(createGatheringRequest.getBookId())
                .orElseThrow(BookNotFoundException::new);
        if (!memberBookClubRepository.existsByMemberIdAndBookClubId(memberId, book.getBookClub().getId())) {
            throw new MemberNotInBookClubException();
        }

        Gathering gathering = Gathering.of(createGatheringRequest, book);
        gatheringRepository.save(gathering);
    }

}
