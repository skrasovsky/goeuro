package com.goeuro.core.cmd;

import com.goeuro.core.CommandHelp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static com.goeuro.service.logger.CustomConsoleLogger.info;

/**
 * Help command line command. Print help information of API commands.
 */
@Component
public class CMDHelp {

    @Autowired
    private CMDCommandProperties properties;

    /**
     * Print help of available API command.
     */
    public void apiCommandHelp(Collection<CommandHelp> commandHelps) {
        StringBuilder helpBuilder = new StringBuilder();
        helpBuilder.append(properties.apiCommandHelpTitle());
        commandHelps.stream().forEach(help -> helpBuilder.append(getCommandHelp(help)));
        helpBuilder.append("\n");
        info(helpBuilder.toString());
    }

    private String getCommandHelp(CommandHelp commandHelp) {
        return String.format(properties.apiCommandHelpTemplate(),
                commandHelp.getName(),
                commandHelp.getDescription(),
                commandHelp.getTemplate(),
                String.join("; ", commandHelp.getAllowedParams()),
                commandHelp.getExample());
    }

}
