package codesquad.bookkbookk.domain.bookclub.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import codesquad.bookkbookk.common.error.exception.InvitationUrlNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberNotInBookClubException;
import codesquad.bookkbookk.domain.bookclub.data.dto.CreateInvitationUrlRequest;
import codesquad.bookkbookk.domain.bookclub.data.dto.InvitationUrlResponse;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClubInvitationCode;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubInvitationCodeRepository;
import codesquad.bookkbookk.domain.bookclub.repository.MemberBookClubRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookClubInvitationService {


    private final BookClubInvitationCodeRepository bookClubInvitationCodeRepository;
    private final MemberBookClubRepository memberBookClubRepository;

    public InvitationUrlResponse createInvitationUrl(Long memberId, CreateInvitationUrlRequest request) {
        validateMemberAuth(memberId, request.getBookClubId());

        String invitationCode = String.valueOf(UUID.randomUUID());
        BookClubInvitationCode bookClubInvitationCode = new BookClubInvitationCode(request, invitationCode);
        bookClubInvitationCodeRepository.save(bookClubInvitationCode);

        return new InvitationUrlResponse(bookClubInvitationCode.getInvitationCode());
    }

    public InvitationUrlResponse readInvitationUrl(Long memberId, Long bookClubId) {
        validateMemberAuth(memberId, bookClubId);

        BookClubInvitationCode bookClubInvitationCode = bookClubInvitationCodeRepository.findByBookClubId(bookClubId)
                .orElseThrow(InvitationUrlNotFoundException::new);

        return new InvitationUrlResponse(bookClubInvitationCode.getInvitationCode());
    }

    private void validateMemberAuth(Long memberId, Long bookClubId) {
        if (!memberBookClubRepository.existsByMemberIdAndBookClubId(memberId, bookClubId)) {
            throw new MemberNotInBookClubException();
        }
    }

}
