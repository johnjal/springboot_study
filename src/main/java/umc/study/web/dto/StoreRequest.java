package umc.study.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class StoreRequest {

    @Getter
    public static class AddDTO {

        @NotBlank(message = "이름이 없습니다.")
        private String name;

        @NotBlank(message = "주소가 없습니다.")
        private String address;

        @NotNull(message = "지역이 없습니다.")
        private Long regionId;
    }
}
