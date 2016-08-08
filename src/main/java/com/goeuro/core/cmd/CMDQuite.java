package com.goeuro.core.cmd;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;

import static com.goeuro.service.logger.CustomConsoleLogger.info;

/**
 * Quit command line command. Exit from application.
 */
@Component
public class CMDQuite {

    @Value("#{'${cmd.command.quit.byes}'.split('#')}")
    private List<String> byes;

    public void quit() {
        Random random = new Random();
        int byeNumber = random.nextInt(byes.size());
        info(byes.get(byeNumber));
        System.exit(0);
    }

}
