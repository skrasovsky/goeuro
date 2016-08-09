package com.goeuro.core.api;

import com.goeuro.core.CommandContextBuilderException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.goeuro.service.logger.CustomConsoleLogger.error;
import static com.goeuro.service.logger.CustomConsoleLogger.message;

/**
 * API command context.
 *
 * Contains all data for HTTP request:
 */
public class APICommandContext {

    private static final String defaultHttpMethod = "GET";

    /**
     * Http request method
     */
    private String httpMethod;

    /**
     * Http request headers
     */
    private Map<String, String> headers;

    /**
     * Http request path parameters
     */
    private Map<String, String> pathParams;

    /**
     * Http request query string
     */
    private String queryString;

    /**
     * Http request body
     */
    private String body;

    private APICommandContext(final Builder builder) {
        this.httpMethod = builder.httpMethod;
        this.headers = builder.headers;
        this.pathParams = builder.pathParams;
        this.queryString = builder.queryString;
        this.body = builder.body;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getPathParams() {
        return pathParams;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getBody() {
        return body;
    }

    /**
     * A nested builder class to create <code>APICommandContext</code> instances
     * using descriptive methods.
     * <p>
     * Example usage:
     * <pre>
     * APICommandContext apiCommandContext = APICommandContext.builder()
     *     .httpMethod("GET")
     *     .pathParam(new HashMap<String, String>())
     *     .build();
     * </pre>
     *
     */
    public static final class Builder {

        /**
         * Http request method
         */
        private String httpMethod;

        /**
         * Http request headers
         */
        private Map<String, String> headers;

        /**
         * Http request path parameters
         */
        private Map<String, String> pathParams;

        /**
         * Http request query string
         */
        private String queryString;

        /**
         * Http request body
         */
        private String body;

        public Builder httpMethod(String[] httpMethodArgs) {
            if (ArrayUtils.isEmpty(httpMethodArgs)) {
                this.httpMethod = defaultHttpMethod;
            } else {
                this.httpMethod = httpMethodArgs[0];
            }
            return this;
        }

        public Builder headers(String[] headersArgs) {
            this.headers = parseParam(headersArgs);
            return this;
        }

        public Builder pathParams(String[] pathParamArgs) {
            this.pathParams = parseParam(pathParamArgs);
            return this;
        }

        public Builder queryString(String[] queryStringArgs) {
            this.queryString = prepareQueryString(queryStringArgs);
            return this;
        }

        public Builder body(String[] body) {
            if (ArrayUtils.isEmpty(body)) {
                this.body = StringUtils.EMPTY;
            } else {
                this.body = body[0];
            }
            return this;
        }

        public APICommandContext build() {
            return new APICommandContext(this);
        }

        private Map<String, String> parseParam(String[] params) {
            Map<String, String> paramMap;
            if (ArrayUtils.isEmpty(params)) {
                return Collections.<String, String>emptyMap();
            }

            paramMap = new HashMap<>(params.length, 1f);
            for (String p : params) {
                String[] param = p.split("=");
                if (param.length != 2) {
                    String message = message("Wrong format of the command line argument %s. It should be <name=value>.", p);
                    error(message);
                    throw new CommandContextBuilderException(message);
                }

                String name = param[0];
                String value = param[1];
                paramMap.put(name, value);
            }

            return paramMap;
        }

        private String prepareQueryString(String[] queryStringArgs) {
            if (ArrayUtils.isEmpty(queryStringArgs)) {
                return StringUtils.EMPTY;
            }
            return "?" + String.join("&", queryStringArgs);
        }

    }
}
