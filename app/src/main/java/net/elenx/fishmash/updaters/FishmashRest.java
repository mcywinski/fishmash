package net.elenx.fishmash.updaters;

import android.util.Log;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class FishmashRest extends RestTemplate
{
    FishmashRest(int timeoutInSeconds)
    {
        int timeoutInMilliseconds = timeoutInSeconds * 1000;

        ClientHttpRequestFactory clientHttpRequestFactory = getRequestFactory();

        if(clientHttpRequestFactory instanceof SimpleClientHttpRequestFactory)
        {
            SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = (SimpleClientHttpRequestFactory) clientHttpRequestFactory;

            simpleClientHttpRequestFactory.setConnectTimeout(timeoutInMilliseconds);
            simpleClientHttpRequestFactory.setReadTimeout(timeoutInMilliseconds);
        }
        else if(clientHttpRequestFactory instanceof HttpComponentsClientHttpRequestFactory)
        {
            HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory = (HttpComponentsClientHttpRequestFactory) clientHttpRequestFactory;

            httpComponentsClientHttpRequestFactory.setReadTimeout(timeoutInMilliseconds);
            httpComponentsClientHttpRequestFactory.setConnectTimeout(timeoutInMilliseconds);
        }
    }

    @Override
    public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> urlVariables) throws RestClientException
    {
        for(String key : urlVariables.keySet())
        {
            Log.e(key, urlVariables.get(key).toString());
        }

        return super.getForObject(url, responseType, urlVariables);
    }

    @Override
    public <T> T postForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException
    {
        for(String key : uriVariables.keySet())
        {
            Log.e(key, uriVariables.get(key).toString());
        }

        return super.postForObject(url, request, responseType, uriVariables);
    }
}
