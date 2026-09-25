package stepdefinitions;

import context.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.PIMPage;

public class PIMSteps {

    private final PIMPage pimPage;

    public PIMSteps(TestContext context) {
        pimPage = new PIMPage(context.getDriver());
    }

    @When("the user searches for employee {string} in PIM")
    public void searchForEmployee(String employeeName) {
        pimPage.clickPIM();
        pimPage.clickEmployeeList();
        pimPage.enterEmployeeName(employeeName);
        pimPage.clickSearch();
    }

    @Then("the employee {string} should be displayed in the list")
    public void employeeShouldBeDisplayed(String employeeName) {
        Assert.assertTrue(
                pimPage.isEmployeeDisplayed(employeeName),
                "Expected employee '" + employeeName + "' to be displayed"
        );
    }

    @Then("a {string} message should be shown")
    public void noRecordsMessageShown(String expectedMessage) {
        Assert.assertEquals(pimPage.getNoRecordsMessage(), expectedMessage);
    }

    @When("the user navigates to PIM and clicks Add")
    public void navigateToPimAndClickAdd() {
        pimPage.clickPIM();
        pimPage.clickAddEmployee();
    }

    @And("the First Name and Last Name fields should be displayed")
    public void firstLastNameFieldsDisplayed() {
        Assert.assertTrue(pimPage.isFirstNameDisplayed(), "First Name field should be displayed");
        Assert.assertTrue(pimPage.isLastNameDisplayed(), "Last Name field should be displayed");
    }

    @And("the user enters last name {string} only")
    public void enterLastNameOnly(String lastName) {
        pimPage.enterLastName(lastName);
    }

    @And("the user clicks Save on employee form")
    public void clickSaveEmployee() {
        pimPage.clickSave();
    }

    @Then("a {string} validation error should appear under First Name")
    public void firstNameRequiredErrorShown(String expectedText) {
        Assert.assertTrue(
                pimPage.isFirstNameRequiredErrorDisplayed(),
                "Expected '" + expectedText + "' validation error under First Name"
        );
    }

    @And("the user enters first name {string} and last name {string}")
    public void enterFirstAndLastName(String firstName, String lastName) {
        pimPage.enterFirstName(firstName);
        pimPage.enterLastName(lastName);
    }

    @And("the Personal Details page should be displayed")
    public void personalDetailsDisplayed() {
        Assert.assertTrue(
                pimPage.isPersonalDetailsPageDisplayed(),
                "Personal Details header should be displayed"
        );
    }

    @And("the user clicks Edit on the first employee row")
    public void clickEditFirstRow() {
        pimPage.clickFirstEditButton();
    }

    @And("the user updates the last name to {string}")
    public void updateLastName(String newLastName) {
        pimPage.updateLastName(newLastName);
    }

    @Then("an update success toast should be displayed")
    public void updateSuccessToastDisplayed() {
        Assert.assertTrue(
                pimPage.isUpdateSuccessToastDisplayed(),
                "Expected success toast after updating employee"
        );
    }

    @And("the user clicks Delete on the first employee row")
    public void clickDeleteFirstRow() {
        pimPage.clickFirstDeleteButton();
    }

    @And("the user confirms the delete")
    public void confirmDelete() {
        pimPage.confirmDelete();
    }

    @Then("a delete success toast should be displayed")
    public void deleteSuccessToastDisplayed() {
        Assert.assertTrue(
                pimPage.isDeleteSuccessToastDisplayed(),
                "Expected success toast after deleting employee"
        );
    }

    @When("the user clicks Search on PIM page")
    public void clickSearchOnPim() {
        pimPage.clickSearch();
    }

    @When("the user navigates to PIM Employee List")
    public void navigateToPimEmployeeList() {
        pimPage.clickPIM();
        pimPage.clickEmployeeList();
    }

    @And("the user selects Employment Status {string}")
    public void selectEmploymentStatus(String status) {
        pimPage.selectEmploymentStatus(status);
    }

    @Then("all displayed rows should have status {string}")
    public void allRowsMatchStatus(String expectedStatus) {
        Assert.assertTrue(
                pimPage.areAllRowsMatchingStatus(expectedStatus),
                "All displayed rows should have Employment Status = " + expectedStatus
        );
    }
}