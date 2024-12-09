package umc.study.service.MemberService;

import umc.study.domain.Member;
import umc.study.web.dto.MemberRequest;

public interface MemberCommandService {
    Member joinMember(MemberRequest.JoinDto request);
}
