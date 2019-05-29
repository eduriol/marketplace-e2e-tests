package stepdefinitions;

import clients.BuyerManagerClient;
import clients.ItemManagerClient;
import clients.PurchaseManagerClient;
import com.google.gson.Gson;
import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entities.*;
import org.awaitility.core.ConditionTimeoutException;
import pages.BasePage;
import pages.BuyersPage;
import pages.HomePage;
import pages.ItemsPage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertTrue;


public class Steps {

    private final String homeUrl = "http://localhost:8000";
    private final String buyersUrl = "http://localhost:8000/#!/buyers";
    private final String itemsUrl = "http://localhost:8000/#!/items";
    private HomePage homePage;
    private BuyersPage buyersPage;
    private ItemsPage itemsPage;
    private Buyer newBuyer;
    private Item newItem;
    private PurchaseOrder newPurchaseOrder;

    @Given("^I enter the marketplace backoffice$")
    public void i_enter_the_marketplace_backoffice() {
        homePage = new HomePage();
        homePage.load(homeUrl);
    }

    @When("^I go to the buyers details$")
    public void i_go_to_the_buyers_details() {
        homePage.moveTo(buyersUrl);
        buyersPage = new BuyersPage();
    }

    @Then("^I see the list of buyers with their details$")
    public void i_see_the_list_of_buyers_with_their_details() {
        assertTrue(buyersPage.hasElements());
    }

    @When("^I go to the items details$")
    public void i_go_to_the_items_details() {
        homePage.moveTo(itemsUrl);
        itemsPage = new ItemsPage();
    }

    @Then("^I see the list of items with their details$")
    public void i_see_the_list_of_items_with_their_details() {
        assertTrue(itemsPage.hasElements());
    }

    @When("^I click on \"([^\"]*)\"$")
    public void i_click_on(String linkText) {
        buyersPage.clickOnLink(linkText);
        itemsPage = new ItemsPage();
    }

    @Given("a new buyer comes to the store")
    public void a_new_buyer_comes_to_the_store() throws FileNotFoundException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("src/test/resources/newBuyer.json"));
        Gson gson = new Gson();
        newBuyer = gson.fromJson(bufferedReader, Buyer.class);
        BuyerManagerClient buyerManagerClient = new BuyerManagerClient();
        buyerManagerClient.addBuyer(newBuyer);
    }


    @Given("^we have the following item in the marketplace:$")
    public void we_have_the_following_item_in_the_marketplace(DataTable dt) {
        List<Map<String, String>> newItemData = dt.asMaps(String.class, String.class);
        newItem = new Item();
        newItem.setId(Long.parseLong(newItemData.get(0).get("id")));
        newItem.setTitle(newItemData.get(0).get("title"));
        Store store = new Store();
        store.setName(newItemData.get(0).get("store name"));
        newItem.setStore(store);
        ItemManagerClient itemManagerClient = new ItemManagerClient();
        itemManagerClient.addItem(newItem);
    }

    @When("^(?:he|she) purchases the item$")
    public void he_purchases_the_item() {
        newPurchaseOrder = new PurchaseOrder();
        newPurchaseOrder.setId(1L);
        newPurchaseOrder.setBuyerId(newBuyer.getId());
        newPurchaseOrder.setItemId(newItem.getId());
        PurchaseManagerClient purchaseManagerClient = new PurchaseManagerClient();
        purchaseManagerClient.addPurchase(newPurchaseOrder);
    }

    @Then("^the purchase is registered in the system$")
    public void the_purchase_is_registered_in_the_system() throws ConditionTimeoutException {
        await()
                .atMost(60, TimeUnit.SECONDS)
                .with()
                .pollInterval(1, TimeUnit.SECONDS)
                .until(() -> isNewPurchaseCommitted());

        assertTrue(isNewPurchaseCommitted());
    }

    private boolean isNewPurchaseCommitted() {
        BuyerManagerClient buyerManagerClient = new BuyerManagerClient();
        Purchase[] ordersFromBuyer = buyerManagerClient.getOrdersFromBuyer(newBuyer.getId());
        for (Purchase purchase : ordersFromBuyer) {
            if (purchase.getItemTitle().equals(newItem.getTitle())) {
                return true;
            }
        }
        return false;
    }

    @After
    public void tearDown() {
        if (BasePage.driver != null) {
            BasePage.driver.quit();
        }
    }
}
