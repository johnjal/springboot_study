package umc.study.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umc.study.apiPayLoad.ApiResponse;
import umc.study.apiPayLoad.code.status.ErrorStatus;
import umc.study.apiPayLoad.exception.handler.TempHandler;
import umc.study.converter.StudyConverter;
import umc.study.domain.Mission;
import umc.study.domain.enums.MissionStatus;
import umc.study.service.MissionService.MissionQueryService;
import umc.study.web.dto.StudyResponse;

@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyController {

    private final MissionQueryService missionQueryService;

    @GetMapping("/mission/challenging")
    public ApiResponse<Page<StudyResponse.MissionDTO>> findChallengingMission(
            @RequestParam(required = false) Long memberId, Pageable pageable){

        if (memberId == null){
            throw new TempHandler(ErrorStatus._BAD_REQUEST);
        }

        Page<Mission> missionPage = missionQueryService.findMissionByMemberAndStatus(
                        memberId,
                        MissionStatus.CHALLENGING,
                        pageable);

        // N+1 발생을 막기 위해 DTO 사용, 재매핑.
        return ApiResponse.onSuccess(missionPage.map(StudyConverter::toMissionDTO));
    }

    @GetMapping("/mission/complete")
    public ApiResponse<Page<StudyResponse.MissionDTO>> findCompleteMission(
            @RequestParam(required = false) Long memberId, Pageable pageable){

        if (memberId == null){
            throw new TempHandler(ErrorStatus._BAD_REQUEST);
        }

        Page<Mission> missionPage = missionQueryService.findMissionByMemberAndStatus(
                memberId,
                MissionStatus.CHALLENGING,
                pageable);
        // N+1 발생을 막기 위해 DTO 사용, 재매핑.
        return ApiResponse.onSuccess(missionPage.map(StudyConverter::toMissionDTO));
    }
}