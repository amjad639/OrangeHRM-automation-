package pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PIMPage {


    WebDriver driver;
    WebDriverWait wait;

    public PIMPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators
    By pimMenu = By.xpath("//span[text()='PIM']");
    By employeeList = By.xpath("//a[text()='Employee List']");

    By employeeNameInput =
            By.xpath("//label[text()='Employee Name']/following::input[1]");

    By searchButton =
            By.xpath("//button[normalize-space()='Search']");

    By employeeTable =
            By.cssSelector(".oxd-table-body");

    By noRecordsFound =
            By.xpath("//div[contains(@class,'oxd-toast')]//*[normalize-space()='No Records Found']");
    By addEmployee = By.xpath("//button[normalize-space()='Add']");
    By firstNameInput = By.name("firstName");
    By lastNameInput = By.name("lastName");
    By saveButton = By.xpath("//button[normalize-space()='Save']");
    // PIMPage.java
    By firstNameRequiredError = By.xpath(
            "//input[@name='firstName']/ancestor::div[contains(@class,'oxd-input-group')][1]" +
                    "//span[contains(@class,'oxd-input-field-error-message')]"
    );
    By formLoader = By.cssSelector(".oxd-form-loader");
    By personalDetailsHeader = By.xpath("//h6[text()='Personal Details']");




    // Methods

    public void clickPIM() {
        Allure.step("Click PIM menu");

        wait.until(
                ExpectedConditions.elementToBeClickable(pimMenu)
        ).click();
    }

    public void clickEmployeeList() {
        Allure.step("Click Employee List");

        wait.until(
                ExpectedConditions.elementToBeClickable(employeeList)
        ).click();
    }

    public void enterEmployeeName(String employeeName) {
        Allure.step("Enter employee name: " + employeeName);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(employeeNameInput)
        ).sendKeys(employeeName);
    }

    public void clickSearch() {
        Allure.step("Click Search");

        wait.until(
                ExpectedConditions.elementToBeClickable(searchButton)
        ).click();
    }

    public boolean isEmployeeDisplayed(String employeeName) {

        String[] nameParts = employeeName.trim().split("\\s+", 2);

        String firstName = nameParts[0];
        String lastName = nameParts[1];

        By employeeRow = By.xpath(
                "//div[contains(@class,'oxd-table-row')]"
                        + "[.//div[normalize-space(text())='" + firstName + "']]"
                        + "[.//div[normalize-space(text())='" + lastName + "']]"
        );

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(employeeRow)
        ).isDisplayed();
    }

    public String getNoRecordsMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(noRecordsFound)
        ).getText();
    }
    public boolean isNoRecordsMessageDisplayed() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(noRecordsFound)
            ).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    public void clickAddEmployee() {
        Allure.step("Click Add Employee");

        wait.until(
                ExpectedConditions.elementToBeClickable(addEmployee)
        ).click();
    }

    public boolean isFirstNameDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(firstNameInput)
        ).isDisplayed();
    }

    public boolean isLastNameDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(lastNameInput)
        ).isDisplayed();
    }
    public void enterLastName(String lastName) {
        Allure.step("Enter last name: " + lastName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameInput))
                .sendKeys(lastName);
    }

    public void clickSave() {
        Allure.step("Click Save");

        // Wait for the loading overlay to disappear first
        wait.until(ExpectedConditions.invisibilityOfElementLocated(formLoader));

        wait.until(
                ExpectedConditions.elementToBeClickable(saveButton)
        ).click();
    }

    public boolean isFirstNameRequiredErrorDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(firstNameRequiredError)
        ).isDisplayed();
    }
    public void enterFirstName(String firstName) {
        Allure.step("Enter first name: " + firstName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput))
                .sendKeys(firstName);
    }

    public boolean isPersonalDetailsPageDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(personalDetailsHeader)
        ).isDisplayed();
    }

}