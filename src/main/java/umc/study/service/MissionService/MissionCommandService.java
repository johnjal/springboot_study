package umc.study.service.MissionService;

import umc.study.domain.Mission;
import umc.study.domain.mapping.MemberMission;
import umc.study.web.dto.MissionRequest;

public interface MissionCommandService {
    Mission addMission(MissionRequest.AddDTO request);
    MemberMission challengeMission(MissionRequest.ChallengeDTO request);
}
