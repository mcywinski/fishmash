package net.elenx.fishmash.updaters;

import android.util.Log;

import net.elenx.fishmash.Constant;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

class FishmashRest extends RestTemplate
{
    FishmashRest()
    {
        int timeoutInMilliseconds = Constant.TIMEOUT_IN_SECONDS * 1000;

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
    public <T> T getForObject(URI url, Class<T> responseType) throws RestClientException
    {
        Log.e("getForObject-simple", url.toString());

        return super.getForObject(url, responseType);
    }

    @Override
    public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> urlVariables) throws RestClientException
    {
        Log.e("getForObject-map", url);

        for(String key : urlVariables.keySet())
        {
            Log.e(key, urlVariables.get(key).toString());
        }

        return super.getForObject(url, responseType, urlVariables);
    }

    @Override
    public <T> T getForObject(String url, Class<T> responseType, Object... urlVariables) throws RestClientException
    {
        Log.e("getForObject-many", url);

        for(Object object : urlVariables)
        {
            Log.e(object.getClass().getSimpleName(), object.toString());
        }

        return super.getForObject(url, responseType, urlVariables);
    }

    @Override
    public <T> T postForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException
    {
        Log.e("postForObject-map", url);

        for(String key : uriVariables.keySet())
        {
            Log.e(key, uriVariables.get(key).toString());
        }

        return super.postForObject(url, request, responseType, uriVariables);
    }

    @Override
    public <T> T postForObject(URI url, Object request, Class<T> responseType) throws RestClientException
    {
        Log.e("postForObject-simple", url.toString());

        return super.postForObject(url, request, responseType);
    }

    @Override
    public <T> T postForObject(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException
    {
        Log.e("postForObject-many", url);

        for(Object object : uriVariables)
        {
            Log.e(object.getClass().getSimpleName(), object.toString());
        }

        return super.postForObject(url, request, responseType, uriVariables);
    }
}
