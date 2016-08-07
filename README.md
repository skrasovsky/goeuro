


# GoEuro

It is a console application, which allows to develop any kind of commands and execute them from comand line. Application contains
only one command for the demonstration. If you need more commands you should extend library and develop your own commands.

What type of commands application allows to create? Any type. 

Command - it is a single class with opportunity to send a http request. It can be:
- Command that sends http request, gets response and sends email
- Command that sends http request, processes response and stores it to CSV file
- Command that sends http request and prints response
- Command without sending http request, e.g. for calculating something

### How to extend the library:

For example we need a command which sends http request to the some endpoint and stores response into CSV file.

Create class:
```JAVA
@API(
    name = "getLocationsByCityCSV",
    description = "Fetch locations by city and import into CSV file",
    template = "/position/suggest/${local}/{city}",
    allowedParams = {"locale: e.g. en, de, fr", "city: e.g. Berlin, London, Munich"},
    example = "/position/suggest/en/Berlin"
)
public class GetLocationsByCityCSV extends APICommand {

    @Override
    public void execute(APICommandContext context) {
        Location[] locations = run(context, Location[].class, 201);
        // Add code to store data into csv file.
    }

    @Override
    protected String getUrl() {
        return "http://api.goeuro.com/api/v2/position/suggest/en/Berlin"
    }

    @Override
    protected void validateContext() {
        // Add validation of api command context here. For example you need some requered parameters.
    }
}
```

After building and running application the command `getLocationsByCityCSV` will be available and one can run it from command line. Also it's possible to print help for this command.


### How to use command line:

Show the application help:
````
-help
```
Show the command list:
```
-command-list
```
Quit from application:
```
-quit
```
Execute command:
```
-command <name>
```
The available `-command` arguments:
- `-http-method <arg>` - HTTP request method. Allow values: `GET`. To use other, need to extend the library. By default `GET`. Example: 
```
-hm GET
```
- `-header <arg>` - HTTP request headers. Support multiple arguments. Example: 
```
-h name1=value name2=value2
```
- `-path-param <arg>` - HTTP request path parameters. Support multiple arguments. Example: 
```
-pp name1=value1 name2=value2
```
- `-query-string <arg>` - HTTP request query string parameters. Support multiple arguments. Example: 
```
-qs name1=valuename2=value2
```

How to execute command:
```
-command getLocationsByCityCSV -path-param city=berlin
-c getLocationsByCityCSV -pp city=roma locale=it
```
# Build & Run
### How to build application
```
mvn clean install
```
### How to run application
```
mvn spring-boot:run
```
or
```
java -jar goeuro.jar
```
