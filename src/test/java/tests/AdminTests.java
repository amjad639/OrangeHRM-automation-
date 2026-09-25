package tests;

import base.BaseTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.AdminPage;
import pages.LoginPage;

import java.io.File;
import java.io.IOException;

public class AdminTests extends BaseTest {

    LoginPage loginPage;
    AdminPage adminPage;

    @BeforeMethod
    public void setUpTest() throws IOException {
        loginPage = new LoginPage(getDriver());
        adminPage = new AdminPage(getDriver());
        JsonNode data = new ObjectMapper().readTree(new File("src/test/resources/testData.json"));

        loginPage.ValidLogin(
                data.get("validCredentialsLogin").get("username").asText(),
                data.get("validCredentialsLogin").get("password").asText()
        );
    }

    //DataProviders

    @DataProvider(name = "newUserData")
    public Object[][] newUserData() throws IOException {
        JsonNode data = new ObjectMapper().readTree(new File("src/test/resources/testData.json"));

        String uniqueUsername = data.get("newUser").get("username").asText() + "_" + System.currentTimeMillis();

        return new Object[][]{
                {
                        data.get("newUser").get("userRole").asText(),
                        data.get("newUser").get("employeeName").asText(),
                        uniqueUsername,
                        data.get("newUser").get("password").asText()
                }
        };
    }

    @DataProvider(name = "existingUsernameData")
    public Object[][] existingUsernameData() throws IOException {
        JsonNode data = new ObjectMapper().readTree(new File("src/test/resources/testData.json"));
        return new Object[][]{
                {data.get("existingUser").get("username").asText()}
        };
    }

    @DataProvider(name = "duplicateUsernameData")
    public Object[][] duplicateUsernameData() throws IOException {
        JsonNode data = new ObjectMapper().readTree(new File("src/test/resources/testData.json"));
        return new Object[][]{
                {
                        data.get("newUser").get("userRole").asText(),
                        data.get("newUser").get("employeeName").asText(),
                        data.get("existingUser").get("username").asText(),
                        data.get("newUser").get("password").asText()
                }
        };
    }

    //Tests

    @Test
    public void verifyAddUserPageFields() {
        adminPage.clickAdmin();
        adminPage.clickAddUser();

        Assert.assertTrue(adminPage.isUserRoleFieldDisplayed(), "User Role field should be displayed");
        Assert.assertTrue(adminPage.isEmployeeNameFieldDisplayed(), "Employee Name field should be displayed");
        Assert.assertTrue(adminPage.isUsernameFieldDisplayed(), "Username field should be displayed");
        Assert.assertTrue(adminPage.isPasswordFieldDisplayed(), "Password field should be displayed");
    }

    @Test(dataProvider = "newUserData")
    public void addNewUserWithDifferentRole(String userRole, String employeeName, String username, String password) {
        adminPage.clickAdmin();
        adminPage.clickAddUser();

        adminPage.clickUserRoleDropdown();
        adminPage.selectDropdownOption(userRole);

        adminPage.enterEmployeeNameForUser(employeeName);

        adminPage.clickStatusDropdown();
        adminPage.selectDropdownOption("Enabled");

        adminPage.enterUsername(username);
        adminPage.enterPassword(password);
        adminPage.enterConfirmPassword(password);
        adminPage.clickSaveUser();

        Assert.assertTrue(
                adminPage.isSuccessToastDisplayed(),
                "Expected success toast after adding new user"
        );

        adminPage.searchUserByUsername(username);
        Assert.assertTrue(
                adminPage.isUserDisplayedInList(username),
                "Newly added user '" + username + "' should appear in the user list"
        );
    }

    @Test(dataProvider = "existingUsernameData")
    public void searchUserByUsername(String username) {
        adminPage.clickAdmin();
        adminPage.searchUserByUsername(username);

        Assert.assertEquals(
                adminPage.getFirstRowUsername(),
                username,
                "Search result should return the correct username"
        );
    }

    @Test(dataProvider = "duplicateUsernameData")
    public void addUserWithDuplicateUsername(String userRole, String employeeName, String existingUsername, String password) {
        adminPage.clickAdmin();
        adminPage.clickAddUser();

        adminPage.clickUserRoleDropdown();
        adminPage.selectDropdownOption(userRole);

        adminPage.enterEmployeeNameForUser(employeeName);

        adminPage.enterUsername(existingUsername);
        adminPage.enterPassword(password);
        adminPage.enterConfirmPassword(password);
        adminPage.clickSaveUser();

        Assert.assertTrue(
                adminPage.isUsernameAlreadyExistsErrorDisplayed(),
                "Expected 'Already exists' validation error for duplicate username"
        );
    }
}