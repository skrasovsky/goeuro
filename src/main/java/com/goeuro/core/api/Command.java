package com.goeuro.core.api;

/**
 * API command interface.
 */
public interface Command {

    /**
     * Execute http command
     *
     * @param context the http request context {@link APICommandContext}
     */
    void execute(APICommandContext context);

}
