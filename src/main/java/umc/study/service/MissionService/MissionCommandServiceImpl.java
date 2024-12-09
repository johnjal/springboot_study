package umc.study.service.MissionService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.study.apiPayLoad.code.status.ErrorStatus;
import umc.study.apiPayLoad.exception.handler.GeneralHandler;
import umc.study.converter.MissionConverter;
import umc.study.domain.Member;
import umc.study.domain.Mission;
import umc.study.domain.Store;
import umc.study.domain.enums.MissionStatus;
import umc.study.domain.mapping.MemberMission;
import umc.study.repository.MemberRepository.MemberRepository;
import umc.study.repository.MissionRepository.MissionRepository;
import umc.study.repository.StoreRepository.StoreRepository;
import umc.study.repository.mapping.MemberMissionRepository.MemberMissionRepository;
import umc.study.web.dto.MissionRequest;

@Service
@RequiredArgsConstructor
public class MissionCommandServiceImpl implements MissionCommandService {

    private final MissionRepository missionRepository;
    private final StoreRepository storeRepository;
    private final MemberMissionRepository memberMissionRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Mission addMission(MissionRequest.AddDTO request) {
        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new GeneralHandler(ErrorStatus.STORE_NOT_FOUND));

        return missionRepository.save(MissionConverter.AddDTOtoMission(request, store, store.getRegion()));
    }

    @Override
    @Transactional
    public MemberMission challengeMission(MissionRequest.ChallengeDTO request) {
        Mission mission = missionRepository.findById(request.getMissionId())
                .orElseThrow(() -> new GeneralHandler(ErrorStatus.MISSION_NOT_FOUND));

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new GeneralHandler(ErrorStatus.MEMBER_NOT_FOUND));

        if (member.getMemberMissionList().stream()
                .anyMatch(memberMission -> memberMission.getMission().getId().equals(mission.getId()))) {
            throw new GeneralHandler(ErrorStatus.MISSION_ALREADY_ADDED);
        }

        MemberMission memberMission = MemberMission.builder()
                .member(member)
                .mission(mission)
                .status(MissionStatus.CHALLENGING)
                .build();
        return memberMissionRepository.save(memberMission);
    }

}
