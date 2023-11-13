package codesquad.bookkbookk.domain.bookclub.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import codesquad.bookkbookk.common.error.exception.BookClubNotFoundException;
import codesquad.bookkbookk.common.error.exception.InvitationUrlNotFoundException;
import codesquad.bookkbookk.common.error.exception.MemberNotFoundException;
import codesquad.bookkbookk.common.image.S3ImageUploader;
import codesquad.bookkbookk.domain.auth.service.AuthorizationService;
import codesquad.bookkbookk.domain.bookclub.data.dto.ReadBookClubDetailResponse;
import codesquad.bookkbookk.domain.mapping.entity.MemberBook;
import codesquad.bookkbookk.domain.mapping.repository.MemberBookRepository;
import codesquad.bookkbookk.domain.bookclub.data.dto.CreateBookClubRequest;
import codesquad.bookkbookk.domain.bookclub.data.dto.CreateBookClubResponse;
import codesquad.bookkbookk.domain.bookclub.data.dto.CreateInvitationUrlRequest;
import codesquad.bookkbookk.domain.bookclub.data.dto.InvitationUrlResponse;
import codesquad.bookkbookk.domain.bookclub.data.dto.JoinBookClubRequest;
import codesquad.bookkbookk.domain.bookclub.data.dto.JoinBookClubResponse;
import codesquad.bookkbookk.domain.bookclub.data.dto.ReadBookClubResponse;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClubInvitationCode;
import codesquad.bookkbookk.domain.mapping.entity.BookClubMember;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubInvitationCodeRepository;
import codesquad.bookkbookk.domain.mapping.repository.BookClubMemberRepository;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookClubService {

    private static final String DEFAULT_BOOK_CLUB_IMAGE_URL =
            "https://i.namu.wiki/i/ZnPxYijjK2AlXyf1dPZv0fqVvg3kdxahVkONYTCR-jplhh48smoq4UCfSAZIz6_R0lxoBz"
                    + "JuQiIL6kfwtv0taBXNa_-nOxJKx2BX-z3GxPj6vqoc14GZ7nrT_jDXlrOV1xNL9RVYBTN_brsnBuCwOA.webp";

    private final AuthorizationService authorizationService;

    private final S3ImageUploader s3ImageUploader;
    private final BookClubRepository bookClubRepository;
    private final BookClubMemberRepository bookClubMemberRepository;
    private final BookClubInvitationCodeRepository bookClubInvitationCodeRepository;
    private final MemberRepository memberRepository;
    private final MemberBookRepository memberBookRepository;

    public CreateBookClubResponse createBookClub(Long memberId, CreateBookClubRequest request) {
        String profileImgUrl = DEFAULT_BOOK_CLUB_IMAGE_URL;

        MultipartFile profileImgFile = request.getProfileImage();
        if (profileImgFile != null) {
            profileImgUrl = s3ImageUploader.upload(profileImgFile).toString();
        }

        BookClub bookClub = BookClub.builder()
                .creatorId(memberId)
                .name(request.getName())
                .profileImgUrl(profileImgUrl)
                .build();
        bookClubRepository.save(bookClub);

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        BookClubMember bookClubMember = new BookClubMember(bookClub, member);

        bookClubMemberRepository.save(bookClubMember);
        bookClub.getBookClubMembers().add(bookClubMember);
        member.getMemberBookClubs().add(bookClubMember);

        InvitationUrlResponse invitationUrlResponse = createInvitationUrl(memberId,
                new CreateInvitationUrlRequest(bookClubMember.getId()));

        return new CreateBookClubResponse(bookClub.getId(), invitationUrlResponse.getInvitationUrl());
    }

    public List<ReadBookClubResponse> readBookClubs(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        List<BookClub> bookClubs = member.getMemberBookClubs().stream()
                .map(BookClubMember::getBookClub)
                .collect(Collectors.toUnmodifiableList());

        return ReadBookClubResponse.from(bookClubs);
    }

    public InvitationUrlResponse createInvitationUrl(Long memberId, CreateInvitationUrlRequest request) {
        authorizationService.authorizeBookClubMembership(memberId, request.getBookClubId());

        String invitationCode = String.valueOf(UUID.randomUUID());
        BookClubInvitationCode bookClubInvitationCode = new BookClubInvitationCode(request.getBookClubId(),
                invitationCode);
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
                .findByInvitationCode(joinBookClubRequest.getInvitationCode())
                .orElseThrow(InvitationUrlNotFoundException::new);
        BookClub bookClub = bookClubRepository.findById(bookClubInvitationCode.getBookClubId())
                .orElseThrow(BookClubNotFoundException::new);

        authorizationService.authorizeBookClubJoin(memberId, bookClub.getId());

        BookClubMember save = bookClubMemberRepository.save(new BookClubMember(bookClub, member));

        bookClub.getBooks().forEach(book -> {
            MemberBook memberBook = new MemberBook(member, book);
            memberBookRepository.save(memberBook);
            member.getMemberBooks().add(memberBook);
        });

        return JoinBookClubResponse.from(save);
    }

    public ReadBookClubDetailResponse readBookClubDetail(Long bookClubId) {
        BookClub bookClub = bookClubRepository.findById(bookClubId).orElseThrow(BookClubNotFoundException::new);

        return bookClub.getBookClubStatus().from(bookClub);
    }

}
