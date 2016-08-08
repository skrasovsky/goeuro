package com.goeuro.core;

import com.goeuro.config.ApplicationTestConfig;
import com.goeuro.core.api.Command;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

@SpringApplicationConfiguration(classes = ApplicationTestConfig.class)
public class CommandContainerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private CommandContainer commandContainer;

    @Test
    public void should_init_api_command() {

        String commandName1 = "testCommand1";

        commandContainer.init();
        assertNotNull(commandContainer.getByName(commandName1));

        Command command1 = commandContainer.getByName(commandName1);
        assertNotNull(command1);

        CommandHelp help1 = commandContainer.getHelpByName(commandName1);
        assertNotNull(help1);
        assertFalse(StringUtils.isEmpty(help1.getName()));
        assertFalse(StringUtils.isEmpty(help1.getDescription()));
    }

}
