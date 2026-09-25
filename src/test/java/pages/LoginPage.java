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
    By userDropdown = By.className("oxd-userdropdown-tab");
    By logoutLink = By.xpath("//a[text()='Logout']");
    By loginFormLocator = By.cssSelector(".orangehrm-login-form");
    By invalidCredentialsMessage = By.xpath("//p[text()='Invalid credentials']");
    By forgotPasswordHeader = By.cssSelector(".orangehrm-login-forgot-header");
    By usernameInput = By.name("username");
    By resetPasswordButton = By.xpath("//button[normalize-space()='Reset Password']");
    By cancelButton = By.xpath("//button[normalize-space()='Cancel']");
    By successMessage = By.xpath("//h6[text()='Reset Password link sent successfully']");
    By linkedinLink = By.xpath("//a[@href='https://www.linkedin.com/company/orangehrm/mycompany/']");
    By facebookLink = By.xpath("//a[@href='https://www.facebook.com/OrangeHRM/']");
    By twitterLink = By.xpath("//a[@href='https://twitter.com/orangehrm?lang=en']");
    By youtubeLink = By.xpath("//a[@href='https://www.youtube.com/c/OrangeHRMInc']");


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
    public void clickForgotPasswordLink(){
        wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordHeader));
        Allure.step("Click Forgot Password link");
        driver.findElement(forgotPasswordHeader).click();
    }

    public void enterResetUsername(String user){
        wait.until(ExpectedConditions.visibilityOfElementLocated(usernameInput));
        Allure.step("Enter username in reset password field");
        driver.findElement(usernameInput).sendKeys(user);
    }

    public void clickResetPasswordButton(){
        wait.until(ExpectedConditions.elementToBeClickable(resetPasswordButton));
        Allure.step("Click Reset Password button");
        driver.findElement(resetPasswordButton).click();
    }

    public boolean isSuccessMessageDisplayed(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
    }
    public void logout(){
        wait.until(ExpectedConditions.elementToBeClickable(userDropdown));
        Allure.step("Open user dropdown");
        driver.findElement(userDropdown).click();
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
        Allure.step("Click Logout");
        driver.findElement(logoutLink).click();
    }

    public boolean isLoginFormDisplayed(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(loginFormLocator)).isDisplayed();
    }

    public String getLinkedinHref(){
        wait.until(ExpectedConditions.presenceOfElementLocated(linkedinLink));
        return driver.findElement(linkedinLink).getAttribute("href");
    }
    public String getFacebookHref(){
        wait.until(ExpectedConditions.presenceOfElementLocated(facebookLink));
        return driver.findElement(facebookLink).getAttribute("href");
    }
    public String getTwitterHref(){
        wait.until(ExpectedConditions.presenceOfElementLocated(twitterLink));
        return driver.findElement(twitterLink).getAttribute("href");
    }
    public String getYoutubeHref(){
        wait.until(ExpectedConditions.presenceOfElementLocated(youtubeLink));
        return driver.findElement(youtubeLink).getAttribute("href");
    }
}
