package codesquad.bookkbookk.domain.bookclub.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import codesquad.bookkbookk.common.error.exception.BookClubNotFoundException;
import codesquad.bookkbookk.common.error.exception.InvitationCodeNotSavedException;
import codesquad.bookkbookk.common.error.exception.MemberNotFoundException;
import codesquad.bookkbookk.common.image.S3ImageUploader;
import codesquad.bookkbookk.common.redis.RedisService;
import codesquad.bookkbookk.domain.auth.service.AuthorizationService;
import codesquad.bookkbookk.domain.bookclub.data.dto.CreateBookClubRequest;
import codesquad.bookkbookk.domain.bookclub.data.dto.CreateBookClubResponse;
import codesquad.bookkbookk.domain.bookclub.data.dto.CreateInvitationUrlRequest;
import codesquad.bookkbookk.domain.bookclub.data.dto.InvitationUrlResponse;
import codesquad.bookkbookk.domain.bookclub.data.dto.JoinBookClubRequest;
import codesquad.bookkbookk.domain.bookclub.data.dto.JoinBookClubResponse;
import codesquad.bookkbookk.domain.bookclub.data.dto.ReadBookClubDetailResponse;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.data.type.BookClubStatus;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.mapping.entity.BookClubMember;
import codesquad.bookkbookk.domain.mapping.repository.BookClubMemberRepository;
import codesquad.bookkbookk.domain.mapping.repository.MemberBookRepository;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookClubService {

    private static final String DEFAULT_BOOK_CLUB_IMAGE_URL =
            "https://i.namu.wiki/i/ZnPxYijjK2AlXyf1dPZv0fqVvg3kdxahVkONYTCR-jplhh48smoq4UCfSAZIz6_R0lxoBz"
                    + "JuQiIL6kfwtv0taBXNa_-nOxJKx2BX-z3GxPj6vqoc14GZ7nrT_jDXlrOV1xNL9RVYBTN_brsnBuCwOA.webp";
    private static final String STATUS_ALL = "ALL";

    private final AuthorizationService authorizationService;
    private final RedisService redisService;

    private final S3ImageUploader s3ImageUploader;
    private final BookClubRepository bookClubRepository;
    private final BookClubMemberRepository bookClubMemberRepository;
    private final MemberRepository memberRepository;
    private final MemberBookRepository memberBookRepository;

    @Transactional
    public CreateBookClubResponse createBookClub(Long memberId, CreateBookClubRequest request) {
        String profileImgUrl = DEFAULT_BOOK_CLUB_IMAGE_URL;

        MultipartFile profileImgFile = request.getProfileImage();
        if (profileImgFile != null) {
            profileImgUrl = s3ImageUploader.upload(profileImgFile).toString();
        }

        BookClub bookClub = BookClub.builder()
                .creatorId(memberId)
                .name(request.getName())
                .profileImageUrl(profileImgUrl)
                .build();
        bookClubRepository.save(bookClub);

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        BookClubMember bookClubMember = new BookClubMember(bookClub, member);

        bookClubMemberRepository.save(bookClubMember);
        bookClub.getBookClubMembers().add(bookClubMember);
        member.getMemberBookClubs().add(bookClubMember);

        InvitationUrlResponse invitationUrlResponse = createInvitationUrl(memberId,
                new CreateInvitationUrlRequest(bookClub.getId()));

        return new CreateBookClubResponse(bookClub.getId(), invitationUrlResponse.getInvitationUrl());
    }

    @Transactional(readOnly = true)
    public List<ReadBookClubDetailResponse> readBookClubs(Long memberId, String statusName) {
        BookClubStatus status;
        if (statusName.equals(STATUS_ALL)) {
            status = null;
        } else {
            status = BookClubStatus.of(statusName);
        }

        return bookClubRepository.findAllByMemberIdAndStatus(memberId, status).stream()
                .map(bookClub -> bookClub.getStatus().from(bookClub))
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public InvitationUrlResponse createInvitationUrl(Long memberId, CreateInvitationUrlRequest request) {
        authorizationService.authorizeBookClubMembershipByBookClubId(memberId, request.getBookClubId());

        String invitationCode = String.valueOf(UUID.randomUUID());
        redisService.saveInvitationCode(invitationCode, request.getBookClubId());

        return new InvitationUrlResponse(invitationCode);
    }

    @Transactional
    public JoinBookClubResponse joinBookClub(Long memberId, JoinBookClubRequest joinBookClubRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Long bookClubId = redisService.getBookClubIdByInvitationCode(joinBookClubRequest.getInvitationCode());
        if (bookClubId == null) throw new InvitationCodeNotSavedException();
        BookClub bookClub = bookClubRepository.findByIdWithBooks(bookClubId).orElseThrow(BookClubNotFoundException::new);

        authorizationService.authorizeBookClubJoin(memberId, bookClub.getId());

        bookClub.addMember(new BookClubMember(bookClub, member));
        bookClub.getBooks().forEach(member::addBook);

        return JoinBookClubResponse.from(bookClub);
    }

    @Transactional(readOnly = true)
    public ReadBookClubDetailResponse readBookClubDetail(Long memberId, Long bookClubId) {
        authorizationService.authorizeBookClubMembershipByBookClubId(memberId, bookClubId);

        BookClub bookClub = bookClubRepository.findDetailById(bookClubId).orElseThrow(BookClubNotFoundException::new);

        return bookClub.getStatus().from(bookClub);
    }

}
