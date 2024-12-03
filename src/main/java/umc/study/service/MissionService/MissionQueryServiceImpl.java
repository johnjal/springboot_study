package umc.study.service.MissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.study.apiPayLoad.code.status.ErrorStatus;
import umc.study.apiPayLoad.exception.handler.GeneralHandler;
import umc.study.domain.Member;
import umc.study.domain.Mission;
import umc.study.domain.Region;
import umc.study.domain.enums.MissionStatus;
import umc.study.repository.MemberRepository.MemberRepository;
import umc.study.repository.MissionRepository.MissionRepository;
import umc.study.repository.RegionRepository.RegionRepository;
import umc.study.repository.mapping.MemberMissionRepository.MemberMissionRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionQueryServiceImpl implements MissionQueryService {

    // DI
    private final MissionRepository missionRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final MemberRepository memberRepository;
    private final RegionRepository regionRepository;

    public Optional<Mission> findMission(Long id){
        return missionRepository.findById(id);
    }

    @Override
    public Page<Mission> findMissionByMemberAndStatus(Long memberId, MissionStatus status, Pageable pageable) {
        Member member = memberRepository.findById(memberId).orElse(null);

        if (member == null) {
            throw new GeneralHandler(ErrorStatus.MEMBER_NOT_FOUND);
        }
        return memberMissionRepository.findMissionDynamicQuery(member, status, pageable);
    }

    @Override
    public Page<Mission> findMissionByRegionExMember(Long memberId, Long regionId, Pageable pageable) {
        Member member; Region region;
        if (memberId == null) member = null;
        else member = memberRepository.findById(memberId).orElse(null);

        if (regionId == null) region = null;
        else region = regionRepository.findById(regionId).orElse(null);

        List<Long> exList = memberMissionRepository.findMissionIdByMemberAndStatus(member, null);

        return missionRepository.findByRegion(region, pageable, exList);
    }
}
