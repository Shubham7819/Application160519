package com.example.ideal48.application160519;

public class RequestFailure {

    private Retryable retryable;
    private String errorMessage;

    RequestFailure(Retryable retryable, String errorMessage) {
        this.retryable = retryable;
        this.errorMessage = errorMessage;
    }

    public Retryable getRetryable() {
        return retryable;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
