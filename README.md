# OrangeHRM Test Automation Framework (with BDD)

A Selenium WebDriver + TestNG automation framework built on the **Page Object Model (POM)**, testing the [OrangeHRM Demo](https://opensource-demo.orangehrmlive.com/) application end-to-end — now extended with a **Cucumber BDD layer** on top of the same Page Objects.

> This project was built as a graduation project for the **Route** training program.

## ✨ Features

- **Page Object Model** — clean separation between locators/page logic and test logic
- **TestNG** — test execution, data providers, and parallel-ready structure
- **Cucumber (BDD)** — feature files written in plain Gherkin (Given/When/Then), reusing the existing Page Object methods underneath
- **Allure Reports** — rich, step-by-step HTML reports for both TestNG and Cucumber runs
- **Log4j2** — structured logging for test execution and retries
- **Retry Analyzer** — automatically retries flaky/failed tests (`RetryAnalyzer`, `RetryTransformer`)
- **Data-Driven Testing** — test data externalized in `testData.json`
- **Config-driven runs** — browser and base URL configurable via `config.properties`

## 🗂️ Project Structure

```
src/test/java/
 ├── base/
 │    └── BaseTest.java              # Driver lifecycle for plain TestNG tests
 ├── context/
 │    └── TestContext.java           # Shared WebDriver instance for Cucumber steps
 ├── hooks/
 │    └── Hooks.java                 # @Before/@After driver setup & teardown (Cucumber)
 ├── pages/
 │    ├── LoginPage.java
 │    ├── AdminPage.java
 │    ├── PIMPage.java
 │    └── DashboardPage.java
 ├── runners/
 │    └── TestRunner.java            # Cucumber-TestNG runner
 ├── stepdefinitions/
 │    ├── LoginSteps.java
 │    ├── AdminSteps.java
 │    ├── PIMSteps.java
 │    └── DashboardSteps.java
 ├── tests/
 │    ├── LoginTests.java            # Original TestNG tests (kept alongside BDD)
 │    ├── AdminTests.java
 │    ├── PIMTests.java
 │    └── DashboardTests.java
 └── utils/
      ├── ConfigReader.java          # Reads config.properties
      ├── DataDriver.java            # Reads testData.json
      ├── RetryAnalyzer.java         # Retry logic for failed tests
      └── RetryTransformer.java      # Applies RetryAnalyzer globally

src/test/resources/
 ├── config.properties
 ├── testData.json
 └── features/
      ├── login.feature
      ├── admin.feature
      ├── pim.feature
      └── dashboard.feature
```

## 🧪 Test Coverage

| Module     | Scenarios covered |
|------------|--------------------|
| **Login**    | Valid/invalid login, empty credentials, forgot password flow, logout, unauthenticated access redirect, social media links |
| **Admin**    | Add User (with role/employee/username/password), field visibility, duplicate username validation, search by username |
| **PIM**      | Employee search (existing/non-existing), add employee (with/without required fields, with/without photo), edit, delete, filter by employment status |
| **Dashboard**| Core widgets visibility, sidebar navigation, footer links, Quick Launch navigation |

Each module above is covered both by the original TestNG test classes and by an equivalent Cucumber feature file, so scenarios can be read and run in plain English by non-technical stakeholders as well.

## ⚙️ Prerequisites

- Java JDK 11+
- Maven
- Chrome browser (ChromeDriver managed via Selenium Manager)

## ▶️ Running the Tests

**All TestNG tests:**
```bash
mvn clean test
```

**A specific TestNG class:**
```bash
mvn test -Dtest=LoginTests
```

**All BDD (Cucumber) scenarios:**
```bash
mvn test -Dtest=TestRunner
```

**A specific feature file:**
```bash
mvn test -Dcucumber.features="src/test/resources/features/login.feature"
```

**By tag** (add a tag like `@login` above a Feature/Scenario first):
```bash
mvn test -Dcucumber.filter.tags="@login"
```

## 📊 Generating Allure Reports

```bash
allure serve target/allure-results
```

## 🔧 Configuration

Edit `src/test/resources/config.properties`:

```properties
base.url=https://opensource-demo.orangehrmlive.com/web/index.php/
browser=chrome
explicit.wait=10
```

Edit `src/test/resources/testData.json` to change test data (credentials, employee names, roles, etc.).

## 🧩 Challenges & Solutions

- **Cucumber/TestNG/Allure integration** — hooked up the `AbstractTestNGCucumberTests` runner with the Allure Cucumber plugin; resolved dependency-version mismatches and classpath issues that initially caused runtime plugin-loading failures.
- **Dynamic and inconsistent locators** — several OrangeHRM elements shifted structure between pages or loaded asynchronously, making static selectors unreliable. Solved with relative XPath strategies and explicit `WebDriverWait`/`ExpectedConditions` instead of assuming elements were immediately present.
- **Shared WebDriver across Cucumber steps** — introduced a `TestContext` + Cucumber PicoContainer dependency injection so all step definition classes share the same driver instance per scenario.

## 🙏 Acknowledgements

Built as a graduation project for the **Route** training program. Special thanks to instructor **Mohamed Mumtaz** for the guidance and support throughout the training.

## 📄 License

This project is for educational/portfolio purposes, built against the public OrangeHRM demo instance.