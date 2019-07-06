package com.example.ideal48.application160519;

public class NetworkState {

    public enum Status{
        RUNNING,
        SUCCESS,
        FAILED
    }
    private final Status status;
    private final String msg;
    private Retryable retryable;

    public static final NetworkState LOADED;
    public static final NetworkState LOADING;

    public NetworkState(Retryable retryable, Status status, String msg) {
        this.retryable = retryable;
        this.status = status;
        this.msg = msg;
    }

    static {
        Retryable emptyRetryable = new Retryable() {
            @Override
            public void retry() {
                // do nothing
            }
        };
        LOADED = new NetworkState(emptyRetryable, Status.SUCCESS,"Success");
        LOADING = new NetworkState(emptyRetryable, Status.RUNNING,"Running");
    }

    public Status getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public Retryable getRetryable() {
        return retryable;
    }

    public void setRetryable(Retryable retryable) {
        this.retryable = retryable;
    }

}
