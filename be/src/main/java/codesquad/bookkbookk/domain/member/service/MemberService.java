package codesquad.bookkbookk.domain.member.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import codesquad.bookkbookk.common.error.exception.MemberNotFoundException;
import codesquad.bookkbookk.common.image.S3ImageUploader;
import codesquad.bookkbookk.domain.member.data.dto.MemberResponse;
import codesquad.bookkbookk.domain.member.data.dto.UpdateProfileRequest;
import codesquad.bookkbookk.domain.member.data.dto.UpdateProfileResponse;
import codesquad.bookkbookk.domain.member.data.entity.Member;
import codesquad.bookkbookk.domain.member.repository.MemberRepository;

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
    public UpdateProfileResponse updateProfile(Long memberId, UpdateProfileRequest updateProfileRequest) {
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        MultipartFile profileImgFile = updateProfileRequest.getProfileImage();
        if (profileImgFile != null) {
            String profileImgUrl = s3ImageUploader.upload(profileImgFile).toString();
            member.updateProfileImgUrl(profileImgUrl);
        }

        String nickname = updateProfileRequest.getNickname();
        if (!nickname.isEmpty()) {
            member.updateNickname(nickname);
        }

        return new UpdateProfileResponse(member.getNickname(), member.getProfileImgUrl());
    }

}
