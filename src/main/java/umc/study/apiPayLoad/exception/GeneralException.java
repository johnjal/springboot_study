package umc.study.apiPayLoad.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import umc.study.apiPayLoad.code.BaseErrorCode;
import umc.study.apiPayLoad.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

    private BaseErrorCode code;

    public ErrorReasonDTO getErrorReason() {
        return this.code.getReason();
    }

    public ErrorReasonDTO getErrorReasonHttpStatus(){
        return this.code.getReasonHttpStatus();
    }
}