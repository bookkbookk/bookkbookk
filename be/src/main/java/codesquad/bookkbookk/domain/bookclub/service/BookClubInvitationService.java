package codesquad.bookkbookk.domain.bookclub.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import codesquad.bookkbookk.common.error.exception.BookClubNotFoundException;
import codesquad.bookkbookk.common.error.exception.InvitationUrlNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberNotFoundException;
import codesquad.bookkbookk.domain.auth.service.AuthorizationService;
import codesquad.bookkbookk.domain.bookclub.data.dto.CreateInvitationUrlRequest;
import codesquad.bookkbookk.domain.bookclub.data.dto.InvitationUrlResponse;
import codesquad.bookkbookk.domain.bookclub.data.dto.JoinBookClubRequest;
import codesquad.bookkbookk.domain.bookclub.data.dto.JoinBookClubResponse;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClubInvitationCode;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClubMember;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubInvitationCodeRepository;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubMemberRepository;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookClubInvitationService {

    private final AuthorizationService authorizationService;

    private final BookClubInvitationCodeRepository bookClubInvitationCodeRepository;
    private final BookClubMemberRepository bookClubMemberRepository;
    private final MemberRepository memberRepository;
    private final BookClubRepository bookClubRepository;

    public InvitationUrlResponse createInvitationUrl(Long memberId, CreateInvitationUrlRequest request) {
        authorizationService.authorizeBookClubMembership(memberId, request.getBookClubId());

        String invitationCode = String.valueOf(UUID.randomUUID());
        BookClubInvitationCode bookClubInvitationCode = new BookClubInvitationCode(request, invitationCode);
        bookClubInvitationCodeRepository.save(bookClubInvitationCode);

        return new InvitationUrlResponse(bookClubInvitationCode.getInvitationCode());
    }

    public InvitationUrlResponse readInvitationUrl(Long memberId, Long bookClubId) {
        authorizationService.authorizeBookClubMembership(memberId, bookClubId);

        BookClubInvitationCode bookClubInvitationCode = bookClubInvitationCodeRepository.findByBookClubId(bookClubId)
                .orElseThrow(InvitationUrlNotFoundException::new);

        return new InvitationUrlResponse(bookClubInvitationCode.getInvitationCode());
    }

    public JoinBookClubResponse joinBookClub(Long memberId, JoinBookClubRequest joinBookClubRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        BookClubInvitationCode bookClubInvitationCode = bookClubInvitationCodeRepository
                .findByInvitationCode(joinBookClubRequest.getInvitationCode()).orElseThrow(InvitationUrlNotFoundException::new);
        BookClub bookClub = bookClubRepository.findById(bookClubInvitationCode.getBookClubId())
                .orElseThrow(BookClubNotFoundException::new);

        authorizationService.authorizeBookClubJoin(memberId, bookClub.getId());

        BookClubMember save = bookClubMemberRepository.save(new BookClubMember(bookClub, member));
        return JoinBookClubResponse.from(save);
    }

}
