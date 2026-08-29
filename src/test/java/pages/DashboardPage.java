package pages;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DashboardPage {

    WebDriver driver;
    WebDriverWait wait;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    By dashboardHeader =
            By.cssSelector("h6.oxd-topbar-header-breadcrumb-module");
    By footerCopyrightText = By.cssSelector("p.orangehrm-copyright a");
    By footerLink = By.cssSelector("p.orangehrm-copyright a[href='http://www.orangehrm.com']");
    By sidebarMenuItems = By.cssSelector(".oxd-main-menu-item");



    public boolean isDashboardDisplayed() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(dashboardHeader)
        ).isDisplayed();
    }

    public String getDashboardHeaderText() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(dashboardHeader)
        ).getText();
    }
    public String getFooterText() {
        Allure.step("Scroll to footer and read text");
        var footer = wait.until(ExpectedConditions.presenceOfElementLocated(footerCopyrightText));
        ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", footer);
        return footer.getText();
    }

    public String clickFooterLinkAndGetNewTabUrl() {
        Allure.step("Click OrangeHRM footer link");

        String originalWindow = driver.getWindowHandle();
        Set<String> originalHandles = driver.getWindowHandles();

        wait.until(ExpectedConditions.elementToBeClickable(footerLink)).click();

        wait.until(d -> d.getWindowHandles().size() > originalHandles.size());

        Set<String> allHandles = driver.getWindowHandles();
        allHandles.removeAll(originalHandles);
        String newWindow = allHandles.iterator().next();

        driver.switchTo().window(newWindow);
        wait.until(ExpectedConditions.urlContains("orangehrm.com"));

        String newTabUrl = driver.getCurrentUrl();

        driver.close();
        driver.switchTo().window(originalWindow);

        return newTabUrl;
    }
    public List<String> getSidebarMenuTexts() {
        Allure.step("Read sidebar menu items");

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(sidebarMenuItems));

        return driver.findElements(sidebarMenuItems)
                .stream()
                .map(el -> el.getText().trim())
                .collect(Collectors.toList());
    }

    public boolean isMenuItemPresent(String menuName) {
        return getSidebarMenuTexts().stream()
                .anyMatch(text -> text.equalsIgnoreCase(menuName));
    }
}