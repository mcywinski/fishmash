package net.elenx.fishmash.rest;

public interface RestListener<ResultClass>
{
    ResultClass makeRequest();
    void onFailure();
    void onSuccess(ResultClass result);
}
