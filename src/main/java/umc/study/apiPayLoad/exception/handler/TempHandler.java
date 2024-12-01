package umc.study.apiPayLoad.exception.handler;

import umc.study.apiPayLoad.code.BaseErrorCode;
import umc.study.apiPayLoad.exception.GeneralException;

public class TempHandler extends GeneralException {

    public TempHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}