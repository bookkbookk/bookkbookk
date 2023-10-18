package codesquad.bookkbookk.member.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import codesquad.bookkbookk.image.S3ImageUploader;
import codesquad.bookkbookk.member.data.dto.MemberResponse;
import codesquad.bookkbookk.member.data.dto.UpdateProfileRequest;
import codesquad.bookkbookk.member.data.entity.Member;
import codesquad.bookkbookk.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final S3ImageUploader s3ImageUploader;

    public MemberResponse readMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(RuntimeException::new);

        return MemberResponse.from(member);
    }

    @Transactional
    public void updateProfile(Long memberId, UpdateProfileRequest updateProfileRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(RuntimeException::new);

        String imageUrl = s3ImageUploader.upload(updateProfileRequest.getProfileImage()).toString();

        member.updateProfile(updateProfileRequest.getNickname(), imageUrl);
    }

}
