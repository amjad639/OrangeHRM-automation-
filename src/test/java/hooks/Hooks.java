package hooks;

import context.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.ConfigReader;

public class Hooks {

    private final TestContext context;

    public Hooks(TestContext context) {
        this.context = context;
    }

    @Before
    public void setUp() {
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(ConfigReader.getBaseUrl() + "auth/login");
        context.setDriver(driver);
    }

    @After
    public void tearDown() {
        if (context.getDriver() != null) {
            context.getDriver().quit();
        }
    }
}