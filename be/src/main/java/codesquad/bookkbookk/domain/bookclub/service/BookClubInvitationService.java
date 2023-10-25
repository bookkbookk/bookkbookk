package codesquad.bookkbookk.domain.bookclub.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import codesquad.bookkbookk.common.error.exception.InvitationUrlNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberNotInBookClubException;
import codesquad.bookkbookk.domain.bookclub.data.dto.CreateInvitationUrlRequest;
import codesquad.bookkbookk.domain.bookclub.data.dto.InvitationUrlResponse;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClubInvitationUrl;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubInvitationUrlRepository;
import codesquad.bookkbookk.domain.bookclub.repository.MemberBookClubRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookClubInvitationService {

    private final BookClubInvitationUrlRepository bookClubInvitationUrlRepository;
    private final MemberBookClubRepository memberBookClubRepository;
    private static final String path = "bookkbookk.site/join/";

    public InvitationUrlResponse createInvitationUrl(Long memberId, CreateInvitationUrlRequest request) {
        validateMemberAuth(memberId, request.getBookClubId());

        String invitationUrl = path + UUID.randomUUID();
        BookClubInvitationUrl bookClubInvitationUrl = new BookClubInvitationUrl(request, invitationUrl);
        bookClubInvitationUrlRepository.save(bookClubInvitationUrl);

        return new InvitationUrlResponse(invitationUrl);
    }

    public InvitationUrlResponse readInvitationUrl(Long memberId, Long bookClubId) {
        validateMemberAuth(memberId, bookClubId);

        BookClubInvitationUrl invitationUrl = bookClubInvitationUrlRepository.findByBookClubId(bookClubId)
                .orElseThrow(InvitationUrlNotFoundException::new);

        return new InvitationUrlResponse(invitationUrl.getInvitationUrl());
    }

    private void validateMemberAuth(Long memberId, Long bookClubId) {
        if (!memberBookClubRepository.existsByMemberIdAndBookClubId(memberId, bookClubId)) {
            throw new MemberNotInBookClubException();
        }
    }

}
