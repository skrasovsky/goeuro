package com.goeuro.core.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.goeuro.core.CommandProcessException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import static com.goeuro.service.logger.CustomConsoleLogger.error;
import static com.goeuro.service.logger.CustomConsoleLogger.message;

/**
 * Base implementation of API command.
 *
 * Class responsible for preparing a http request and sending to the endpoint.
 */
public abstract class APICommand {

    @Autowired
    private APIProperties properties;

    private final HttpClientBuilder HTTP_CLIENT_BUILDER = HttpClientBuilder.create();

    private final ObjectMapper JSON_MAPPER = new ObjectMapper();

    private APICommandContext context;

    public APICommandContext getContext() {
        return context;
    }

    public String getBaseUrl() {
        return properties.baseUrl();
    }

    public String getApiVersion() {
        return properties.apiVersion();
    }

    public String getDefaultLocale() {
        return properties.defaultLocale();
    }

    /**
     * Run process of execution API command.
     *
     * Method prepares HTTP request from api context command data, validate api command context
     * and then sends request to the endpoint.
     *
     * @param ctx the API command context {@link APICommandContext}
     * @param expectedClass determine type of instance which will be return by method
     * @param expectedStatus determine request status code which method will be expect
     * @param <T> type of expected instance
     * @return the instance of type <code>expectedClass</code>
     */
    protected <T> T run(APICommandContext ctx, Class<T> expectedClass, int expectedStatus) {

        setContext(ctx);

        if (!context.getHttpMethod().equals("GET")) {
            String message = "Application supports only GET requests. If you need more, please extend the library.";
            error(message);
            throw new IllegalArgumentException(message);
        }

        CloseableHttpResponse response = null;
        InputStream inputStream = null;
        try {

            HttpRequestBase request = createRequest();
            request.setURI(prepareUrl());
            request.setHeaders(prepareHeaders());

            if (!StringUtils.isEmpty(context.getBody()) && !context.getHttpMethod().equals("GET")) {
                ((HttpEntityEnclosingRequestBase) request).setEntity(prepareBody());
            }

            response = HTTP_CLIENT_BUILDER.build().execute(request);

            if (response.getStatusLine().getStatusCode() != expectedStatus) {
                String message = message("Wrong status of request '%s'. Response status is %d, but expected is %d.",
                        request.getURI().toString(), response.getStatusLine().getStatusCode(), expectedStatus);
                error(message);
                throw new IllegalStateException(message);
            }

            inputStream = response.getEntity().getContent();
            return JSON_MAPPER.readValue(inputStream, expectedClass);

        } catch (IOException e) {
            String message = "Obtain error while process the HTTP request.";
            error(message);
            throw new CommandProcessException(message, e);
        } finally {
            if (response != null) {
                HttpClientUtils.closeQuietly(response);
            }
            if (inputStream != null) {
                IOUtils.closeQuietly(inputStream);
            }
        }
    }

    /**
     * Takes from api command context the http method and creates
     * {@link org.apache.http.client.methods.HttpRequestBase}.
     *
     * @return {@link org.apache.http.client.methods.HttpRequestBase}
     */
    private HttpRequestBase createRequest() {

        // TODO Extend the list of supporting types of request

        switch (context.getHttpMethod()) {
            case "GET":
                return new HttpGet();
            default:
                String message = message("Unsupported type of HTTP request");
                error(message);
                throw new CommandProcessException(message);
        }
    }

    /**
     * Prepares http url from api command context data.
     *
     * @return the {@link java.net.URI}
     */
    private URI prepareUrl() {
        try {
            String urlTemplate = getUrl() + context.getQueryString();
            return new URI(urlTemplate);
        } catch (URISyntaxException e) {
            String message = message("Obtain error while preparing URL %s for the HTTP request.");
            error(message);
            throw new CommandProcessException(message);
        }
    }

    /**
     * Prepares http headers from api command context data.
     *
     * @return the array of {@link org.apache.http.Header}
     */
    private Header[] prepareHeaders() {
        if (context.getHeaders().isEmpty()) {
            return ArrayUtils.toArray();
        }

        return context
            .getHeaders()
            .entrySet()
            .stream()
            .map(h -> new BasicHeader(h.getKey(), h.getValue()))
            .toArray(size -> new Header[size]);
    }

    /**
     * Prepares http body from api command context data.
     *
     * @throws UnsupportedEncodingException
     */
    private ByteArrayEntity prepareBody() throws UnsupportedEncodingException {
        return new ByteArrayEntity(context.getBody().getBytes("UTF-8"));
    }

    /**
     * Initialize api command context and validate it.
     *
     * @param ctx the api command context {@link com.goeuro.core.api.APICommandContext}
     */
    private void setContext(APICommandContext ctx) {
        this.context = ctx;
        if (context == null) {
            String message = message("API command context is null");
            error(message);
            throw new CommandProcessException(message);
        }
        validateContext();
    }

    /**
     * Every API command has own implementation of building a http request url.
     *
     * @return The url of the http request
     */
    protected abstract String getUrl();

    /**
     * Every API command has own validation of command context implementation.
     * Use the exception {@link com.goeuro.core.InvalidCommandContextException} if
     * context is invalid.
     */
    protected abstract void validateContext();

}
