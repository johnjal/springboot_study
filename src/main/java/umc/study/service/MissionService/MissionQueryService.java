package umc.study.service.MissionService;

import org.springframework.data.domain.Page;
import umc.study.domain.Mission;
import umc.study.domain.enums.MissionStatus;

import java.util.Optional;

public interface MissionQueryService {

    Optional<Mission> findMission(Long id);
    Page<Mission> findMissionByMemberAndStatus(Long memberId, MissionStatus status, Integer page, Integer size);
    Page<Mission> findMissionByRegionExMember(Long memberId, Long regionId, Integer page, Integer size);
    Page<Mission> findMissionByStore(Long storeId, Integer page, Integer size);
}
