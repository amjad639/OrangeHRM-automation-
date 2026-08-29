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
}