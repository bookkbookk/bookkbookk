package codesquad.bookkbookk.domain.member.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import codesquad.bookkbookk.common.image.S3ImageUploader;
import codesquad.bookkbookk.domain.member.data.dto.MemberResponse;
import codesquad.bookkbookk.domain.member.data.dto.UpdateProfileRequest;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;
import codesquad.bookkbookk.common.error.exception.MemberNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final S3ImageUploader s3ImageUploader;

    public MemberResponse readMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        return MemberResponse.from(member);
    }

    @Transactional
    public void updateProfile(Long memberId, UpdateProfileRequest updateProfileRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        String imageUrl = s3ImageUploader.upload(updateProfileRequest.getProfileImage()).toString();

        member.updateProfile(updateProfileRequest.getNickname(), imageUrl);
    }

}
