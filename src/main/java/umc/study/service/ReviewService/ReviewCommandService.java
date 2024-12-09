package umc.study.service.ReviewService;

import umc.study.domain.Review;
import umc.study.web.dto.ReviewRequest;

public interface ReviewCommandService {
    Review addReview(ReviewRequest.AddDTO request);
}
