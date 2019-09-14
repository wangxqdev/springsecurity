package com.tec.anji.www.core.validate.code;

import com.tec.anji.www.core.properties.SecurityConstants;

public enum ValidateCodeType {

    IMAGE {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
        }
    }, SMS {
        @Override
        public String getParamNameOnValidate() {
            return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
        }
    };

    public abstract String getParamNameOnValidate();
}
