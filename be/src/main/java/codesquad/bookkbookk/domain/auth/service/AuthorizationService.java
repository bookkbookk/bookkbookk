package codesquad.bookkbookk.domain.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import codesquad.bookkbookk.common.error.exception.MemberJoinedBookClubException;
import codesquad.bookkbookk.common.error.exception.MemberNotInBookClubException;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubMemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final BookClubMemberRepository bookClubMemberRepository;

    @Transactional(readOnly = true)
    public void authorizeBookClubMembership(Long memberId, Long bookClubId) {
        if (!bookClubMemberRepository.existsByBookClubIdAndMemberId(bookClubId, memberId)) {
            throw new MemberNotInBookClubException();
        }
    }

    @Transactional(readOnly = true)
    public void authorizeBookClubJoin(Long memberId, Long bookClubId) {
        if (bookClubMemberRepository.existsByBookClubIdAndMemberId(bookClubId, memberId)) {
            throw new MemberJoinedBookClubException();
        }
    }




}
