package net.elenx.fishmash.updaters;

import android.util.Log;

import com.google.gson.Gson;

import net.elenx.fishmash.utilities.Fishmash;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;
import java.util.Map;

class RestInterceptor extends RestTemplate
{
    private final static Gson gson = new Gson();

    RestInterceptor()
    {
        int timeoutInMilliseconds = Fishmash.API_TIMEOUT_IN_SECONDS * 1000;

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

        List<HttpMessageConverter<?>> httpMessageConverterList = getMessageConverters();
        httpMessageConverterList.add(new StringHttpMessageConverter());
        httpMessageConverterList.add(new MappingJackson2HttpMessageConverter());
        httpMessageConverterList.add(new GsonHttpMessageConverter());
    }

    @Override
    public <T> T getForObject(URI url, Class<T> responseType) throws RestClientException
    {
        Log.e("getForObject-simple", url.toString());

        T response = super.getForObject(url, responseType);
        Log.e("response", gson.toJson(response));

        return response;
    }

    @Override
    public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> urlVariables) throws RestClientException
    {
        Log.e("getForObject-map", url);
        Log.e("variables", gson.toJson(urlVariables));

        T response = super.getForObject(url, responseType, urlVariables);
        Log.e("response", gson.toJson(response));

        return response;
    }

    @Override
    public <T> T getForObject(String url, Class<T> responseType, Object... urlVariables) throws RestClientException
    {
        Log.e("getForObject-many", url);
        Log.e("variables", gson.toJson(urlVariables));

        T response = super.getForObject(url, responseType, urlVariables);
        Log.e("response", gson.toJson(response));

        return response;
    }

    @Override
    public <T> T postForObject(String url, Object request, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException
    {
        Log.e("postForObject-map", url);
        Log.e("variables", gson.toJson(uriVariables));

        if(request != null)
        {
            Log.e("request", gson.toJson(request));
        }

        T response = super.postForObject(url, request, responseType, uriVariables);
        Log.e("response", gson.toJson(response));

        return response;
    }

    @Override
    public <T> T postForObject(URI url, Object request, Class<T> responseType) throws RestClientException
    {
        Log.e("postForObject-simple", url.toString());

        if(request != null)
        {
            Log.e("request", gson.toJson(request));
        }

        T response = super.postForObject(url, request, responseType);
        Log.e("response", gson.toJson(response));

        return response;
    }

    @Override
    public <T> T postForObject(String url, Object request, Class<T> responseType, Object... uriVariables) throws RestClientException
    {
        Log.e("postForObject-many", url);
        Log.e("variables", gson.toJson(uriVariables));

        if(request != null)
        {
            Log.e("request", gson.toJson(request));
        }

        T response = super.postForObject(url, request, responseType, uriVariables);
        Log.e("response", gson.toJson(response));

        return response;
    }
}
