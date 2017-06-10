package com.ceeses.dto;

import java.util.List;

/**
 * Created by zhaoshan on 2017/6/3.
 * 初步确定的输出结果
 * 可能需要调整
 */
public class ProbabilityCalaResponse {

    private String errorCode;

    private boolean result;

    private List<ProbabilityCalaDTO>  probabilityCalaDTOs;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public List<ProbabilityCalaDTO> getProbabilityCalaDTOs() {
        return probabilityCalaDTOs;
    }

    public void setProbabilityCalaDTOs(List<ProbabilityCalaDTO> probabilityCalaDTOs) {
        this.probabilityCalaDTOs = probabilityCalaDTOs;
    }
}
