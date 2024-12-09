package umc.study.repository.MissionRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import umc.study.domain.Mission;
import umc.study.domain.QMission;
import umc.study.domain.Region;
import umc.study.domain.Store;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MissionRepositoryImpl implements MissionRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    private final QMission mission = QMission.mission;

    @Override
    public List<Mission> findByIDList(List<Long> idList) {
        BooleanBuilder predicate = new BooleanBuilder();

        return jpaQueryFactory
                .selectFrom(mission)
                .join(mission.store).fetchJoin() // N+1
                .join(mission.region).fetchJoin() // N+1
                .where(predicate)
                .fetch();
    }

    @Override
    public Page<Mission> findByRegion(Region targetRegion, Pageable pageable, List<Long> exceptionList) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (targetRegion != null) {
            predicate.and(mission.region.id.eq(targetRegion.getId()));
        }

        if (exceptionList != null && !exceptionList.isEmpty()) {
            predicate.and(mission.id.notIn(exceptionList));
        }

        List<Mission> missions = jpaQueryFactory
                .selectFrom(mission)
                .join(mission.store).fetchJoin() // N+1
                .join(mission.region).fetchJoin() // N+1
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .select(mission.count())
                .from(mission)
                .where(predicate)
                .fetchFirst();

        return new PageImpl<>(missions, pageable, total);
    }

    @Override
    public Page<Mission> findByStore(Store store, Pageable pageable) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (store != null) {
            predicate.and(mission.store.id.eq(store.getId()));
        }

        List<Mission> missions = jpaQueryFactory
                .selectFrom(mission)
                .join(mission.store).fetchJoin() // N+1
                .join(mission.region).fetchJoin() // N+1
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .select(mission.count())
                .from(mission)
                .where(predicate)
                .fetchFirst();

        return new PageImpl<>(missions, pageable, total);
    }
}
