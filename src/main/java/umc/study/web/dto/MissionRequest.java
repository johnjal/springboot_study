package umc.study.web.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.time.LocalDate;

public class MissionRequest {

    @Getter
    public static class AddDTO {

        @NotNull(message = "보상은 필수입니다.")
        @Positive(message = "보상은 양수여야 합니다.")
        private Integer reward;

        @NotNull(message = "마감일은 필수입니다.")
        @Future(message = "마감일은 미래여야 합니다.")
        private LocalDate deadline;

        @NotBlank(message = "미션 설명은 필수입니다.")
        private String missionSpec;

        @NotNull(message = "가게 ID는 필수입니다.")
        private Long storeId;
    }

    @Getter
    public static class ChallengeDTO {

        @NotNull(message = "미션아이디가 없습니다.")
        private Long missionId;

        @NotNull(message = "멤버아이디가 없습니다.")
        private Long memberId;
    }
}