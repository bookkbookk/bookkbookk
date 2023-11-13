package codesquad.bookkbookk.domain.gathering.service;

import org.springframework.stereotype.Service;

import codesquad.bookkbookk.common.error.exception.BookNotFoundException;
import codesquad.bookkbookk.domain.auth.service.AuthorizationService;
import codesquad.bookkbookk.domain.book.data.entity.Book;
import codesquad.bookkbookk.domain.book.repository.BookRepository;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.gathering.data.dto.CreateGatheringRequest;
import codesquad.bookkbookk.domain.gathering.data.entity.Gathering;
import codesquad.bookkbookk.domain.gathering.repository.GatheringRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GatheringService {

    private final AuthorizationService authorizationService;

    private final GatheringRepository gatheringRepository;
    private final BookRepository bookRepository;

    public void createGathering(CreateGatheringRequest createGatheringRequest, Long memberId) {
        Book book = bookRepository.findById(createGatheringRequest.getBookId())
                .orElseThrow(BookNotFoundException::new);
        BookClub bookClub = book.getBookClub();
        authorizationService.authorizeBookClubMembership(memberId, bookClub.getId());

        Gathering gathering = new Gathering(book, createGatheringRequest.getDateTime(),
                createGatheringRequest.getPlace());
        gatheringRepository.save(gathering);

        bookClub.updateUpcomingGatheringDate(gathering.getDateTime());
    }

}
