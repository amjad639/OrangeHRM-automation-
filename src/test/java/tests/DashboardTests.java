package tests;

import base.BaseTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
    public void verifyCoreDashboardWidgets() {
        List<String> expectedWidgets = Arrays.asList(
                "Time at Work", "My Actions", "Quick Launch", "Employees on Leave Today"
        );

        for (String widget : expectedWidgets) {
            Assert.assertTrue(
                    dashboardPage.isWidgetPresent(widget),
                    "Expected dashboard widget to be displayed: " + widget
            );
        }
    }

    @Test
    public void navigateViaQuickLaunch() {
        dashboardPage.clickQuickLaunchItem("Assign Leave");

        Assert.assertTrue(
                getDriver().getCurrentUrl().contains("/leave/"),
                "Expected Quick Launch 'Assign Leave' to navigate into the Leave module"
        );
    }
}