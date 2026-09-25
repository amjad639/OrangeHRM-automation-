package stepdefinitions;

import context.TestContext;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.DashboardPage;

import java.util.List;

public class DashboardSteps {

    private final DashboardPage dashboardPage;
    private final TestContext context;

    public DashboardSteps(TestContext context) {
        this.context = context;
        dashboardPage = new DashboardPage(context.getDriver());
    }

    @Then("the following dashboard widgets should be displayed:")
    public void widgetsShouldBeDisplayed(DataTable dataTable) {
        List<String> widgets = dataTable.asList();
        for (String widget : widgets) {
            Assert.assertTrue(
                    dashboardPage.isWidgetPresent(widget.trim()),
                    "Expected dashboard widget to be displayed: " + widget
            );
        }
    }

    @When("the user clicks the Quick Launch item {string}")
    public void clickQuickLaunchItem(String itemName) {
        dashboardPage.clickQuickLaunchItem(itemName);
    }

    @Then("the URL should contain {string}")
    public void urlShouldContain(String partialUrl) {
        Assert.assertTrue(
                context.getDriver().getCurrentUrl().contains(partialUrl),
                "Expected URL to contain: " + partialUrl
        );
    }
}
