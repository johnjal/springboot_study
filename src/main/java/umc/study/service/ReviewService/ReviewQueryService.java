package umc.study.service.ReviewService;

import org.springframework.data.domain.Page;
import umc.study.domain.Review;

import java.util.Optional;

public interface ReviewQueryService {

    Optional<Review> findReview(Long id);
    Review createReview(String body, Float score, Long storeId, Long memberId);
    Page<Review> findReviewByStore(Long storeId, Integer page, Integer size);
    Page<Review> findReviewByMember(Long memberId, Integer page, Integer size);
}
