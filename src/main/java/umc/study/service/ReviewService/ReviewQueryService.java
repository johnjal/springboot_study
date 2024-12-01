package umc.study.service.ReviewService;

import umc.study.domain.Review;

import java.util.Optional;

public interface ReviewQueryService {

    Optional<Review> findReview(Long id);
    Review createReview(String body, Float score, Long storeId, Long memberId);
}
