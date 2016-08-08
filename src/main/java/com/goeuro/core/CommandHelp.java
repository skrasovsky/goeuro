package com.goeuro.core;

/**
 * API command help.
 */
public class CommandHelp {

    /**
     * The name of command.
     */
    private String name;

    /**
     * The description of command/
     */
    private String description;

    /**
     * The template of command, e.g. '/position/suggest/${local}/{city}'
     */
    private String template;

    /**
     * The allowed parameters, e.g. locale=en, city=Berlin
     */
    private String[] allowedParams;

    /**
     * The example of command, e.g. '/position/suggest/en/Berlin'
     */
    private String example;

    public CommandHelp(String name, String description, String template, String[] allowedParams, String example) {
        this.name = name;
        this.description = description;
        this.template = template;
        this.allowedParams = allowedParams;
        this.example = example;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String[] getAllowedParams() {
        return allowedParams;
    }

    public void setAllowedParams(String[] allowedParams) {
        this.allowedParams = allowedParams;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

}
