package ru.geekbrains.steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductOperationSteps {

    private WebDriver webDriver = LoginSteps.webDriver;

    @When("^I navigate to products$")
    public void iNavigateToProductsPage() throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("products-list"));
        webElement.click();
    }

    @When("^click on new product creating button$")
    public void clickOnNewProductCreatingButton() throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("btn-new-product"));
        webElement.click();
    }

    @When("^I provide product name as \"([^\"]*)\", cost as \"([^\"]*)\" and category as \"([^\"]*)\"$")
    public void iProvideProductNameAsAndCostAsAndCategory(String productName, String cost, String category) throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("name"));
        webElement.sendKeys(productName);
        webElement = webDriver.findElement(By.id("cost"));
        webElement.sendKeys(cost);
        webElement = webDriver.findElement(By.id("categories"));
        List<WebElement> webElements = webElement.findElements(By.tagName("option"));
        for (WebElement element : webElements) {
            if (element.getText().equals(category)) {
                element.click();
                return;
            }
        }
    }

    @When("^I edit product name as \"([^\"]*)\", cost as \"([^\"]*)\" and category as \"([^\"]*)\"$")
    public void iEditProductNameAsAndCostAsAndCategory(String productName, String cost, String category) throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("name"));
        webElement.clear();
        webElement.sendKeys(productName);
        webElement = webDriver.findElement(By.id("cost"));
        webElement.clear();
        webElement.sendKeys(cost);
        webElement = webDriver.findElement(By.id("categories"));
        List<WebElement> webElements = webElement.findElements(By.tagName("option"));
        for (WebElement element : webElements) {
            if (element.isSelected())
                element.click();
            if (element.getText().equals(category)) {
                element.click();
            }
        }
    }

    @When("^click submit button$")
    public void clickSubmitButton() throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("btn-submit-editing"));
        webElement.click();
    }

    @When("^apply filter$")
    public void applyProductFilter() throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("btn-apply-filter"));
        webElement.click();
    }

    @When("^I click on edit button$")
    public void iClickOnProductEditButton() throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("btn-edit"));
        webElement.click();
    }

    @When("^I click on delete button$")
    public void iClickOnProductDeleteButton() throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("btn-delete"));
        webElement.click();
    }

    @When("^I click on success deleting button$")
    public void iClickOnSuccessDeletingButton() throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("btn-submit-deleting"));
        webElement.click();
    }

    @When("^I fill product filters, name as \"([^\"]*)\", mincost and maxcost as \"([^\"]*)\"" +
            ", and category as \"([^\"]*)\"$")
    public void iFillProductFiltersNameAsCostsAsCategoryAs(String productName, String cost, String category) {
        WebElement webElement = webDriver.findElement(By.id("productNameFilter"));
        webElement.sendKeys(productName);
        webElement = webDriver.findElement(By.id("minCostFilter"));
        webElement.sendKeys(cost);
        webElement = webDriver.findElement(By.id("maxCostFilter"));
        webElement.sendKeys(cost);
        webElement = webDriver.findElement(By.id("categoryFilter"));
        webElement.sendKeys(category);
    }

    @Then("^found product name should be \"([^\"]*)\", cost \"([^\"]*)\", and category \"([^\"]*)\"$")
    public void foundProductNameShouldBeLikeCostLikeCategoryLike(String productName, String cost, String category) {
        String productSelector = "body > div.container > div > div:nth-child(3) > table > tbody > tr > ";
        WebElement webElement = webDriver.findElement(By.cssSelector(
                productSelector + "td:nth-child(3)"));
        assertThat(webElement.getText()).isEqualTo(productName);
        webElement = webDriver.findElement(By.cssSelector(
                productSelector + "td:nth-child(4)"));
        assertThat(Double.valueOf(webElement.getText())).isEqualTo(Double.valueOf(cost));
        webElement = webDriver.findElement(By.cssSelector(
                productSelector + "td:nth-child(5) > ul > li"));
        assertThat(webElement.getText()).isEqualTo(category);
    }

    @Then("^product list should be empty$")
    public void productListShouldBeEmpty() {
        WebElement webElement = webDriver.findElement(
                By.cssSelector("body > div.container > div > div:nth-child(3) > table > tbody > tr > td"));
        assertThat(webElement.getText()).isEqualTo("No products");
    }
}
