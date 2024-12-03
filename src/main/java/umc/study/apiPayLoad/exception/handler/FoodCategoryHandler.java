package umc.study.apiPayLoad.exception.handler;

import umc.study.apiPayLoad.code.BaseErrorCode;
import umc.study.apiPayLoad.exception.GeneralException;

public class FoodCategoryHandler extends GeneralException {

    public FoodCategoryHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
