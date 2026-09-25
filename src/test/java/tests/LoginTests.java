package tests;

import base.BaseTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;

import java.io.File;
import java.io.IOException;

public class LoginTests extends BaseTest {

    @DataProvider(name = "validLoginData")
    public Object[][] validLoginData() throws IOException {
        JsonNode data = new ObjectMapper().readTree(
                new File("src/test/resources/testData.json"));
        return new Object[][]{
                {
                        data.get("validCredentialsLogin").get("username").asText(),
                        data.get("validCredentialsLogin").get("password").asText()
                }
        };
    }

    @DataProvider(name = "invalidLoginData")
    public Object[][] invalidLoginData() throws IOException {
        JsonNode data = new ObjectMapper().readTree(
                new File("src/test/resources/testData.json"));

        return new Object[][]{
                {
                        data.get("invalidCredentialsLogin").get("username").asText(),
                        data.get("invalidCredentialsLogin").get("password").asText()
                }
        };
    }

    @Test(dataProvider = "validLoginData")
    public void VerifyLoginWithValidCredentials(String username, String password) {
        LoginPage loginPage = new LoginPage(getDriver());
        DashboardPage dashboardPage = new DashboardPage(getDriver());
        loginPage.EnterUsername(username);
        loginPage.EnterPassword(password);
        loginPage.clickLoginButton();
        Assert.assertEquals(
                getDriver().getCurrentUrl(),
                "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index"
        );
        Assert.assertEquals(dashboardPage.getDashboardHeaderText(), "Dashboard");
    }

    @Test(dataProvider = "invalidLoginData")
    public void VerifyLoginWithInvalidCredentials(String username, String password) {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.EnterUsername(username);
        loginPage.EnterPassword(password);
        loginPage.clickLoginButton();
        Assert.assertTrue(loginPage.GetErrorMessage().contains("Invalid credentials"));
    }

    @Test
    public void VerifyLoginWithEmptyCredentials() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.clickLoginButton();
        Assert.assertEquals(loginPage.getRequiredMessagesCount(), 2);
    }
    @Test
    public void VerifyForgotPasswordFlow() {
        LoginPage loginPage = new LoginPage(getDriver());

        loginPage.clickForgotPasswordLink();
        loginPage.enterResetUsername("Admin");
        loginPage.clickResetPasswordButton();

        Assert.assertTrue(
                loginPage.isSuccessMessageDisplayed(),
                "Reset password success message should be displayed"
        );
    }
    @Test(dataProvider = "validLoginData")
    public void VerifyLogout(String username, String password) {
        LoginPage loginPage = new LoginPage(getDriver());

        loginPage.ValidLogin(username, password);
        loginPage.logout();

        Assert.assertTrue(
                loginPage.isLoginFormDisplayed(),
                "Login form should be displayed after logout"
        );
    }

    @Test
    public void VerifyDashboardAccessWithoutLogin() {
        getDriver().get("https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");

        Assert.assertTrue(
                getDriver().getCurrentUrl().contains("/auth/login"),
                "Unauthenticated user should be redirected to login page"
        );
    }

    @Test
    public void VerifySocialMediaLinks() {
        LoginPage loginPage = new LoginPage(getDriver());

        Assert.assertEquals(
                loginPage.getLinkedinHref(),
                "https://www.linkedin.com/company/orangehrm/mycompany/",
                "LinkedIn link mismatch"
        );
        Assert.assertEquals(
                loginPage.getFacebookHref(),
                "https://www.facebook.com/OrangeHRM/",
                "Facebook link mismatch"
        );
        Assert.assertEquals(
                loginPage.getTwitterHref(),
                "https://twitter.com/orangehrm?lang=en",
                "Twitter link mismatch"
        );
        Assert.assertEquals(
                loginPage.getYoutubeHref(),
                "https://www.youtube.com/c/OrangeHRMInc",
                "YouTube link mismatch"
        );
    }
}