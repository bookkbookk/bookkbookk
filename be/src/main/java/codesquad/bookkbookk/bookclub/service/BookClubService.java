package codesquad.bookkbookk.bookclub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import codesquad.bookkbookk.bookclub.data.dto.CreateBookClubRequest;
import codesquad.bookkbookk.bookclub.data.dto.CreateBookClubResponse;
import codesquad.bookkbookk.bookclub.data.dto.ReadBookClubResponse;
import codesquad.bookkbookk.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.bookclub.data.entity.MemberBookClub;
import codesquad.bookkbookk.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.bookclub.repository.MemberBookClubRepository;
import codesquad.bookkbookk.image.S3ImageUploader;
import codesquad.bookkbookk.member.data.entity.Member;
import codesquad.bookkbookk.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookClubService {

    private final S3ImageUploader s3ImageUploader;
    private final BookClubRepository bookClubRepository;
    private final MemberBookClubRepository memberBookClubRepository;
    private final MemberRepository memberRepository;

    public CreateBookClubResponse createBookClub(Long memberId, CreateBookClubRequest request) {
        String profileImgUrl = s3ImageUploader.upload(request.getProfileImage()).toString();

        BookClub bookClub = BookClub.builder()
                .creatorId(memberId)
                .name(request.getName())
                .profileImgUrl(profileImgUrl)
                .build();
        bookClubRepository.save(bookClub);

        Member member = memberRepository.findById(memberId).orElseThrow();
        MemberBookClub memberBookClub = new MemberBookClub(member, bookClub);

        memberBookClubRepository.save(memberBookClub);

        return new CreateBookClubResponse(bookClub.getId());

    }

    public List<ReadBookClubResponse> readBookClubs(Long memberId) {
        List<BookClub> bookClubs = bookClubRepository.findBookClubsByMemberId(memberId);

        return ReadBookClubResponse.from(bookClubs);
    }

}
