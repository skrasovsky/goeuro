package com.goeuro.core.cmd;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Command line API command option container.
 *
 * Initialize command line commands.
 */
@Component
public class CMDOptionsContainer {

    private Options options;

    @Autowired
    private CMDCommandProperties properties;

    @PostConstruct
    public void init() {

        options = new Options();
        options.addOption(initListCommandOption());
        options.addOption(initCommandOption());
        options.addOption(initHttpMethodOption());
        options.addOption(initHeaderOption());
        options.addOption(initPathParamOption());
        options.addOption(initQueryStringOption());
        options.addOption(initBodyOption());
        options.addOption(initQuitOption());
        options.addOption(initHelpOption());
    }

    /**
     * Parse the arguments according to the specified options.
     *
     * @param args the command line arguments
     * @return the list of atomic option and value tokens
     *
     * @throws ParseException if there are any problems encountered
     * while parsing the command line tokens.
     */
    public CommandLine parse(String args[]) throws ParseException {
        CommandLineParser cmd = new DefaultParser();
        return cmd.parse(options, args);
    }

    /**
     * Print the help for <code>options</code> with the specified
     * command line syntax.
     */
    public void printCMDHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        StringBuilder helpBuilder = new StringBuilder();
        helpBuilder.append(properties.helpUsage());
        helpBuilder.append("\n");
        helpBuilder.append(properties.helpExample());
        helpBuilder.append("\n");
        helpBuilder.append("\n");
        helpFormatter.printHelp(helpBuilder.toString(), options);
    }

    private Option initListCommandOption() {
        return Option.builder("l")
                .longOpt(properties.commandList())
                .desc(properties.commandListDescription())
                .build();
    }

    private Option initCommandOption() {
        return Option.builder("c")
                .longOpt(properties.command())
                .numberOfArgs(1)
                .desc(properties.commandDescription())
                .build();
    }

    private Option initHttpMethodOption() {
        return Option.builder("hm")
                .longOpt(properties.httpMethod())
                .numberOfArgs(1)
                .desc(properties.httpMethodDescription())
                .build();
    }

    private Option initHeaderOption() {
        return Option.builder("h")
                .longOpt(properties.header())
                .hasArgs()
                .valueSeparator(' ')
                .desc(properties.headerDescription())
                .build();
    }

    private Option initPathParamOption() {
        return Option.builder("pp")
                .longOpt(properties.pathParam())
                .hasArgs()
                .valueSeparator(' ')
                .desc(properties.pathParamDescription())
                .build();
    }

    private Option initQueryStringOption() {
        return Option.builder("qs")
                .longOpt(properties.queryString())
                .hasArgs()
                .valueSeparator(' ')
                .desc(properties.queryStringDescription())
                .build();
    }

    private Option initBodyOption() {
        return Option.builder("b")
                .longOpt(properties.body())
                .numberOfArgs(1)
                .desc(properties.bodyDescription())
                .build();
    }

    private Option initQuitOption() {
        return Option.builder("q")
                .longOpt(properties.quit())
                .desc(properties.quitDescription())
                .build();
    }

    private Option initHelpOption() {
        return Option.builder()
                .longOpt(properties.help())
                .desc(properties.helpDescription())
                .build();
    }
}
