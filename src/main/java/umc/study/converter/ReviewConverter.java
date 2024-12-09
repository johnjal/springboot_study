package umc.study.converter;

import org.springframework.data.domain.Page;
import umc.study.domain.Member;
import umc.study.domain.Review;
import umc.study.domain.Store;
import umc.study.web.dto.ReviewRequest;
import umc.study.web.dto.ReviewResponse;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ReviewConverter {

    public static Review addDTOtoReview(ReviewRequest.AddDTO request, Store store, Member member) {
        return Review.builder()
                .body(request.getBody())
                .score(request.getScore())
                .reviewImageList(new ArrayList<>())
                .store(store)
                .member(member)
                .build();
    }

    public static ReviewResponse.AddResultDTO toAddResponse(Review review) {
        return ReviewResponse.AddResultDTO.builder()
                .reviewId(review.getId())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public static ReviewResponse.ReviewPreViewDTO toReviewResponse(Review review) {
        return ReviewResponse.ReviewPreViewDTO.builder()
                .id(review.getId())
                .nickName(review.getMember().getName())
                .body(review.getBody())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public static ReviewResponse.ReviewPreviewListDTO toReviewListResponse(Page<Review> reviewList) {
        return ReviewResponse.ReviewPreviewListDTO.builder()
                .isLast(reviewList.isLast())
                .isFirst(reviewList.isFirst())
                .totalPage(reviewList.getTotalPages())
                .totalElements(reviewList.getTotalElements())
                .listSize(reviewList.getSize())
                .reviewList(reviewList.stream()
                        .map(ReviewConverter::toReviewResponse).collect(Collectors.toList()))
                .build();
    }
}
