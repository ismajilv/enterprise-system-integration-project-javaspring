package com.example.rentit;

import com.example.rentit.sales.rest.dtos.*;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class Stepdefs {
    WebDriver rentItEmployee;
    WebDriver siteEngineer;
    WebDriver workEngineer;
    RestTemplate restTemplate = new RestTemplate();
    final int rentItFEPort = 7070;
    final int rentItBEPort = 8090;
    final int buildItFEPort = 6080;
    final int buildItBEPort = 8080;
    final String host = "http://localhost:";
    final int waitForMs = 2000;

    static {
        System.setProperty("webdriver.chrome.driver", "c:/drivers/chromedriver.exe");
    }

    @Before
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        rentItEmployee = new ChromeDriver(options);
    }

    @After
    public void tearoff() {
        // Comment the following line if you want to check the application's final state on the browser
       rentItEmployee.close();
       //siteEngineer.close();
       //workEngineer.close();
    }

    @Given("^the test data has been set up$")
    public void the_test_data_has_been_set_up() throws Throwable {
        restTemplate.postForObject(host+rentItBEPort+"/test/initData", null, PlantInventoryEntry[].class);
    }

    @Given("^employee is in the RentIt webpage \"([^\"]*)\" tab$")public void employee_is_in_the_RentIt_webpage_tab(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        rentItEmployee.get(host+rentItFEPort+"/#/PlantDepotEmployee");
    }

    @When("^employee selects \"([^\"]*)\" tab$")
    public void employee_selects_tab(String arg1) throws Throwable {
        rentItEmployee.get(host+rentItFEPort+"/#/PlantDepotEmployee");
    }

    @Then("^(\\d+) purchase orders are shown$")
    public void purchase_orders_are_shown(int arg1) throws Throwable {
        Thread.sleep(waitForMs);
        List<WebElement> rows = rentItEmployee.findElements(By.className("table-row-rentit"));
        assertThat(rows).hasSize(arg1);
    }

    @Then("^po with for \"([^\"]*)\" of them has status \"([^\"]*)\"$")
    public void po_with_for_of_them_has_status(String arg1, String arg2) throws Throwable {
        Thread.sleep(waitForMs);
        WebElement row = rentItEmployee.findElement(By.xpath(String.format("//tr/td[contains(text(), '%s')]", arg1)));
        WebElement parentElement = row.findElement(By.xpath("./.."));
        assertThat(arg2).isEqualToIgnoringWhitespace(parentElement.findElement(By.id("poStatus")).getText());
    }

    @When("^employee clicks \"([^\"]*)\" button PO with name \"([^\"]*)\"$")
    public void employee_clicks_button_PO_with_name(String arg1, String arg2) throws Throwable {
        WebElement row = rentItEmployee.findElement(By.xpath(String.format("//tr/td[contains(text(), '%s')]", arg2)));
        WebElement parentElement = row.findElement(By.xpath("./.."));
        parentElement.findElement(By.id(arg1)).click();
    }

    @Given("^site engineer is in the BuildIt webpage \"([^\"]*)\" tab$")
    public void site_engineer_is_in_the_BuildIt_webpage_tab(String arg1) throws Throwable {
        siteEngineer.get(host+buildItFEPort+"/#/");
    }

    @Given("^RentIt's employee is in the RentIt webpage \"([^\"]*)\" tab$")
    public void rentit_s_employee_is_in_the_RentIt_webpage_tab(String arg1) throws Throwable {
        //rentItEmployee.get(host+rentItFEPort+"/#/");
    }

}