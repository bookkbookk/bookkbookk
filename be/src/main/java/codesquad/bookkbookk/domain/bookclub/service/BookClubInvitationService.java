package codesquad.bookkbookk.domain.bookclub.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import codesquad.bookkbookk.common.error.exception.BookClubNotFoundException;
import codesquad.bookkbookk.common.error.exception.InvitationUrlNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberNotInBookClubException;
import codesquad.bookkbookk.domain.bookclub.data.dto.CreateInvitationUrlRequest;
import codesquad.bookkbookk.domain.bookclub.data.dto.InvitationUrlResponse;
import codesquad.bookkbookk.domain.bookclub.data.dto.JoinBookClubRequest;
import codesquad.bookkbookk.domain.bookclub.data.dto.JoinBookClubResponse;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClubInvitationCode;
import codesquad.bookkbookk.domain.bookclub.data.entity.MemberBookClub;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubInvitationCodeRepository;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.bookclub.repository.MemberBookClubRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookClubInvitationService {

    private final BookClubInvitationCodeRepository bookClubInvitationCodeRepository;
    private final MemberBookClubRepository memberBookClubRepository;
    private final MemberRepository memberRepository;
    private final BookClubRepository bookClubRepository;

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

    public JoinBookClubResponse joinBookClub(Long memberId, JoinBookClubRequest joinBookClubRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        BookClubInvitationCode bookClubInvitationCode = bookClubInvitationCodeRepository
                .findByInvitationCode(joinBookClubRequest.getBookClubCode()).orElseThrow(InvitationUrlNotFoundException::new);
        BookClub bookClub = bookClubRepository.findById(bookClubInvitationCode.getBookClubId())
                .orElseThrow(BookClubNotFoundException::new);

        MemberBookClub save = memberBookClubRepository.save(new MemberBookClub(member, bookClub));
        return JoinBookClubResponse.from(save);
    }

    private void validateMemberAuth(Long memberId, Long bookClubId) {
        if (!memberBookClubRepository.existsByMemberIdAndBookClubId(memberId, bookClubId)) {
            throw new MemberNotInBookClubException();
        }
    }

}
