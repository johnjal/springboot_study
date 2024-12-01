package umc.study.converter;

import umc.study.domain.Mission;
import umc.study.web.dto.StudyResponse;

public class StudyConverter {

    public static StudyResponse.MissionDTO toMissionDTO(Mission mission){
        return StudyResponse.MissionDTO.builder()
                .id(mission.getId())
                .store(mission.getStore())
                .missionSpec(mission.getMissionSpec())
                .region(mission.getRegion())
                .reward(mission.getReward())
                .deadline(mission.getDeadline())
                .build();
    }
}
