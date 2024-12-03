package umc.study.converter;

import umc.study.domain.Region;
import umc.study.domain.Review;
import umc.study.domain.Store;
import umc.study.web.dto.StoreRequest;
import umc.study.web.dto.StoreResponse;

import java.util.ArrayList;

public class StoreConverter {

    public static Store addDTOtoStore(StoreRequest.AddDTO request, Region region){

        return Store.builder()
                .name(request.getName())
                .score(Float.intBitsToFloat(0))
                .address(request.getAddress())
                .reviewList(new ArrayList<Review>())
                .region(region)
                .build();
    }

    public static StoreResponse.AddResultDTO toAddResponse(Store store){

        return StoreResponse.AddResultDTO.builder()
                .id(store.getId())
                .createdAt(store.getCreatedAt())
                .build();
    }
}
