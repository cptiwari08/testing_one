# This extension provides cucumber runtime test runner.

Runtime means it runs tests on already running application and use all setting, beans and so on of the application.

## Ways to run tests 
1. Run feature files from file system by endpoint call:
    *   URL         : http://localhost:9001/cucumberserver/runbackendcucumbertests
    *   Http method : GET
    *   Parameters  :
        *   tags - feature tags to run(for example @Backend). It can be multiple tags split by "--tags" delimiter
        *   featuresPath    - path to folder with feature files
        *   reportPath      - path to folder where tests report will be placed
        *   reportFormat    - report format (can be json or html)

2. Run feature from request by endpoint call:
    *   URL         : http://localhost:9001/cucumberserver/runadhocfeature
    *   Http method : POST
    *   Content-Type: application/json
    *   Body        : feature text
3. Run feature files from file system by ant task:
    *   Task name   : runcucumbertests
    *   Parameters  :
        *   pathToFeatures - relative path to folder with features(this path should be start from root hybris folder)
        *   tags           - feature tags to run(for example @Backend). It can be multiple tags split by "--tags" delimiter

**To run features** we need to specify package with stepdefs classes.
It can be done by overriding of `aurora.cucumberserver.path.to.stepdefs` property.

## More details about parameters:

| URL Parameter | Description | Example |
| ------ | ------ | ------ |
|reportFormat | The format in which report will be generated. There are two formats available, HTML is better for humans, JSON is better if you want to parse in using your CI tools| html, json |
|reportPath| Path where report will be generated | /Users/test/Documents/AURORA/customextensions/teststepdefs/backendfeatures/cucumber/report |
|featuresPath| This will specify what feature files will be executed, to run all tests you should add path to your 'backendfeatures' folder. |/Users/test/Documents/AURORA/customextensions/teststepdefs/backendfeatures|
|tags| Cucumber annotation that helps to decide what scenarios will be executed. Detailed information is [here](https://kb.epam.com/display/~Vadym_Baranenko/01.02.10+Setup+Instructions) | @Parallel--tags~@Wip--tags~@Backend |

## Existed properties:

| Property | Description |
| ------ | ------ |
|aurora.cucumberserver.concurrent.number.of.threads| Max number of thread to run your tests. Default value is 4|
|aurora.cucumberserver.concurrent.should.run.concurrently| Enables running tests in paralel. Default is true|
|aurora.cucumberserver.concurrent.should.use.real.runtimes.pool| Enables usage of pull for cucumber.runtime.Runtime. Default is true |
|aurora.cucumberserver.path.to.stepdefs| Determines classpath location of cucumber step definitions. Default is com.epam.aurora.cucumberstepdefs |

### Please look to [cucumbertestexamples](https://git.epam.com/vadym_baranenko/aurora/tree/develop/extensions/cucumbertestexamples) extension for additional examples and happy testing!!!