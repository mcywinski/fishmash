package net.elenx.fishmash.models.adapters;

import com.fasterxml.jackson.annotation.JsonProperty;

// it's used by spring-android
// and cannot be local, because we will serialize it to json
@SuppressWarnings({"unused", "FieldCanBeLocal"})
public class PasswordResult
{
    private boolean success;

    @JsonProperty("result_status")
    private long resultStatus;

    PasswordResult()
    {
        // required by spring-android
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public long getResultStatus()
    {
        return resultStatus;
    }

    public void setResultStatus(long resultStatus)
    {
        this.resultStatus = resultStatus;
    }
}
