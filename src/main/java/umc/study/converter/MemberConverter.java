package umc.study.converter;

import umc.study.domain.FoodCategory;
import umc.study.domain.Member;
import umc.study.domain.enums.Gender;
import umc.study.domain.mapping.MemberPrefer;
import umc.study.web.dto.MemberRequest;
import umc.study.web.dto.MemberResponse;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MemberConverter {

    public static MemberResponse.JoinResultDTO toJoinResultDTO(Member member){
        return MemberResponse.JoinResultDTO.builder()
                .memberId(member.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static Member toMember(MemberRequest.JoinDto request){

        Gender gender = switch (request.getGender()) {
            case 1 -> Gender.MALE;
            case 2 -> Gender.FEMALE;
            case 3 -> Gender.NONE;
            default -> null;
        };

        return Member.builder()
                .address(request.getAddress())
                .specAddress(request.getSpecAddress())
                .gender(gender)
                .name(request.getName())
                .memberPreferList(new ArrayList<>())// 리스트는 수동으로 초기화 할 필요 없다. 뭔가 잘못 알고있는듯
                .build();
    }

    public static List<MemberPrefer> toMemberPreferList(List<FoodCategory> preferList, Member targetMember){
        return preferList.stream()
                .map(target -> {return MemberPrefer.builder().member(targetMember).foodCategory(target).build();})
                .toList();
    }

    public static  MemberResponse.JoinResultDTO toMemberResponse(Member member){
        return MemberResponse.JoinResultDTO.builder()
                .memberId(member.getId())
                .createdAt(member.getCreatedAt())
                .build();
    }
}
