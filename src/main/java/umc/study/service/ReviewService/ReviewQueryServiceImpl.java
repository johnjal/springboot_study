package umc.study.service.ReviewService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.study.apiPayLoad.code.status.ErrorStatus;
import umc.study.apiPayLoad.exception.handler.GeneralHandler;
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

    @Override
    public Page<Review> findReviewByStore(Long storeId, Integer page, Integer size) {
        Store store = storeRepository.findById(storeId).orElseThrow(() -> new GeneralHandler(ErrorStatus.STORE_NOT_FOUND));

        return reviewRepository.findAllByStoreCustom(store, PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }

    @Override
    public Page<Review> findReviewByMember(Long memberId, Integer page, Integer size) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new GeneralHandler(ErrorStatus.MEMBER_NOT_FOUND));

        return reviewRepository.findAllByMemberCustom(member, PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }
}
