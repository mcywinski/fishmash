package net.elenx.fishmash.models.adapters;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PasswordResult
{
    private boolean success;

    @JsonProperty("result_status")
    private long resultStatus;

    PasswordResult()
    {

    }

    public boolean isSuccess()
    {
        return success;
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
