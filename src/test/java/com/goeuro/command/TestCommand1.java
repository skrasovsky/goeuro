package com.goeuro.command;

import com.goeuro.core.api.API;
import com.goeuro.core.api.APICommand;
import com.goeuro.core.api.APICommandContext;
import com.goeuro.core.api.Command;

@API(
        name = "testCommand1",
        description = "Test command description",
        template = "",
        allowedParams = {""},
        example = ""
)
public class TestCommand1 extends APICommand implements Command {

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void validateContext() {

    }

    @Override
    public void execute(APICommandContext context) {

    }
}
