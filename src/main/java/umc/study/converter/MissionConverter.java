package umc.study.converter;

import umc.study.domain.Mission;
import umc.study.domain.Region;
import umc.study.domain.Store;
import umc.study.domain.mapping.MemberMission;
import umc.study.web.dto.MissionRequest;
import umc.study.web.dto.MissionResponse;

public class MissionConverter {

    public static MissionResponse.MissionDTO toMissionDTO(Mission mission){
        return MissionResponse.MissionDTO.builder()
                .missionId(mission.getId())
                .store(mission.getStore())
                .missionSpec(mission.getMissionSpec())
                .region(mission.getRegion())
                .reward(mission.getReward())
                .deadline(mission.getDeadline())
                .build();
    }

    public static Mission AddDTOtoMission(MissionRequest.AddDTO addDTO, Store store, Region region) {
        return Mission.builder()
                .reward(addDTO.getReward())
                .deadline(addDTO.getDeadline())
                .missionSpec(addDTO.getMissionSpec())
                .store(store)
                .region(region)
                .build();
    }

    public static  MissionResponse.AddResultDTO toAddResponse(Mission mission){
        return MissionResponse.AddResultDTO.builder()
                .missionId(mission.getId())
                .createdAt(mission.getCreatedAt())
                .build();
    }

    public static MissionResponse.ChallengeResultDTO toChallengeResponse(MemberMission memberMission){
        return MissionResponse.ChallengeResultDTO.builder()
                .memberMissionId(memberMission.getId())
                .createdAt(memberMission.getCreatedAt())
                .build();
    }
}
