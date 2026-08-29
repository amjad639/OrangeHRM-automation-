# OrangeHRM Selenium Test Automation Framework

A test automation framework for the [OrangeHRM Open Source Demo](https://opensource-demo.orangehrmlive.com/) built with **Selenium WebDriver**, **TestNG**, and the **Page Object Model (POM)**. The framework supports data-driven testing, parallel execution, retry-on-failure, structured logging, and Allure reporting.

## Tech Stack

- **Java 17**
- **Selenium WebDriver 4.44.0**
- **TestNG 7.10.2**
- **Jackson Databind** — JSON test data parsing
- **Allure 2.29.1** — test reporting
- **Log4j2** — structured logging
- **Maven** — build and dependency management

## Project Structure

```
src/test/java
├── base/
│   ├── BaseTest.java        # Test lifecycle (setup/teardown), ThreadLocal WebDriver
│   └── DriverManager.java
├── pages/
│   ├── LoginPage.java
│   ├── PIMPage.java
│   ├── AdminPage.java
│   └── DashboardPage.java
├── tests/
│   ├── LoginTests.java
│   ├── PIMTests.java
│   ├── AdminTests.java
│   ├── DashboardTests.java
│   └── UITest.java
└── utils/
    ├── ConfigReader.java     # Reads config.properties
    ├── RetryAnalyzer.java    # Retries failed tests (max 2 retries)
    ├── RetryTransformer.java # Applies RetryAnalyzer to all @Test methods
    └── TestListener.java     # Logs test start/pass/fail/skip events

src/test/resources
├── config.properties         # Environment config (base URL, browser, wait time)
├── testdata.json             # Test data (credentials, employee data)
└── testing.xml               # TestNG suite configuration
```

## Features

- **Page Object Model (POM)** — locators and page interactions are encapsulated per page
- **Explicit waits** — no `Thread.sleep()`; all waits use `WebDriverWait`
- **Data-driven testing** — test data is stored in `testdata.json` and consumed via TestNG `@DataProvider`
- **Parallel execution** — test classes run in parallel (`parallel="tests"`, thread-count configurable) using a `ThreadLocal<WebDriver>` so each thread gets its own independent browser session
- **Retry mechanism** — failed tests are automatically retried up to 2 times via a custom `IRetryAnalyzer`, reducing false failures from flaky runs
- **Centralized configuration** — environment settings (base URL, browser, explicit wait) are read from `config.properties` instead of being hardcoded
- **Logging** — Log4j2 logs each test step (`INFO`) and failure (`ERROR`) to both console and a log file
- **Allure reporting** — generates an HTML report with test results and step-by-step execution detail

## Test Coverage

| Area | Test Cases |
|---|---|
| Login | Valid login, invalid login, empty credentials validation |
| PIM | Search existing/non-existing employee, open Add Employee page, empty required field validation, full add-employee end-to-end flow |
| Admin | Add User form field verification |
| Dashboard | Sidebar menu items, footer branding link |

## Configuration

Edit `src/test/resources/config.properties` to change environment settings:

```properties
base.url=https://opensource-demo.orangehrmlive.com/web/index.php/
browser=chrome
explicit.wait=10
```

Test data (login credentials, employee names) lives in `src/test/resources/testdata.json`.

## Running the Tests

**Run the full suite via Maven:**

```bash
mvn test
```

This uses the TestNG suite defined in `src/test/resources/testing.xml`, running test classes in parallel and applying the retry analyzer to any failures.

**Run from an IDE:** right-click `testing.xml` and run it as a TestNG Suite, or run individual test classes directly.

## Generating the Allure Report

After running the tests:

```bash
mvn test
allure generate allure-results --clean -o allure-report
```

**View the report locally:**

```bash
allure open allure-report
```

> Note: `allure-results/` (raw test output) is excluded from version control via `.gitignore`. The generated `allure-report/` (static HTML report) is committed to the repository so it can be viewed without regenerating it.

## Logs

Test execution logs (step-level `INFO`, failures at `ERROR`) are written to the `logs/` directory at the project root when running locally.

## Known Limitations

The OrangeHRM Open Source Demo is a **public, shared environment** used by many people simultaneously. As a result:

- Login attempts may occasionally be rate-limited by the server during heavy or parallel test runs, which can cause intermittent failures unrelated to the test code itself.
- Data created or searched for (e.g., employee records) may be affected by other users interacting with the same demo instance at the same time.

The retry mechanism is configured specifically to reduce the impact of this kind of environment-related flakiness.
