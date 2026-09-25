package stepdefinitions;

import context.TestContext;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.DashboardPage;
import pages.LoginPage;

public class LoginSteps {

    private final LoginPage loginPage;
    private final DashboardPage dashboardPage;

    public LoginSteps(TestContext context) {
        loginPage = new LoginPage(context.getDriver());
        dashboardPage = new DashboardPage(context.getDriver());
    }

    @Given("the user is on the login page")
    public void userOnLoginPage() {
        Assert.assertTrue(loginPage.isLoginFormDisplayed());
    }

    @When("the user logs in with username {string} and password {string}")
    public void userLogsIn(String username, String password) {
        loginPage.ValidLogin(username, password);
    }

    @Then("the dashboard should be displayed")
    public void dashboardDisplayed() {
        Assert.assertEquals(dashboardPage.getDashboardHeaderText(), "Dashboard");
    }

    @Then("an error message {string} should be shown")
    public void errorShown(String message) {
        Assert.assertTrue(loginPage.GetErrorMessage().contains(message));
    }

    @When("the user clicks login without entering credentials")
    public void clickLoginEmpty() {
        loginPage.clickLoginButton();
    }

    @Then("required field validation messages should appear")
    public void requiredMessagesShown() {
        Assert.assertEquals(loginPage.getRequiredMessagesCount(), 2);
    }

    @Given("the user is logged in with username {string} and password {string}")
    public void userLoggedIn(String username, String password) {
        loginPage.ValidLogin(username, password);
    }

    @When("the user logs out")
    public void userLogsOut() {
        loginPage.logout();
    }

    @Then("the login form should be displayed again")
    public void loginFormDisplayedAgain() {
        Assert.assertTrue(loginPage.isLoginFormDisplayed());
    }
}