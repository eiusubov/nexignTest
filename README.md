# nexignTest
Environment which was used for test creation and execution:
- Windows 10
- JDK 18.0.2.1
- apache-maven-3.8.6
- allure-2.20.1

Tests are configured for parallel execution. Use 'mvn clean test' and after test execution navigate to project 'target' folder and use 'allure serve' to get pretty report.
Some tests are parameterized. This is needed to check boundaries within one test.
