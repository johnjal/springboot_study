package umc.study.service.MemberService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import umc.study.apiPayLoad.code.status.ErrorStatus;
import umc.study.apiPayLoad.exception.handler.GeneralHandler;
import umc.study.converter.MemberConverter;
import umc.study.domain.FoodCategory;
import umc.study.domain.Member;
import umc.study.domain.mapping.MemberPrefer;
import umc.study.repository.FoodCategoryRepository.FoodCategoryRepository;
import umc.study.repository.MemberRepository.MemberRepository;
import umc.study.web.dto.MemberRequest;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final FoodCategoryRepository foodCategoryRepository;

    @Override
    @Transactional
    public Member joinMember(MemberRequest.JoinDto request) {

        Member newMember = MemberConverter.toMember(request);
        List<FoodCategory> foodCategoryList = request.getPreferCategory().stream()
                .map(category -> foodCategoryRepository.findById(category).orElseThrow(() -> new GeneralHandler(ErrorStatus.FOOD_CATEGORY_NOT_FOUND))).toList();

        List<MemberPrefer> memberPreferList = MemberConverter.toMemberPreferList(foodCategoryList, newMember);

        memberPreferList.forEach(memberPrefer -> {newMember.getMemberPreferList().add(memberPrefer);});

        return memberRepository.save(newMember);
    }
}
