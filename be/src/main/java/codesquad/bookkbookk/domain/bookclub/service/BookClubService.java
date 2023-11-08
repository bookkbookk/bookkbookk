package codesquad.bookkbookk.domain.bookclub.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import codesquad.bookkbookk.domain.bookclub.data.dto.CreateBookClubRequest;
import codesquad.bookkbookk.domain.bookclub.data.dto.CreateBookClubResponse;
import codesquad.bookkbookk.domain.bookclub.data.dto.ReadBookClubResponse;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.domain.bookclub.data.entity.BookClubMember;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.domain.bookclub.repository.BookClubMemberRepository;
import codesquad.bookkbookk.common.image.S3ImageUploader;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookClubService {

    private final S3ImageUploader s3ImageUploader;
    private final BookClubRepository bookClubRepository;
    private final BookClubMemberRepository bookClubMemberRepository;
    private final MemberRepository memberRepository;

    private static final String DEFAULT_BOOK_CLUB_IMAGE_URL =
            "https://i.namu.wiki/i/ZnPxYijjK2AlXyf1dPZv0fqVvg3kdxahVkONYTCR-jplhh48smoq4UCfSAZIz6_R0lxoBz" +
                    "JuQiIL6kfwtv0taBXNa_-nOxJKx2BX-z3GxPj6vqoc14GZ7nrT_jDXlrOV1xNL9RVYBTN_brsnBuCwOA.webp";

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

        Member member = memberRepository.findById(memberId).orElseThrow();
        BookClubMember bookClubMember = new BookClubMember(bookClub, member);

        bookClubMemberRepository.save(bookClubMember);

        return new CreateBookClubResponse(bookClub.getId());

    }

    public List<ReadBookClubResponse> readBookClubs(Long memberId) {
        List<BookClub> bookClubs = bookClubRepository.findBookClubsByMemberId(memberId);

        return ReadBookClubResponse.from(bookClubs);
    }

}
