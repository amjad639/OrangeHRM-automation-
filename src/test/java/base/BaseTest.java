package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

public class BaseTest {
    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public WebDriver getDriver() {
        return driver.get();
    }

    @BeforeMethod
    public void setUpDriver() {
        String browser = ConfigReader.getBrowser();

        if (browser.equalsIgnoreCase("chrome")) {
            driver.set(new ChromeDriver());
        }
        getDriver().manage().window().maximize();
        getDriver().get(ConfigReader.getBaseUrl() + "auth/login");
    }

    @AfterMethod
    public void CloseDriver() {
        getDriver().quit();
        driver.remove();
    }
}