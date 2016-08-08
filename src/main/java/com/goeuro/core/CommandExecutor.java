package com.goeuro.core;

import com.goeuro.core.api.APICommandContext;
import com.goeuro.core.api.Command;
import com.goeuro.core.cmd.CMDHelp;
import com.goeuro.core.cmd.CMDOptionsContainer;
import com.goeuro.core.cmd.CMDCommandProperties;
import com.goeuro.core.cmd.CMDQuite;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.goeuro.service.logger.CustomConsoleLogger.error;
import static com.goeuro.service.logger.CustomConsoleLogger.message;

/**
 * Executor of API command.
 *
 * Class takes command line arguments, parse them and decides which command will be execute.
 */
@Component
public class CommandExecutor {

    @Autowired
    private CMDCommandProperties properties;

    @Autowired
    private CMDOptionsContainer cmdOptionsContainer;

    @Autowired
    private CommandContainer commandContainer;

    @Autowired
    private CMDHelp cmdHelp;

    @Autowired
    private CMDQuite cmdQuite;

    /**
     * Parse command line arguments, create API command context, define API command and execute.
     *
     * @param args The array of command line arguments
     * @throws ParseException
     */
    public void execute(String[] args) throws ParseException {

        CommandLine commandLine = cmdOptionsContainer.parse(args);

        if (commandLine.hasOption(properties.help())) {
            cmdOptionsContainer.printCMDHelp();
            return;
        }

        if (commandLine.hasOption(properties.quit())) {
            cmdQuite.quit();
        }

        if (commandLine.hasOption(properties.commandList())) {
            cmdHelp.apiCommandHelp(commandContainer.getHelp());
            return;
        }

        Command command = fetchCommand(commandLine.getOptionValues(properties.command()));

        APICommandContext context = prepareCommandContext(commandLine);

        command.execute(context);
    }

    /**
     * Prepare api command context {@link com.goeuro.core.api.APICommandContext}
     *
     * @param commandLine the command line arguments
     * @return the api command context {@link com.goeuro.core.api.APICommandContext}
     */
    private APICommandContext prepareCommandContext(CommandLine commandLine) {
         return APICommandContext.builder()
            .httpMethod(commandLine.getOptionValues(properties.httpMethod()))
            .headers(commandLine.getOptionValues(properties.header()))
            .pathParams(commandLine.getOptionValues(properties.pathParam()))
            .queryString(commandLine.getOptionValues(properties.queryString()))
                 .body(commandLine.getOptionValues(properties.body()))
                 .build();
    }

    /**
     * Parse command line argument and fetch {@link com.goeuro.core.api.Command}
     * from {@link com.goeuro.core.CommandContainer}
     *
     * @param commandArgs The command line argument
     * @return the {@link com.goeuro.core.api.Command}
     */
    private Command fetchCommand(String[] commandArgs) {
        if (ArrayUtils.isEmpty(commandArgs)) {
            String message = "Command is not specified.";
            error(message);
            throw new CommandNotFoundException(message);
        }

        Command command = commandContainer.getByName(commandArgs[0]);

        if (command == null) {
            String message = message("Command with name %s is not found", commandArgs[0]);
            error(message);
            throw new CommandNotFoundException(message);
        }

        return command;
    }

}
