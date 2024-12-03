package umc.study.converter;

import umc.study.domain.Member;
import umc.study.domain.Review;
import umc.study.domain.Store;
import umc.study.web.dto.ReviewRequest;
import umc.study.web.dto.ReviewResponse;

import java.util.ArrayList;

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
}
