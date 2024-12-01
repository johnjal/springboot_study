package umc.study.repository.StoreRepository;

import umc.study.domain.Store;

import java.util.List;

public interface StoreRepositoryCustom {
    List<Store> dynamicQuery(String name, Float score);
}