package com.goeuro.command;

import com.goeuro.core.api.API;
import com.goeuro.core.api.APICommand;
import com.goeuro.core.api.APICommandContext;
import com.goeuro.core.api.Command;

import static com.goeuro.service.logger.CustomConsoleLogger.error;

@API(
        name = "getLocationsByCityJSON",
        description = "<Not implemented. It is only for the demonstration.>"
)
public class GetLocationsByCityJSON extends APICommand implements Command {

    @Override
    public void execute(APICommandContext context) {
        // TODO Implement it.
    }

    @Override
    protected String getUrl() {
        // TODO Implement it.
        String message = "Not implemented";
        error(message);
        throw new UnsupportedOperationException(message);
    }

    @Override
    protected void validateContext() {
        // TODO Implement it.
    }

}
