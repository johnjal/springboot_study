package umc.study.converter;

import org.springframework.data.domain.Page;
import umc.study.domain.Mission;
import umc.study.domain.Region;
import umc.study.domain.Store;
import umc.study.domain.mapping.MemberMission;
import umc.study.web.dto.MissionRequest;
import umc.study.web.dto.MissionResponse;

public class MissionConverter {

    public static MissionResponse.findResultDTO toMissionDTO(Mission mission){
        return MissionResponse.findResultDTO.builder()
                .missionId(mission.getId())
                .storeId(mission.getStore().getId())
                .storeName(mission.getStore().getName())
                .missionSpec(mission.getMissionSpec())
                .regionId(mission.getRegion().getId())
                .regionName(mission.getRegion().getName())
                .reward(mission.getReward())
                .deadline(mission.getDeadline())
                .build();
    }

    public static MissionResponse.findResultListDTO toResultListDTO(Page<Mission> missionList){
        return MissionResponse.findResultListDTO.builder()
                .isLast(missionList.isLast())
                .isFirst(missionList.isFirst())
                .totalPage(missionList.getTotalPages())
                .totalElements(missionList.getTotalElements())
                .listSize(missionList.getSize())
                .missionList(missionList.stream()
                        .map(MissionConverter::toMissionDTO).toList())
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
