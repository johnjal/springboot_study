package umc.study.repository.ReviewRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import umc.study.domain.Member;
import umc.study.domain.QReview;
import umc.study.domain.Review;
import umc.study.domain.Store;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private QReview review;

    @Override
    public Page<Review> findAllByStoreCustom(Store targetStore, Pageable pageable) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (targetStore != null){
            predicate.and(review.store.eq(targetStore));
        }

        List<Review> reviews = jpaQueryFactory
                .select(review)
                .join(review.member).fetchJoin() // N+1
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .select(review.count())
                .from(review)
                .where(predicate)
                .fetchFirst();

        return new PageImpl<>(reviews, pageable, total);
    }

    @Override
    public Page<Review> findAllByMemberCustom(Member targetMember, Pageable pageable) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (targetMember != null){
            predicate.and(review.member.id.eq(targetMember.getId()));
        }

        List<Review> reviews = jpaQueryFactory
                .select(review)
                .join(review.member).fetchJoin() // N+1
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .select(review.count())
                .from(review)
                .where(predicate)
                .fetchFirst();

        return new PageImpl<>(reviews, pageable, total);
    }
}
