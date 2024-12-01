package umc.study.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.study.domain.Region;
import umc.study.domain.Store;

import java.time.LocalDate;

public class StudyResponse {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionDTO{
        private Long id;
        private Integer reward;
        private LocalDate deadline;
        private String missionSpec;
        private Store store;
        private Region region;
    }
}
