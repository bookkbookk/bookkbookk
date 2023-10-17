package codesquad.bookkbookk.member.service;

import org.springframework.stereotype.Service;

import codesquad.bookkbookk.member.data.dto.MemberResponse;
import codesquad.bookkbookk.member.data.entity.Member;
import codesquad.bookkbookk.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponse readMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(RuntimeException::new);

        return MemberResponse.from(member);
    }

}
