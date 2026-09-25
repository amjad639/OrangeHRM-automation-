package pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AdminPage {

    WebDriver driver;
    WebDriverWait wait;

    public AdminPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    By adminMenu = By.xpath("//span[text()='Admin']");
    By addButton = By.xpath("//button[normalize-space()='Add']");

    By userRoleLabel = By.xpath("//label[text()='User Role']");
    By employeeNameLabel = By.xpath("//label[text()='Employee Name']");
    By usernameLabel = By.xpath("//label[text()='Username']");
    By passwordLabel = By.xpath("//label[text()='Password']");
    By userRoleDropdown = By.xpath("//label[text()='User Role']/following::div[contains(@class,'oxd-select-text')][1]");
    By statusDropdown = By.xpath("//label[text()='Status']/following::div[contains(@class,'oxd-select-text')][1]");
    By dropdownOption = By.xpath("//div[@role='listbox']//span[text()='%s']");
    By employeeNameInput = By.xpath("//label[text()='Employee Name']/following::input[1]");
    By employeeNameAutocompleteOption = By.className("oxd-autocomplete-option");
    By usernameInput = By.xpath("//label[text()='Username']/following::input[1]");
    By passwordInput = By.xpath("//label[text()='Password']/following::input[1]");
    By confirmPasswordInput = By.xpath("//label[text()='Confirm Password']/following::input[1]");
    By saveButton = By.xpath("//button[normalize-space()='Save']");
    By searchUsernameInput = By.xpath("//label[text()='Username']/following::input[1]");
    By searchButton = By.xpath("//button[normalize-space()='Search']");
    By userTableRows = By.cssSelector(".oxd-table-card");
    By firstRowUsername = By.xpath("(//div[contains(@class,'oxd-table-card')])[1]//div[contains(@class,'oxd-table-cell')][2]//div");
    By firstRowEditButton = By.xpath("(//button[.//i[contains(@class,'bi-pencil-fill')]])[1]");
    By usernameAlreadyExistsError = By.xpath("//span[text()='Already exists']");
    By successToast = By.xpath("//p[text()='Successfully Saved']");
    By statusColumn = By.xpath("(//div[contains(@class,'oxd-table-card')])[1]//div[@class='header' and text()='Status']/following-sibling::div[@class='data']");
    By filterToggleButton = By.xpath("//div[contains(@class,'oxd-table-filter-header-options')]//button");



    public void clickAdmin() {
        Allure.step("Click Admin menu");
        wait.until(ExpectedConditions.elementToBeClickable(adminMenu)).click();
    }

    public void clickAddUser() {
        Allure.step("Click Add button on User Management page");
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
    }

    public boolean isUserRoleFieldDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(userRoleLabel)).isDisplayed();
    }

    public boolean isEmployeeNameFieldDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameLabel)).isDisplayed();
    }

    public boolean isUsernameFieldDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameLabel)).isDisplayed();
    }

    public boolean isPasswordFieldDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(passwordLabel)).isDisplayed();
    }
    public void clickUserRoleDropdown() {
        Allure.step("Click User Role dropdown");
        wait.until(ExpectedConditions.elementToBeClickable(userRoleDropdown)).click();
    }

    public void selectDropdownOption(String optionText) {
        Allure.step("Select option: " + optionText);
        By option = By.xpath(String.format("//div[@role='listbox']//span[text()='%s']", optionText));
        wait.until(ExpectedConditions.elementToBeClickable(option)).click();
    }

    public void enterEmployeeNameForUser(String employeeName) {
        Allure.step("Enter employee name: " + employeeName);
        wait.until(ExpectedConditions.visibilityOfElementLocated(employeeNameInput)).sendKeys(employeeName);

        By matchingOption = By.xpath(
                "//div[@role='listbox']//span[contains(normalize-space(text()), '" + employeeName + "')]"
        );

        wait.until(ExpectedConditions.elementToBeClickable(matchingOption)).click();
    }

    public void enterUsername(String username) {
        Allure.step("Enter username: " + username);
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput)).sendKeys(username);
    }

    public void enterPassword(String password) {
        Allure.step("Enter password");
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordInput)).sendKeys(password);
    }

    public void enterConfirmPassword(String password) {
        Allure.step("Enter confirm password");
        wait.until(ExpectedConditions.visibilityOfElementLocated(confirmPasswordInput)).sendKeys(password);
    }

    public void clickSaveUser() {
        Allure.step("Click Save");
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public boolean isUserDisplayedInList(String username) {
        By userRow = By.xpath(
                "//div[contains(@class,'oxd-table-cell')]//div[normalize-space(text())='" + username + "']"
        );
        return wait.until(ExpectedConditions.visibilityOfElementLocated(userRow)).isDisplayed();
    }

    public void searchUserByUsername(String username) {
        Allure.step("Search user by username: " + username);
        openSearchFilterIfCollapsed();
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchUsernameInput)).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchUsernameInput)).sendKeys(username);
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
    }

    public void clickStatusDropdown() {
        Allure.step("Click Status dropdown");
        wait.until(ExpectedConditions.elementToBeClickable(statusDropdown)).click();
    }

    public boolean isSuccessToastDisplayed() {
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try {
            return shortWait.until(ExpectedConditions.visibilityOfElementLocated(successToast)).isDisplayed();
        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public String getFirstRowUsername() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(firstRowUsername)).getText();
    }

    public boolean isUsernameAlreadyExistsErrorDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(usernameAlreadyExistsError)).isDisplayed();
    }
    public void openSearchFilterIfCollapsed() {
        By filterArea = By.cssSelector(".oxd-table-filter-area");
        try {
            boolean visible = wait.until(ExpectedConditions.presenceOfElementLocated(filterArea)).isDisplayed();
            if (!visible) {
                wait.until(ExpectedConditions.elementToBeClickable(filterToggleButton)).click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(filterArea));
            }
        } catch (org.openqa.selenium.TimeoutException e) {
            // filter area never appeared — page likely still transitioning
            wait.until(ExpectedConditions.visibilityOfElementLocated(filterArea));
        }
    }

}