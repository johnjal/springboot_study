package umc.study.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.study.domain.Member;
import umc.study.domain.Review;
import umc.study.domain.Store;
import umc.study.repository.MemberRepository.MemberRepository;
import umc.study.repository.ReviewRepository.ReviewRepository;
import umc.study.repository.StoreRepository.StoreRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;
    private final MemberRepository memberRepository;

    @Override
    public Optional<Review> findReview(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Review createReview(String body, Float score, Long storeId, Long memberId) {
        Store store = storeRepository.findById(storeId).orElse(null);
        Member member = memberRepository.findById(memberId).orElse(null);

        // 다 나눠서 에러 띄워주는게 좋긴할듯.
        if (body == null || body.isEmpty() || score == null || score < 0 || score > 5 || store == null || member == null) {
            throw new IllegalArgumentException("Some Inputs are Not Found.");
        }

        Review review = new Review(body, score, store, member);
        reviewRepository.save(review);
        return review;
    }
}
