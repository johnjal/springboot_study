package umc.study.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

public class MemberRequest {

    @Getter
    public static class JoinDto{

        @NotBlank(message = "이름이 없습니다")
        String name;

        @NotNull(message = "성별이 없습니다")
        Integer gender;

        @NotNull(message = "출생년도가 없습니다")
        Integer birthYear;

        @NotNull(message = "출생월이 없습니다")
        Integer birthMonth;

        @NotNull(message = "출생일이 없습니다")
        Integer birthDay;

        @NotBlank(message = "주소가 없습니다")
        String address;

        @NotBlank(message = "상세주소가 없습니다")
        String specAddress;

        @NotNull(message = "빈 브라켓이라도 좀 줘요.")
        List<Long> preferCategory;
    }
}
