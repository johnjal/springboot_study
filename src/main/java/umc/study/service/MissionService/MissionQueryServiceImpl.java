package umc.study.service.MissionService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.study.apiPayLoad.code.status.ErrorStatus;
import umc.study.apiPayLoad.exception.handler.GeneralHandler;
import umc.study.domain.Member;
import umc.study.domain.Mission;
import umc.study.domain.Region;
import umc.study.domain.Store;
import umc.study.domain.enums.MissionStatus;
import umc.study.repository.MemberRepository.MemberRepository;
import umc.study.repository.MissionRepository.MissionRepository;
import umc.study.repository.RegionRepository.RegionRepository;
import umc.study.repository.StoreRepository.StoreRepository;
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
    private final StoreRepository storeRepository;

    public Optional<Mission> findMission(Long id){
        return missionRepository.findById(id);
    }

    @Override
    public Page<Mission> findMissionByMemberAndStatus(Long memberId, MissionStatus status, Integer page, Integer size) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new GeneralHandler(ErrorStatus.MEMBER_NOT_FOUND));

        return memberMissionRepository.findMissionDynamicQuery(member, status, PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    @Override
    public Page<Mission> findMissionByRegionExMember(Long memberId, Long regionId, Integer page, Integer size) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new GeneralHandler(ErrorStatus.MEMBER_NOT_FOUND));
        Region region = regionRepository.findById(regionId).orElseThrow(() -> new GeneralHandler(ErrorStatus.REGION_NOT_FOUND));

        List<Long> exList = memberMissionRepository.findMissionIdByMemberAndStatus(member, null);

        return missionRepository.findByRegion(region, PageRequest.of(page, size, Sort.by("createdAt").descending()), exList);
    }

    @Override
    public Page<Mission> findMissionByStore(Long storeId, Integer page, Integer size) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new GeneralHandler(ErrorStatus.STORE_NOT_FOUND));

        return missionRepository.findByStore(store, PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }
}
