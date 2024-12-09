package umc.study.repository.ReviewRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.study.domain.Member;
import umc.study.domain.Review;
import umc.study.domain.Store;

public interface ReviewRepositoryCustom {
    Page<Review> findAllByStoreCustom(Store targetStore, Pageable pageable);
    Page<Review> findAllByMemberCustom(Member targetMember, Pageable pageable);
}
