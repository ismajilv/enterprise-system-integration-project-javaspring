package com.example.rentit;

import com.example.rentit.sales.rest.dtos.PlantInventoryEntry;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class Stepdefs {
    WebDriver rentItUser;
    WebDriver buildItUser;
    RestTemplate restTemplate = new RestTemplate();
    int rentItFEPort = 8080;
    int rentItBEPort = 8090;
    int buildItFEPort = 9000;
    int buildItBEPort = 9010;
    String host = "http://localhost:";

    static {
        System.setProperty("webdriver.chrome.driver", "c:/drivers/chromedriver.exe");
    }

    @Before
    public void setup() {
        rentItUser = new ChromeDriver();
        buildItUser = new ChromeDriver();
    }

    @After
    public void tearoff() {
        // Comment the following line if you want to check the application's final state on the browser
       // customer.close();
    }

    @Given("^the following plant catalog$")
    public void the_following_plant_catalog(DataTable table) throws Exception {
        List<PlantInventoryEntry> entries = table.asMaps(String.class, String.class)
                .stream()
                .map(row -> PlantInventoryEntry.of(row.get("id"), row.get("name"), row.get("description"), row.get("price")))
                .collect(Collectors.toList());

        PlantInventoryEntry[] result = restTemplate.postForObject(host+rentItBEPort+"/api/entries", entries, PlantInventoryEntry[].class);
    }

    @Given("^the following inventory$")
    public void the_following_inventory(DataTable items) throws Exception {
    }

    /*@Given("^a customer is in the \"([^\"]*)\" web page$")
    public void a_customer_is_in_the_web_page(String arg1) throws Exception {
        rentItUser.get("http://localhost:8080/#/");
    }

    @Given("^no purchase order exists in the system$")
    public void no_purchase_order_exists_in_the_system() throws Exception {
    }

    @When("^the customer queries the plant catalog for an \"([^\"]*)\" available from \"([^\"]*)\" to \"([^\"]*)\"$")
    public void the_customer_queries_the_plant_catalog_for_an_available_from_to(String plantName, String startDate, String endDate) throws Exception {
        rentItUser.findElement(By.id("name")).sendKeys(plantName);
        rentItUser.findElement(By.id("start-date")).sendKeys(startDate);
        rentItUser.findElement(By.id("end-date")).sendKeys(endDate);
        rentItUser.findElement(By.id("submit-query")).click();
    }

    @Then("^(\\d+) plants are shown$")
    public void plants_are_shown(int numberOfPlants) throws Exception {
        Thread.sleep(3000);
        List<?> rows = rentItUser.findElements(By.className("table-row"));
        assertThat(rows).hasSize(numberOfPlants);
    }

    @When("^the customer selects a \"([^\"]*)\"$")
    public void the_customer_selects_a(String plantDescription) throws Exception {
        WebElement row = rentItUser.findElement(By.xpath(String.format("//tr/td[contains(text(), '%s')]", plantDescription)));
        WebElement selectPlantButton = row.findElement(By.xpath("//a[contains(text(), 'Select plant')]"));
        selectPlantButton.click();
    }

    @Then("^a purchase order should be created with a total price of (\\d+\\.\\d+)$")
    public void a_purchase_order_should_be_created_with_a_total_price_of(double price) throws Exception {

        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }*/

    @Given("^a BuildIt's user is in the \"([^\"]*)\" web page$")
    public void a_BuildIt_s_user_is_in_the_web_page(String arg1) throws Throwable {
        buildItUser.get(host+buildItFEPort+"/#/");
    }

    @Given("^a Rentit's user is in the \"([^\"]*)\" web page$")
    public void a_Rentit_s_user_is_in_the_web_page(String arg1) throws Throwable {
        rentItUser.get(host+rentItFEPort+"/#/");
    }

    @Given("^no purchase order exists in the system$")
    public void no_purchase_order_exists_in_the_system() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^the BuildIt's user queries the plant catalog for an \"([^\"]*)\" available from \"([^\"]*)\" to \"([^\"]*)\" from \"([^\"]*)\" to be used on site \"([^\"]*)\"$")
    public void the_BuildIt_s_user_queries_the_plant_catalog_for_an_available_from_to_from_to_be_used_on_site(String arg1, String arg2, String arg3, String arg4, String arg5) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^(\\d+) plants are shown$")
    public void plants_are_shown(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^the BuildIt's user selects a \"([^\"]*)\"$")
    public void the_BuildIt_s_user_selects_a(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the BuildIt's user sees the price and availability of the the selected plant$")
    public void the_BuildIt_s_user_sees_the_price_and_availability_of_the_the_selected_plant() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^the BuildIt's user accepts the plant hire request$")
    public void the_BuildIt_s_user_accepts_the_plant_hire_request() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the RentIt's user sees a plant hire request for \"([^\"]*)\"$")
    public void the_RentIt_s_user_sees_a_plant_hire_request_for(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^the RentIt's user accepts the plant hire request$")
    public void the_RentIt_s_user_accepts_the_plant_hire_request() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the BuildIt's user sees that the state of the plant hire request is \"([^\"]*)\"$")
    public void the_BuildIt_s_user_sees_that_the_state_of_the_plant_hire_request_is(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^the RentIt's user rejects the plant hire request$")
    public void the_RentIt_s_user_rejects_the_plant_hire_request() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^the BuildIt's user reject the plant hire request$")
    public void the_BuildIt_s_user_reject_the_plant_hire_request() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the RentIt's user doesn't see a plant hire request for \"([^\"]*)\"$")
    public void the_RentIt_s_user_doesn_t_see_a_plant_hire_request_for(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}