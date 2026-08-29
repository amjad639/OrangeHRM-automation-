package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class DashboardTests extends BaseTest {

    LoginPage loginPage;
    DashboardPage dashboardPage;

    @BeforeMethod
    public void setUpTest() throws IOException {
        loginPage = new LoginPage(getDriver());
        dashboardPage = new DashboardPage(getDriver());
        JsonNode data = new ObjectMapper().readTree(new File("src/test/resources/testData.json"));
        loginPage.ValidLogin(
                data.get("validCredentialsLogin").get("username").asText(),
                data.get("validCredentialsLogin").get("password").asText()
        );
    }

    @Test
    public void verifyFooterBrandingLink() {
        String footerText = dashboardPage.getFooterText();
        Assert.assertTrue(
                footerText.contains("OrangeHRM, Inc"),
                "Footer should contain 'OrangeHRM, Inc', but was: " + footerText
        );
        String newTabUrl = dashboardPage.clickFooterLinkAndGetNewTabUrl();
        Assert.assertTrue(
                newTabUrl.contains("orangehrm.com"),
                "New tab URL should contain 'orangehrm.com', but was: " + newTabUrl
        );
    }
}