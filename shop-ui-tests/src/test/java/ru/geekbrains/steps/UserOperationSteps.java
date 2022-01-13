package ru.geekbrains.steps;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserOperationSteps {

    private WebDriver webDriver = LoginSteps.webDriver;

    @When("^I navigate to users$")
    public void iNavigateToProductsPage() throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("users-list"));
        webElement.click();
    }

    @When("^click on new user creating button$")
    public void clickOnNewUserCreatingButton() throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("btn-new-user"));
        webElement.click();
    }

    @When("^I provide user name as \"([^\"]*)\", age as \"([^\"]*)\"," +
            " password and repeated pass as \"([^\"]*)\" and role as \"([^\"]*)\"$")
    public void iProvideUserNameAsAndAgeAsAndPasswordAsAndRoleAs(String username, String age,
                                                          String password, String role) throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("username"));
        webElement.sendKeys(username);
        webElement = webDriver.findElement(By.id("age"));
        webElement.sendKeys(age);
        webElement = webDriver.findElement(By.id("password"));
        webElement.sendKeys(password);
        webElement = webDriver.findElement(By.id("matchPassword"));
        webElement.sendKeys(password);
        webElement = webDriver.findElement(By.id("roles"));
        List<WebElement> webElements = webElement.findElements(By.tagName("option"));
        for (WebElement element : webElements) {
            if (element.getText().equals(role)) {
                element.click();
                return;
            }
        }
    }

    @When("^I edit user name as \"([^\"]*)\", age as \\\"([^\\\"]*)\\\"," +
            " password and repeated pass as \\\"([^\\\"]*)\\\" and role as \\\"([^\\\"]*)\\\"$")
    public void iEditUsernameAsAndAgeAsAndPasswordAsAndRole(String username, String age,
                                                       String password, String role) throws Throwable {
        WebElement webElement = webDriver.findElement(By.id("username"));
        webElement.clear();
        webElement.sendKeys(username);
        webElement = webDriver.findElement(By.id("age"));
        webElement.clear();
        webElement.sendKeys(age);
        webElement = webDriver.findElement(By.id("password"));
        webElement.sendKeys(password);
        webElement = webDriver.findElement(By.id("matchPassword"));
        webElement.sendKeys(password);
        webElement = webDriver.findElement(By.id("roles"));
        List<WebElement> webElements = webElement.findElements(By.tagName("option"));
        for (WebElement element : webElements) {
            if (element.isSelected())
                element.click();
            if (element.getText().equals(role)) {
                element.click();
            }
        }
    }

    @When("^I fill user filters, name as \"([^\"]*)\", minage and maxage as \"([^\"]*)\"$")
    public void iFillUserFiltersNameAsAgeAs(String name, String age) {
        WebElement webElement = webDriver.findElement(By.id("usernameFilter"));
        webElement.sendKeys(name);
        webElement = webDriver.findElement(By.id("minAgeFilter"));
        webElement.sendKeys(age);
        webElement = webDriver.findElement(By.id("maxAgeFilter"));
        webElement.sendKeys(age);
    }

    @Then("^found user name should be \"([^\"]*)\", age \"([^\"]*)\", and role \"([^\"]*)\"$")
    public void foundUserNameShouldBeLikeAgeLikeRoleLike(String name, String age, String role) {
        String userSelector = "body > div.container > div > div:nth-child(3) > table > tbody > tr > ";
        WebElement webElement = webDriver.findElement(By.cssSelector(
                userSelector + "td:nth-child(2)"));
        assertThat(webElement.getText()).isEqualTo(name);
        webElement = webDriver.findElement(By.cssSelector(
                userSelector + "td:nth-child(3)"));
        assertThat(Double.valueOf(webElement.getText())).isEqualTo(Double.valueOf(age));
        webElement = webDriver.findElement(By.cssSelector(
                userSelector + "td:nth-child(4) > ul > li"));
        assertThat(webElement.getText()).isEqualTo(role);
    }

    @Then("^wait$")
    public void waitFor() throws InterruptedException {
        Thread.sleep(5000);
    }

    @Then("^user list should be empty$")
    public void userListShouldBeEmpty() {
        WebElement webElement = webDriver.findElement(
                By.cssSelector("body > div.container > div > div:nth-child(3) > table > tbody > tr > td"));
        assertThat(webElement.getText()).isEqualTo("No users");
    }

    @Then("^access should be denied$")
    public void accessShouldBeDenied() {
        WebElement webElement = webDriver.findElement(
                By.cssSelector("body > div.container > div > h2"));
        assertThat(webElement.getText()).isEqualTo("Access denied.");
    }
}