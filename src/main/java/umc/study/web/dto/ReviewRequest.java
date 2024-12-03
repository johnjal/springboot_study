package umc.study.web.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

public class ReviewRequest {

    @Getter
    public static class AddDTO {

        @NotBlank(message = "내용이 없습니다.")
        private String body;

        @NotNull(message = "점수가 없습니다.")
        @DecimalMin(value = "0.0", inclusive = true, message = "값은 최소 0.0 이상이어야 합니다.")
        @DecimalMax(value = "5.0", inclusive = true, message = "값은 최대 5.0 이하여야 합니다.")
        private Float score;

        @NotNull(message = "매장아이디가 없습니다.")
        private Long storeId;

        @NotNull(message = "멤버아이디가 없습니다.")
        private Long memberId;

        @NotNull(message = "빈 브라켓이라도 좀 줘요. 처리하기 귀찮아요. 미안해요.")
        private List<String> reviewImgURL;
    }
}
