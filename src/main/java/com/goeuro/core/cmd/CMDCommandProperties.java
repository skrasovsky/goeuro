package com.goeuro.core.cmd;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class CMDCommandProperties {

    @NotEmpty
    @Value("${cmd.command.command}")
    private String command;

    @NotEmpty
    @Value("${cmd.command.command.description}")
    private String commandDescription;

    @NotEmpty
    @Value("${cmd.command.list}")
    private String commandList;

    @NotEmpty
    @Value("${cmd.command.list.description}")
    private String commandListDescription;

    @NotEmpty
    @Value("${cmd.command.quit}")
    private String quit;

    @NotEmpty
    @Value("${cmd.command.quit.description}")
    private String quitDescription;

    @NotEmpty
    @Value("${cmd.command.help}")
    private String help;

    @NotEmpty
    @Value("${cmd.command.help.description}")
    private String helpDescription;

    @NotEmpty
    @Value("${cmd.command.help.usage}")
    private String helpUsage;

    @NotEmpty
    @Value("${cmd.command.help.example}")
    private String helpExample;

    @NotEmpty
    @Value("${cmd.arg.http.method}")
    private String httpMethod;

    @NotEmpty
    @Value("${cmd.arg.http.method.description}")
    private String httpMethodDescription;

    @NotEmpty
    @Value("${cmd.arg.header}")
    private String header;

    @NotEmpty
    @Value("${cmd.arg.header.description}")
    private String headerDescription;

    @NotEmpty
    @Value("${cmd.arg.path.param}")
    private String pathParam;

    @NotEmpty
    @Value("${cmd.arg.path.param.description}")
    private String pathParamDescription;

    @NotEmpty
    @Value("${cmd.arg.query.string}")
    private String queryString;

    @NotEmpty
    @Value("${cmd.arg.query.string.description}")
    private String queryStringDescription;

    @NotEmpty
    @Value("${cmd.arg.body}")
    private String body;

    @NotEmpty
    @Value("${cmd.arg.body.description}")
    private String bodyDescription;

    @NotEmpty
    @Value("${cmd.api.command.help.title}")
    private String apiCommandHelpTitle;

    @NotEmpty
    @Value("${cmd.api.command.help.template}")
    private String apiCommandHelpTemplate;

    public String command() {
        return command;
    }

    public String commandDescription() {
        return commandDescription;
    }

    public String commandList() {
        return commandList;
    }

    public String commandListDescription() {
        return commandListDescription;
    }

    public String quit() {
        return quit;
    }

    public String quitDescription() {
        return quitDescription;
    }

    public String help() {
        return help;
    }

    public String helpDescription() {
        return helpDescription;
    }

    public String helpUsage() {
        return helpUsage;
    }

    public String helpExample() {
        return helpExample;
    }

    public String httpMethod() {
        return httpMethod;
    }

    public String httpMethodDescription() {
        return httpMethodDescription;
    }

    public String header() {
        return header;
    }

    public String headerDescription() {
        return headerDescription;
    }

    public String pathParam() {
        return pathParam;
    }

    public String pathParamDescription() {
        return pathParamDescription;
    }

    public String queryString() {
        return queryString;
    }

    public String queryStringDescription() {
        return queryStringDescription;
    }

    public String body() {
        return body;
    }

    public String bodyDescription() {
        return bodyDescription;
    }

    public String apiCommandHelpTitle() {
        return apiCommandHelpTitle;
    }

    public String apiCommandHelpTemplate() {
        return apiCommandHelpTemplate;
    }

}
