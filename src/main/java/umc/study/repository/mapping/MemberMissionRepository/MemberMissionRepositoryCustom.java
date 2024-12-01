package umc.study.repository.mapping.MemberMissionRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.study.domain.Member;
import umc.study.domain.Mission;
import umc.study.domain.enums.MissionStatus;

import java.util.List;

public interface MemberMissionRepositoryCustom {
    List<Long> findMissionIdByMemberAndStatus(Member member, MissionStatus missionStatus);
    Page<Mission> findMissionDynamicQuery(Member member, MissionStatus missionStatus, Pageable pageable);
}

