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
    public Page<Mission> findByRegion(Region region, Pageable pageable, List<Long> exceptionList) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (region != null) {
            predicate.and(mission.region.eq(region));
        }

        if (exceptionList != null && !exceptionList.isEmpty()) {
            predicate.and(mission.id.notIn(exceptionList));
        }

        // Pagination. 매번 total 계산하는 비효율성이 존재. Cache 로 해결 가능
        List<Mission> missions = jpaQueryFactory
                .selectFrom(mission)
                .join(mission.store).fetchJoin() // N+1
                .join(mission.region).fetchJoin() // N+1
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(mission)
                .join(mission.store).fetchJoin() // N+1
                .join(mission.region).fetchJoin() // N+1
                .where(predicate)
                .fetchCount();

        return new PageImpl<>(missions, pageable, total);
    }
}
