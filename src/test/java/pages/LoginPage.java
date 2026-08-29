package pages;
import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
public class LoginPage {
    WebDriver driver;
    WebDriverWait wait;
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        wait=new WebDriverWait(driver,Duration.ofSeconds(5));
    }

    //locators
    By username =By.name("username");
    By password =By.name("password");
    By LoginButton =By.xpath("//button[@type='submit']");
    By errormessage = By.cssSelector("p.oxd-alert-content-text");
    By requiredMessages = By.cssSelector(".oxd-input-field-error-message");


    //method
    public void EnterUsername(String user)
    {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(username)
        );
        Allure.step("Enter username");
        driver.findElement(username).sendKeys(user);
    }

    public void EnterPassword(String pass){
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(password)
        );
        Allure.step("Enter password");
        driver.findElement(password).sendKeys(pass);
    }
    public void clickLoginButton(){
        wait.until(
                ExpectedConditions.elementToBeClickable(LoginButton)
        );
        Allure.step("Click Login button");
        driver.findElement(LoginButton).click();
    }
    public String GetErrorMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(errormessage)
        ).getText();
    }
    public void ValidLogin(String username,String pass){
        EnterUsername(username);
        EnterPassword(pass);
        clickLoginButton();
    }
    public int getRequiredMessagesCount() {
        return driver.findElements(requiredMessages).size();
    }
}
