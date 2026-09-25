package stepdefinitions;

import context.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.AdminPage;

import java.util.Map;

public class AdminSteps {

    private final AdminPage adminPage;
    private String lastGeneratedUsername;

    public AdminSteps(TestContext context) {
        adminPage = new AdminPage(context.getDriver());
    }

    @When("the user navigates to Admin and clicks Add")
    public void navigateToAdminAndClickAdd() {
        adminPage.clickAdmin();
        adminPage.clickAddUser();
    }

    @Then("the User Role, Employee Name, Username and Password fields should be displayed")
    public void verifyAddUserFields() {
        Assert.assertTrue(adminPage.isUserRoleFieldDisplayed(), "User Role field should be displayed");
        Assert.assertTrue(adminPage.isEmployeeNameFieldDisplayed(), "Employee Name field should be displayed");
        Assert.assertTrue(adminPage.isUsernameFieldDisplayed(), "Username field should be displayed");
        Assert.assertTrue(adminPage.isPasswordFieldDisplayed(), "Password field should be displayed");
    }

    @And("the user fills the new user form:")
    public void fillNewUserForm(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        lastGeneratedUsername = "esstestuser_" + System.currentTimeMillis();

        adminPage.clickUserRoleDropdown();
        adminPage.selectDropdownOption(data.get("userRole"));

        adminPage.enterEmployeeNameForUser(data.get("employeeName"));

        adminPage.clickStatusDropdown();
        adminPage.selectDropdownOption(data.get("status"));

        adminPage.enterUsername(lastGeneratedUsername);
        adminPage.enterPassword(data.get("password"));
        adminPage.enterConfirmPassword(data.get("password"));
        adminPage.clickSaveUser();
    }

    @Then("a success toast should be displayed")
    public void successToastDisplayed() {
        Assert.assertTrue(adminPage.isSuccessToastDisplayed(), "Expected success toast after adding new user");
    }

    @Then("the new user should appear when searched by username")
    public void newUserAppearsInSearch() {
        adminPage.searchUserByUsername(lastGeneratedUsername);
        Assert.assertTrue(
                adminPage.isUserDisplayedInList(lastGeneratedUsername),
                "Newly added user '" + lastGeneratedUsername + "' should appear in the user list"
        );
    }

    @When("the user searches for username {string} in Admin page")
    public void searchByUsername(String username) {
        adminPage.clickAdmin();
        adminPage.searchUserByUsername(username);
    }

    @Then("the search result should show username {string}")
    public void searchResultShowsUsername(String expectedUsername) {
        Assert.assertEquals(
                adminPage.getFirstRowUsername(),
                expectedUsername,
                "Search result should return the correct username"
        );
    }

    @And("the user fills the new user form with duplicate username:")
    public void fillFormWithDuplicateUsername(DataTable dataTable) {
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        adminPage.clickUserRoleDropdown();
        adminPage.selectDropdownOption(data.get("userRole"));

        adminPage.enterEmployeeNameForUser(data.get("employeeName"));

        adminPage.enterUsername(data.get("username"));
        adminPage.enterPassword(data.get("password"));
        adminPage.enterConfirmPassword(data.get("password"));
        adminPage.clickSaveUser();
    }

    @Then("an {string} error should be shown on Admin page")
    public void alreadyExistsErrorShown(String expectedText) {
        Assert.assertTrue(
                adminPage.isUsernameAlreadyExistsErrorDisplayed(),
                "Expected '" + expectedText + "' validation error for duplicate username"
        );
    }
}