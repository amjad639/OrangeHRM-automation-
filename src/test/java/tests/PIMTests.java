package tests;

import base.BaseTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.PIMPage;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class PIMTests extends BaseTest {

    LoginPage loginPage;
    PIMPage pimPage;

    @BeforeMethod
    public void setUpTest() throws IOException {
        loginPage = new LoginPage(getDriver());
        pimPage = new PIMPage(getDriver());
        JsonNode data = new ObjectMapper().readTree(new File("src/test/resources/testData.json"));
        loginPage.ValidLogin(
                data.get("validCredentialsLogin").get("username").asText(),
                data.get("validCredentialsLogin").get("password").asText()
        );
    }

    @DataProvider(name = "employeeSearchData")
    public Object[][] employeeSearchData() throws IOException {
        JsonNode data = new ObjectMapper().readTree(
                new File("src/test/resources/testData.json"));
        return new Object[][]{
                {data.get("employeeSearch").get("existingEmployee").asText()}
        };
    }

    @DataProvider(name = "nonExistingEmployeeData")
    public Object[][] nonExistingEmployeeData() throws IOException {
        JsonNode data = new ObjectMapper().readTree(new File("src/test/resources/testData.json"));
        return new Object[][]{
                {data.get("employeeSearch").get("nonExistingEmployee").asText()}
        };
    }

    @DataProvider(name = "employeeCreationData")
    public Object[][] employeeCreationData() throws IOException {
        JsonNode data = new ObjectMapper().readTree(
                new File("src/test/resources/testData.json"));
        return new Object[][]{
                {
                        data.get("employeeCreation").get("firstName").asText(),
                        data.get("employeeCreation").get("lastName").asText()
                }
        };
    }

    @DataProvider(name = "employeeLastNameOnlyData")
    public Object[][] employeeLastNameOnlyData() throws IOException {
        JsonNode data = new ObjectMapper().readTree(new File("src/test/resources/testData.json"));
        return new Object[][]{
                {data.get("employeeCreation").get("lastName").asText()}
        };
    }

    @Test(dataProvider = "employeeSearchData")
    public void searchForExistingEmployee(String employeeName) {
        pimPage.clickPIM();
        pimPage.clickEmployeeList();
        pimPage.enterEmployeeName(employeeName);
        pimPage.clickSearch();
        Assert.assertTrue(
                pimPage.isEmployeeDisplayed(employeeName),
                "Expected employee '" + employeeName + "' to be displayed"
        );
    }

    @Test(dataProvider = "nonExistingEmployeeData")
    public void searchForNonExistingEmployee(String employeeName) {
        pimPage.clickPIM();
        pimPage.clickEmployeeList();
        pimPage.enterEmployeeName(employeeName);
        pimPage.clickSearch();
        Assert.assertEquals(
                pimPage.getNoRecordsMessage(),
                "No Records Found"
        );
    }

    @Test
    public void openAddEmployeePage() {
        pimPage.clickPIM();
        pimPage.clickAddEmployee();
        Assert.assertTrue(
                getDriver().getCurrentUrl().contains("/pim/addEmployee"),
                "Expected URL to contain /pim/addEmployee"
        );

        Assert.assertTrue(
                pimPage.isFirstNameDisplayed(),
                "First Name field should be displayed"
        );

        Assert.assertTrue(
                pimPage.isLastNameDisplayed(),
                "Last Name field should be displayed"
        );
    }

    @Test(dataProvider = "employeeLastNameOnlyData")
    public void addEmployeeWithEmptyFirstName(String lastName) {
        pimPage.clickPIM();
        pimPage.clickAddEmployee();
        pimPage.enterLastName(lastName);
        pimPage.clickSave();

        Assert.assertTrue(
                pimPage.isFirstNameRequiredErrorDisplayed(),
                "Expected 'Required' validation error under First Name"
        );
    }

    @Test(dataProvider = "employeeCreationData")
    public void addEmployeeEndToEnd(String firstName, String lastName) {
        String fullName = firstName + " " + lastName;
        pimPage.clickPIM();
        pimPage.clickAddEmployee();
        pimPage.enterFirstName(firstName);
        pimPage.enterLastName(lastName);
        pimPage.clickSave();

        new WebDriverWait(getDriver(), Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/pim/viewPersonalDetails"));

        Assert.assertTrue(
                getDriver().getCurrentUrl().contains("/pim/viewPersonalDetails"),
                "Expected to land on Personal Details page after saving"
        );

        Assert.assertTrue(
                pimPage.isPersonalDetailsPageDisplayed(),
                "Personal Details header should be displayed"
        );

        pimPage.clickPIM();
        pimPage.clickEmployeeList();
        pimPage.enterEmployeeName(fullName);
        pimPage.clickSearch();

        Assert.assertTrue(
                pimPage.isEmployeeDisplayed(fullName),
                "Newly created employee '" + fullName + "' should appear in search results"
        );
    }
}