package umc.study.service.MissionService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.study.domain.Mission;
import umc.study.domain.enums.MissionStatus;

import java.util.Optional;

public interface MissionQueryService {

    Optional<Mission> findMission(Long id);
    Page<Mission> findMissionByMemberAndStatus(Long memberId, MissionStatus status, Pageable pageable);
    Page<Mission> findMissionByRegionExMember(Long memberId, Long regionId, Pageable pageable);

}
