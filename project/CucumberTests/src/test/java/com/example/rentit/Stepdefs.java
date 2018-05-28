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
        //siteEngineer = new ChromeDriver();
        //workEngineer = new ChromeDriver();
    }

    @After
    public void tearoff() {
        // Comment the following line if you want to check the application's final state on the browser
      // rentItEmployee.close();
       //siteEngineer.close();
       //workEngineer.close();
    }

    @Given("^the test data has been set up$")
    public void the_test_data_has_been_set_up() throws Throwable {
        //TODO restore test data every time- currently need to restart rentit and buildit
    }

    @Given("^the following plant catalog$")
    public void the_following_plant_catalog(DataTable table) throws Exception {
        /*List<PlantInventoryEntry> entries = table.asMaps(String.class, String.class)
                .stream()
                .map(row -> PlantInventoryEntry.of(row.get("id"), row.get("name"), row.get("description"), row.get("price")))
                .collect(Collectors.toList());

        PlantInventoryEntry[] result = restTemplate.postForObject(host+rentItBEPort+"/api/entries", entries, PlantInventoryEntry[].class);
        assertThat(result).hasSize(11);*/
    }

    /*
    {
        "constructionSiteId": 2,
        "supplierId": 4,
        "plantHref": "http://localhost:8090/api/sales/plants/3",
        "rentalPeriod" : {
        "startDate" : "2018-05-26",
        "endDate" : "2018-05-30"
        }
        }
     */

    @Given("^plant hire requests have been set up$")
    public void plant_hire_requests_have_been_set_up() throws Throwable {    // Write code here that turns the phrase above into concrete actions
        //PlantHireRequest[] result = restTemplate.postForObject(host+buildItBEPort+"/api/phr", 8, PlantHireRequest[].class);
        //assertThat(result).hasSize(11);
    }

    @Given("^purchase orders have been set up$")
    public void purchase_orders_have_been_set_up() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
       // PurchaseOrder[] result = restTemplate.postForObject(host+rentItBEPort+"/api/pos",8, PurchaseOrder[].class);
       // assertThat(result).hasSize(11);
    }

    @Given("^the following inventory$")
    public void the_following_inventory(DataTable table) throws Exception {
        /*List<PlantInventoryItem> items = table.asMaps(String.class, String.class)
                .stream()
                .map(row -> PlantInventoryItem.of(row.get("id"), row.get("serialNumber"), row.get("equipmentCondition"), row.get("plantInfo")))
                .collect(Collectors.toList());
        PlantInventoryItem[] result = restTemplate.postForObject(host+rentItBEPort+"/api/items", items, PlantInventoryItem[].class);
        assertThat(result).hasSize(8);*/
    }

    @Given("^the following Purchase Orders$")
    public void the_following_Purchase_Orders(DataTable arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        // For automatic transformation, change DataTable to one of
        // List<YourType>, List<List<E>>, List<Map<K,V>> or Map<K,V>.
        // E,K,V must be a scalar (String, Integer, Date, enum etc)
       // throw new PendingException();
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


    @Then("^the status of po with id (\\d+) changes to \"([^\"]*)\"$")
    public void the_status_of_po_with_id_changes_to(int arg1, String arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^button \"([^\"]*)\" is enabled for po with id (\\d+)$")
    public void button_is_enabled_for_po_with_id(String arg1, int arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^button \"([^\"]*)\" is disabled for po with id (\\d+)$")
    public void button_is_disabled_for_po_with_id(String arg1, int arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^purchase order status is not \"([^\"]*)\"$")
    public void purchase_order_status_is_not(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^button \"([^\"]*)\" is disabled$")
    public void button_is_disabled(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^disabled \"([^\"]*)\" button is clicked$")
    public void disabled_button_is_clicked(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^status of the purchase order is not changed$")
    public void status_of_the_purchase_order_is_not_changed() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }


    @Given("^the following suppliers$")
    public void the_following_suppliers(DataTable table) throws Throwable {
/*        List<Supplier> suppliers = table.asMaps(String.class, String.class)
                .stream()
                .map(row -> Supplier.of(row.get("id"), row.get("name")))
                .collect(Collectors.toList());
        Supplier[] result = restTemplate.postForObject(host+buildItBEPort+"/api/suppliers", suppliers, Supplier[].class);
        assertThat(result).hasSize(2);
  */  }

    @Given("^the following construction sites$")
    public void the_following_construction_sites(DataTable table) throws Throwable {
    /*    List<ConstructionSite> constructionSites = table.asMaps(String.class, String.class)
                .stream()
                .map(row -> ConstructionSite.of(row.get("id"), row.get("address")))
                .collect(Collectors.toList());
        ConstructionSite[] result = restTemplate.postForObject(host+buildItBEPort+"/api/constructionsites", constructionSites, ConstructionSite[].class);
        assertThat(result).hasSize(3);
    */}

    @Given("^site engineer is in the BuildIt webpage \"([^\"]*)\" tab$")
    public void site_engineer_is_in_the_BuildIt_webpage_tab(String arg1) throws Throwable {
        siteEngineer.get(host+buildItFEPort+"/#/");
    }

    @Given("^work engineer is in the BuildIt webpage \"([^\"]*)\" tab$")
    public void work_engineer_is_in_the_BuildIt_webpage_tab(String arg1) throws Throwable {
        //workEngineer.get(host+buildItFEPort+"/#/WorkEngineer");
    }

    @Given("^RentIt's employee is in the RentIt webpage \"([^\"]*)\" tab$")
    public void rentit_s_employee_is_in_the_RentIt_webpage_tab(String arg1) throws Throwable {
        //rentItEmployee.get(host+rentItFEPort+"/#/");
    }

    @Given("^no purchase order exists in the RentIts system$")
    public void no_purchase_order_exists_in_the_RentIts_system() throws Throwable {
        int count = restTemplate.getForObject(host+rentItBEPort+"/api/po/count", Integer.class);
        assertThat(count).isEqualTo(0);
    }

    @Given("^no plant hire requests exists in the BuildIt system$")
    public void no_plant_hire_requests_exists_in_the_BuildIt_system() throws Throwable {
        int count = restTemplate.getForObject(host+buildItBEPort+"/api/plantihirerequest/count", Integer.class);
        assertThat(count).isEqualTo(0);
    }

    @When("^site engineer queries the plant catalog for an \"([^\"]*)\" available from \"([^\"]*)\" to \"([^\"]*)\"$")
    public void site_engineer_queries_the_plant_catalog_for_an_available_from_to(String plantName, String startDate, String endDate) throws Throwable {
        Thread.sleep(waitForMs);
        siteEngineer.findElement(By.id("name")).sendKeys(plantName);
        siteEngineer.findElement(By.id("start-date")).sendKeys(startDate);
        siteEngineer.findElement(By.id("end-date")).sendKeys(endDate);
        siteEngineer.findElement(By.id("submit-query")).click();
    }

    @Then("^(\\d+) plants are shown including \"([^\"]*)\" with price (\\d+)$")
    public void plants_are_shown_including_with_price(int numberOfPlants, String description, int price) throws Throwable {
        Thread.sleep(waitForMs);
        List<WebElement> rows = siteEngineer.findElements(By.className("table-row"));
        assertThat(rows).hasSize(numberOfPlants);
        WebElement specificColumnRow = siteEngineer.findElement(By.xpath(String.format("//tr/td[contains(text(), '%s')]", description)));
        //WebElement parentElement = specificColumnRow.findElement(By.xpath("./.."));
        //assertThat(parentElement.findElement(By.id("plantName")).getText()).isEqualToIgnoringCase("Mini Excavator");
        //assertThat(parentElement.findElement(By.id("plantPrice")).getText()).isEqualToIgnoringCase(String.valueOf(price));
        //assertThat(parentElement.findElement(By.id("plantDesc")).getText()).isEqualToIgnoringCase(description);
    }

    @When("^site engineer selects a \"([^\"]*)\"$")
    public void site_engineer_selects_a(String plantDescription) throws Throwable {
        Thread.sleep(waitForMs);
        WebElement row = siteEngineer.findElement(By.xpath(String.format("//tr/td[contains(text(), '%s')]", plantDescription)));
        WebElement selectPlantButton = row.findElement(By.xpath("//a[contains(text(), 'Select plant')]"));
        selectPlantButton.click();
    }

    @Then("^tab is changed to \"([^\"]*)\"$")
    public void tab_is_changed_to(String tabName) throws Throwable {
        Thread.sleep(waitForMs);
        WebElement tab = siteEngineer.findElement(By.xpath(String.format("//span[contains(text(), '%s')]", tabName)));
        WebElement parentElement = tab.findElement(By.xpath("./../.."));
        assertThat("is-active").isEqualToIgnoringCase(parentElement.getAttribute("class"));
    }

    @When("^site engineer selects supplier \"([^\"]*)\"$")
    public void site_engineer_selects_supplier(String supplierName) throws Throwable {
        Thread.sleep(waitForMs);
        List<WebElement> selectors = siteEngineer.findElements(By.xpath("//select"));
        Select supplier = new Select(selectors.get(0));
        supplier.selectByIndex(1);
       // supplier.selectByVisibleText(supplierName);
    }

    @When("^selects construction site \"([^\"]*)\"$")
    public void selects_construction_site(String siteName) throws Throwable {
        Thread.sleep(waitForMs);
        List<WebElement> selectors = siteEngineer.findElements(By.xpath("//select"));
        Select site = new Select(selectors.get(1));
        site.selectByIndex(1);
        //ssite.selectByVisibleText(siteName);
    }

    @When("^pushes \"([^\"]*)\" button$")
    public void pushes_button(String buttonName) throws Throwable {
        Thread.sleep(waitForMs);
        WebElement button = siteEngineer.findElement(By.xpath(String.format("//button[contains(text(), '%s')]", buttonName)));
        button.click();
    }

    @Then("^(\\d+) \"([^\"]*)\" plant hire request with name \"([^\"]*)\" from \"([^\"]*)\" to \"([^\"]*)\" with price \"([^\"]*)\" is shown for the work engineer$")
    public void plant_hire_request_with_name_from_to_with_price_is_shown_for_the_work_engineer(int numberOfRequests, String status, String name, String from, String to, String price) throws Throwable {
        workEngineer.get(host+buildItFEPort+"/#/WorkEngineer");
        Thread.sleep(waitForMs);
        List<WebElement> rows = workEngineer.findElements(By.className("table-row-we2"));
        assertThat(rows).hasSize(numberOfRequests);
        WebElement specificRow = rows.get(0);
        assertThat(specificRow.findElement(By.id("plantNameWE2")).getText()).isEqualToIgnoringCase(name);
        assertThat(specificRow.findElement(By.id("plantStartDateWE2")).getText()).isEqualToIgnoringCase(from);
        assertThat(specificRow.findElement(By.id("plantEndDateWE2")).getText()).isEqualToIgnoringCase(to);
        assertThat(specificRow.findElement(By.id("plantPriceWE2")).getText()).isEqualToIgnoringCase(price);
    }

    @Then("^(\\d+) \"([^\"]*)\" plant hire request with name \"([^\"]*)\" from \"([^\"]*)\" to \"([^\"]*)\" with price \"([^\"]*)\" is shown for the site engineer$")
    public void plant_hire_request_with_name_from_to_with_price_is_shown_for_the_site_engineer(int numberOfRequests, String status, String name, String from, String to, String price) throws Throwable {
        Thread.sleep(waitForMs);
        List<WebElement> rows = siteEngineer.findElements(By.className("table-row-hire"));
        assertThat(rows).hasSize(numberOfRequests);
        WebElement specificRow = rows.get(0);
        assertThat(specificRow.findElement(By.id("plantNameHire")).getText()).isEqualToIgnoringCase(name);
        assertThat(specificRow.findElement(By.id("plantStartDateHire")).getText()).isEqualToIgnoringCase(from);
        assertThat(specificRow.findElement(By.id("plantEndDateHire")).getText()).isEqualToIgnoringCase(to);
        assertThat(specificRow.findElement(By.id("plantTotalCostHire")).getText()).isEqualToIgnoringCase(price);
        assertThat(specificRow.findElement(By.id("plantStatusHire")).getText()).isEqualToIgnoringCase(status);
    }

    @When("^work engineer accepts the \"([^\"]*)\" plant hire request$")
    public void work_engineer_accepts_the_plant_hire_request(String plantDescription) throws Throwable {
        Thread.sleep(waitForMs);
        WebElement row = workEngineer.findElement(By.xpath(String.format("//tr/td[contains(text(), '%s')]", plantDescription)));
        WebElement acceptButton = row.findElement(By.xpath("//a[contains(text(), 'Accept')]"));
        acceptButton.click();
    }

    @Then("^the site engineer sees that the state of the plant hire request for \"([^\"]*)\" is \"([^\"]*)\"$")
    public void the_site_engineer_sees_that_the_state_of_the_plant_hire_request_for_is(String plantDescription, String status) throws Throwable {
        Thread.sleep(waitForMs);
        WebElement row = siteEngineer.findElement(By.xpath(String.format("//tr/td[contains(text(), '%s')]", plantDescription)));
        assertThat(row.findElement(By.id("plantStatusHireUp")).getText()).isEqualToIgnoringCase(status);
    }

    @Then("^(\\d+) pending plant hire request is/are shown for the RentIt's employee$")
    public void pending_plant_hire_request_is_are_shown_for_the_RentIt_s_employee(int arg1) throws Throwable {
        rentItEmployee.get(host+rentItFEPort+"/#/");
        List<WebElement> rows = rentItEmployee.findElements(By.className("table-row-rentit"));
        assertThat(rows).hasSize(arg1);
    }

    @When("^the RentIt's employee pushes \"([^\"]*)\" button for the plant hire request for \"([^\"]*)\"$")
    public void the_RentIt_s_employee_pushes_button_for_the_plant_hire_request_for(String buttonLabel, String plantDescription) throws Throwable {
        WebElement row = rentItEmployee.findElement(By.xpath(String.format("//tr/td[contains(text(), '%s')]", plantDescription)));
        WebElement acceptButton = row.findElement(By.xpath("//a[contains(text(), "+buttonLabel+")]"));
        acceptButton.click();
    }
}