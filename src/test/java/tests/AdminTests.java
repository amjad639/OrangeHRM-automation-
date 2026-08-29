package tests;

import base.BaseTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
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

    @Test
    public void verifyAddUserPageFields() {

        adminPage.clickAdmin();
        adminPage.clickAddUser();

        Assert.assertTrue(adminPage.isUserRoleFieldDisplayed(), "User Role field should be displayed");
        Assert.assertTrue(adminPage.isEmployeeNameFieldDisplayed(), "Employee Name field should be displayed");
        Assert.assertTrue(adminPage.isUsernameFieldDisplayed(), "Username field should be displayed");
        Assert.assertTrue(adminPage.isPasswordFieldDisplayed(), "Password field should be displayed");
    }
}