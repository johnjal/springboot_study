package umc.study.service.StoreService;

import umc.study.domain.Store;
import umc.study.web.dto.StoreRequest;

public interface StoreCommandService {
    Store addStore(StoreRequest.AddDTO request);
}
