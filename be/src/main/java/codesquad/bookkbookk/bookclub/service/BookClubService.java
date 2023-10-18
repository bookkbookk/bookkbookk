package codesquad.bookkbookk.bookclub.service;

import org.springframework.stereotype.Service;

import codesquad.bookkbookk.bookclub.data.dto.CreateBookClubRequest;
import codesquad.bookkbookk.bookclub.data.dto.CreateBookClubResponse;
import codesquad.bookkbookk.bookclub.data.entity.BookClub;
import codesquad.bookkbookk.bookclub.repository.BookClubRepository;
import codesquad.bookkbookk.image.S3ImageUploader;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookClubService {

    private final S3ImageUploader s3ImageUploader;
    private final BookClubRepository bookClubRepository;

    public CreateBookClubResponse createBookClub(Long memberId, CreateBookClubRequest request) {
        String profileImgUrl = s3ImageUploader.upload(request.getProfileImage()).toString();

        BookClub bookClub = BookClub.from(memberId, request.getName(), profileImgUrl);
        bookClubRepository.save(bookClub);

        return CreateBookClubResponse.from(bookClub.getId());

    }

}
