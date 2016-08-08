package com.goeuro.core;

import com.goeuro.core.api.API;
import com.goeuro.core.api.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Class responsible for initialization and storing all API commands and help for them into the
 * <code>commands</code> and <code>help</code> respectively.
 */
@Component
public class CommandContainer {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommandContainer.class);

    private Class<API> annotation = API.class;

    @Autowired
    private ApplicationContext context;

    private Map<String, Command> commands;

    private Map<String, CommandHelp> help;

    /**
     * Fetches all instances which classes annotate by {@link com.goeuro.core.api.API} and stored
     * into the <code>commands<code/>.
     *
     * Creates help for all API command and stored into the <code>help</code>
     */
    @PostConstruct
    public void init() {

        String[] classes = context.getBeanNamesForAnnotation(annotation);
        commands = new HashMap<>(classes.length, 1f);
        help = new HashMap<>(classes.length, 1f);

        for (int i = 0; i < classes.length; i++) {
            Command command = (Command) context.getBean(classes[i]);
            API apiAnnotation = command.getClass().getAnnotation(annotation);
            commands.put(apiAnnotation.name(), command);

            CommandHelp commandHelp = new CommandHelp(
                    apiAnnotation.name(),
                    apiAnnotation.description(),
                    apiAnnotation.template(),
                    apiAnnotation.allowedParams(),
                    apiAnnotation.example());

            help.put(apiAnnotation.name(), commandHelp);

            LOGGER.debug("Registered command: {}", apiAnnotation.name());
        }
    }

    /**
     * Return a command {@link com.goeuro.core.api.Command} by name
     *
     * @param name of API command
     * @return instance of class which implements interface {@link com.goeuro.core.api.Command}
     */
    public Command getByName(String name) {
        return commands.get(name);
    }

    /**
     * Help for all registered API command.
     *
     * @return collection of API command help objects {@link com.goeuro.core.CommandHelp}
     */
    public Collection<CommandHelp> getHelp() {
        return help.values();
    }

    /**
     * Help for particular API command.
     *
     * @param name the name of API command
     * @return the help of API command
     */
    public CommandHelp getHelpByName(String name) {
        return help.get(name);
    }
}
