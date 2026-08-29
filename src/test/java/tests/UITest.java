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

public class UITest extends BaseTest {

    LoginPage loginPage;
    DashboardPage dashboardPage;

    @BeforeMethod
    public void setUpTest() throws IOException {

        loginPage = new LoginPage(getDriver());
        dashboardPage = new DashboardPage(getDriver());
        JsonNode data = new ObjectMapper().readTree(
                new File("src/test/resources/testData.json"));
        loginPage.ValidLogin(
                data.get("validCredentialsLogin").get("username").asText(),
                data.get("validCredentialsLogin").get("password").asText()
        );
    }

    @Test
    public void verifySidebarMenuItems() {
        List<String> expectedMenuItems = Arrays.asList(
                "Admin", "PIM", "Leave", "Time", "Recruitment",
                "My Info", "Performance", "Dashboard", "Directory"
        );
        List<String> actualMenuItems = dashboardPage.getSidebarMenuTexts();
        System.out.println("ACTUAL SIDEBAR ITEMS = " + actualMenuItems);
        for (String expected : expectedMenuItems) {
            Assert.assertTrue(
                    actualMenuItems.stream().anyMatch(actual -> actual.equalsIgnoreCase(expected)),
                    "Expected sidebar to contain: " + expected
            );
        }
    }
}