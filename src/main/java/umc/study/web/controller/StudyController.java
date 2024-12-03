package umc.study.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.study.apiPayLoad.ApiResponse;
import umc.study.apiPayLoad.code.status.ErrorStatus;
import umc.study.apiPayLoad.exception.handler.GeneralHandler;
import umc.study.converter.MemberConverter;
import umc.study.converter.MissionConverter;
import umc.study.converter.ReviewConverter;
import umc.study.converter.StoreConverter;
import umc.study.domain.Member;
import umc.study.domain.Mission;
import umc.study.domain.Review;
import umc.study.domain.Store;
import umc.study.domain.enums.MissionStatus;
import umc.study.domain.mapping.MemberMission;
import umc.study.service.MemberService.MemberCommandService;
import umc.study.service.MissionService.MissionCommandServiceImpl;
import umc.study.service.MissionService.MissionQueryService;
import umc.study.service.ReviewService.ReviewCommandService;
import umc.study.service.StoreService.StoreCommandService;
import umc.study.web.dto.*;

@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
@Validated
public class StudyController {

    private final MissionQueryService missionQueryService;
    private final MemberCommandService memberCommandService;
    private final StoreCommandService storeCommandService;
    private final ReviewCommandService reviewCommandService;
    private final MissionCommandServiceImpl missionCommandService;

    @GetMapping("/mission/challenging")
    public ApiResponse<Page<MissionResponse.MissionDTO>> findChallengingMission(
            @RequestParam Long memberId,
            @RequestParam @NotNull(message = "페이징 정보가 누락되었습니다.") Pageable pageable,
            @RequestParam(required = false) String status) {

        if (memberId == null) {
            throw new GeneralHandler(ErrorStatus._BAD_REQUEST);
        }

        Page<Mission> missionPage = missionQueryService.findMissionByMemberAndStatus(
                memberId,
                MissionStatus.CHALLENGING,
                pageable);

        // N+1 발생을 막기 위해 DTO 사용, 재매핑.
        return ApiResponse.onSuccess(missionPage.map(MissionConverter::toMissionDTO));
    }

    @GetMapping("/mission/complete")
    public ApiResponse<Page<MissionResponse.MissionDTO>> findCompleteMission(
            @RequestParam(required = false) Long memberId, Pageable pageable) {

        if (memberId == null) {
            throw new GeneralHandler(ErrorStatus._BAD_REQUEST);
        }

        Page<Mission> missionPage = missionQueryService.findMissionByMemberAndStatus(
                memberId,
                MissionStatus.COMPLETE,
                pageable);
        // N+1 발생을 막기 위해 DTO 사용, 재매핑.
        return ApiResponse.onSuccess(missionPage.map(MissionConverter::toMissionDTO));
    }

    @PostMapping("/member/join")
    public ApiResponse<MemberResponse.JoinResultDTO> joinMember(@RequestBody @Valid MemberRequest.JoinDto request) {
        Member member = memberCommandService.joinMember(request);
        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(member));
    }

    @PostMapping("/store/add")
    public ApiResponse<StoreResponse.AddResultDTO> addStore(@RequestBody @Valid StoreRequest.AddDTO request) {
        Store store = storeCommandService.addStore(request);
        return ApiResponse.onSuccess(StoreConverter.toAddResponse(store));
    }

    @PostMapping("/store/add-review")
    public ApiResponse<ReviewResponse.AddResultDTO> addReview(@RequestBody @Valid ReviewRequest.AddDTO request) {
        Review review = reviewCommandService.addReview(request);
        return ApiResponse.onSuccess(ReviewConverter.toAddResponse(review));
    }

    @PostMapping("/mission/add")
    public ApiResponse<MissionResponse.AddResultDTO> addMission(@RequestBody @Valid MissionRequest.AddDTO request) {
        Mission mission = missionCommandService.addMission(request);
        return ApiResponse.onSuccess(MissionConverter.toAddResponse(mission));
    }

    @PostMapping("/mission/challenge-mission")
    public ApiResponse<MissionResponse.ChallengeResultDTO> challengeMission(@RequestBody @Valid MissionRequest.ChallengeDTO request){
        MemberMission memberMission = missionCommandService.challengeMission(request);
        return ApiResponse.onSuccess(MissionConverter.toChallengeResponse(memberMission));
    }
}