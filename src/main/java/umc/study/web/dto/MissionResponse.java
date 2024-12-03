package umc.study.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import umc.study.domain.Region;
import umc.study.domain.Store;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MissionResponse {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MissionDTO{
        private Long missionId;
        private Integer reward;
        private LocalDate deadline;
        private String missionSpec;
        private Store store;
        private Region region;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddResultDTO {
        Long missionId;
        LocalDateTime createdAt;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChallengeResultDTO {
        Long memberMissionId;
        LocalDateTime createdAt;
    }
}
