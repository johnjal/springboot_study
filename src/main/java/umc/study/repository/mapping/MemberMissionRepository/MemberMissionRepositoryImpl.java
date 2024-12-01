package umc.study.repository.mapping.MemberMissionRepository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import umc.study.domain.Member;
import umc.study.domain.Mission;
import umc.study.domain.QMission;
import umc.study.domain.enums.MissionStatus;
import umc.study.domain.mapping.QMemberMission;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberMissionRepositoryImpl implements MemberMissionRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    private final QMemberMission memberMission = QMemberMission.memberMission;
    private final QMission mission = QMission.mission;

    @Override
    public List<Long> findMissionIdByMemberAndStatus(Member member, MissionStatus missionStatus){
        BooleanBuilder predicate = new BooleanBuilder();

        if (member != null){
            predicate.and(memberMission.member.eq(member));
        }

        if (missionStatus != null){
            predicate.and(memberMission.status.eq(missionStatus));
        }

        return jpaQueryFactory
                .select(memberMission.mission.id)
                .from(memberMission)
                .where(predicate)
                .fetch();
    }

    @Override
    public Page<Mission> findMissionDynamicQuery(Member member, MissionStatus missionStatus, Pageable pageable){
        BooleanBuilder predicate = new BooleanBuilder();

        if (member != null){
            predicate.and(memberMission.member.eq(member));
        }

        if (missionStatus != null){
            predicate.and(memberMission.status.eq(missionStatus));
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
