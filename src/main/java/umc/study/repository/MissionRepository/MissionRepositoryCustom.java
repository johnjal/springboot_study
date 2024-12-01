package umc.study.repository.MissionRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import umc.study.domain.Mission;
import umc.study.domain.Region;

import java.util.List;

public interface MissionRepositoryCustom {
    List<Mission> findByIDList(List<Long> idList);
    Page<Mission> findByRegion(Region region, Pageable pageable, List<Long> exceptionList);
}
