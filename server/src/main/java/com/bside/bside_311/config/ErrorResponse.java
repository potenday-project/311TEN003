package com.bside.bside_311.config;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ErrorResponse {

    private int httpStatus;

    private String errorMessage;

    private String detailMessage;

    private List<FieldError> errors = new ArrayList<>();

    @Builder
    public ErrorResponse(int httpStatus, String errorMessage, String detailMessage, List<FieldError> errors) {
        super();
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
        this.detailMessage = detailMessage;
        this.errors = errors;
    }

    public ErrorResponse(int httpStatus, String errorMessage, String detailMessage) {
        super();
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
        this.detailMessage = detailMessage;
    }

    public ErrorResponse(int httpStatus, String errorMessage ) {
        super();
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }

    @Getter
    public static class FieldError {
        private String field;
        private String value;
        private String reason;

        @Builder
        public FieldError(String field, String value, String reason) {
            this.field = field;
            this.value = value;
            this.reason = reason;
        }
    }
}