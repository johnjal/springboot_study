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
import umc.study.domain.enums.MissionStatus;
import umc.study.domain.mapping.QMemberMission;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberMissionRepositoryImpl implements MemberMissionRepositoryCustom{
    private final JPAQueryFactory jpaQueryFactory;
    private final QMemberMission memberMission = QMemberMission.memberMission;

    @Override
    public List<Long> findMissionIdByMemberAndStatus(Member member, MissionStatus missionStatus){
        BooleanBuilder predicate = new BooleanBuilder();

        if (member != null){
            predicate.and(memberMission.member.id.eq(member.getId()));
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
    public Page<Mission> findMissionDynamicQuery(Member targetMember, MissionStatus missionStatus, Pageable pageable){
        BooleanBuilder predicate = new BooleanBuilder();

        if (targetMember != null){
            predicate.and(memberMission.member.id.eq(targetMember.getId()));
        }

        if (missionStatus != null){
            predicate.and(memberMission.status.eq(missionStatus));
        }

        List<Mission> missions = jpaQueryFactory
                .select(memberMission.mission)
                .from(memberMission)
                .join(memberMission.mission.store).fetchJoin() // N+1
                .join(memberMission.mission.region).fetchJoin() // N+1
                .where(predicate)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .select(memberMission.count())
                .from(memberMission)
                .where(predicate)
                .fetchFirst();

        return new PageImpl<>(missions, pageable, total);
    }

}
