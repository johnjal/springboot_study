package umc.study.web.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umc.study.apiPayLoad.ApiResponse;
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
import umc.study.service.ReviewService.ReviewQueryService;
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
    private final ReviewQueryService reviewQueryService;

    @GetMapping("/member/mission/{memberId}/{status}")
    public ApiResponse<MissionResponse.findResultListDTO> findChallengingMission(
            @PathVariable(name = "memberId")
            @NotNull(message = "멤버 아이디가 없어요.")
            Long memberId,

            @PathVariable(name = "status", required = false)
            String status,

            @RequestParam
            @NotNull(message = "페이지 정보가 누락되었습니다.")
            @PositiveOrZero(message = "페이지는 0보다 크거나 같아야 합니다.")
            Integer page,

            @RequestParam(name = "size", defaultValue = "10")
            Integer size) {

        if (status == null) status = "";

        MissionStatus missionStatus = switch (status) {
            case "challenging" -> MissionStatus.CHALLENGING;
            case "complete" -> MissionStatus.COMPLETE;
            default -> null;
        };

        Page<Mission> missionPage = missionQueryService.findMissionByMemberAndStatus(memberId, missionStatus, page, size);

        return ApiResponse.onSuccess(MissionConverter.toResultListDTO(missionPage));
    }

    @PostMapping("/member/join")
    public ApiResponse<MemberResponse.JoinResultDTO> joinMember(
            @RequestBody
            @Valid
            MemberRequest.JoinDto request) {

        Member member = memberCommandService.joinMember(request);
        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(member));
    }

    @PostMapping("/store/add")
    public ApiResponse<StoreResponse.AddResultDTO> addStore(
            @RequestBody
            @Valid
            StoreRequest.AddDTO request) {

        Store store = storeCommandService.addStore(request);
        return ApiResponse.onSuccess(StoreConverter.toAddResponse(store));
    }

    @PostMapping("/store/add-review")
    public ApiResponse<ReviewResponse.AddResultDTO> addReview(
            @RequestBody
            @Valid
            ReviewRequest.AddDTO request) {

        Review review = reviewCommandService.addReview(request);
        return ApiResponse.onSuccess(ReviewConverter.toAddResponse(review));
    }

    @PostMapping("/member/mission/add")
    public ApiResponse<MissionResponse.AddResultDTO> addMission(
            @RequestBody
            @Valid
            MissionRequest.AddDTO request) {
        Mission mission = missionCommandService.addMission(request);
        return ApiResponse.onSuccess(MissionConverter.toAddResponse(mission));
    }

    @PostMapping("/member/challenge-mission")
    public ApiResponse<MissionResponse.ChallengeResultDTO> challengeMission(
            @RequestBody
            @Valid
            MissionRequest.ChallengeDTO request) {

        MemberMission memberMission = missionCommandService.challengeMission(request);
        return ApiResponse.onSuccess(MissionConverter.toChallengeResponse(memberMission));
    }

    @GetMapping("/store/{storeId}/review")
    public ApiResponse<ReviewResponse.ReviewPreviewListDTO> findReviewByStore(
            @PathVariable(name = "storeId")
            @NotNull(message = "매장아이디가 없어요.")
            Long storeId,

            @RequestParam(name = "page")
            @NotNull(message = "페이지가 없습니다.")
            @PositiveOrZero(message = "페이지는 0보다 크거나 같아야 합니다.")
            Integer page,

            @RequestParam(name = "size", defaultValue = "10")
            Integer size) {

        Page<Review> reviewList = reviewQueryService.findReviewByStore(storeId, page, size);
        return ApiResponse.onSuccess(ReviewConverter.toReviewListResponse(reviewList));
    }

    @GetMapping("/member/{memberId}/review")
    public ApiResponse<ReviewResponse.ReviewPreviewListDTO> findReviewByMember(
            @PathVariable(name = "memberId")
            @NotNull(message = "멤버아이디가 없어요.")
            Long memberId,

            @RequestParam(name = "page")
            @NotNull(message = "페이지가 없습니다.")
            @PositiveOrZero(message = "페이지는 0보다 크거나 같아야 합니다.")
            Integer page,

            @RequestParam(name = "size", defaultValue = "10")
            Integer size) {

        Page<Review> reviewList = reviewQueryService.findReviewByMember(memberId, page, size);
        return ApiResponse.onSuccess(ReviewConverter.toReviewListResponse(reviewList));
    }

    @GetMapping("/store/{storeId}/mission")
    public ApiResponse<MissionResponse.findResultListDTO> findMissionByStore(
            @PathVariable(name = "storeId")
            @NotNull(message = "매장아이디가 없어요.")
            Long storeId,

            @RequestParam(name = "page")
            @NotNull(message = "페이지가 없습니다.")
            @PositiveOrZero(message = "페이지는 0보다 크거나 같아야 합니다.")
            Integer page,

            @RequestParam(name = "size", defaultValue = "10")
            Integer size) {

        Page<Mission> missionList = missionQueryService.findMissionByStore(storeId, page, size);
        return ApiResponse.onSuccess(MissionConverter.toResultListDTO(missionList));
    }
}