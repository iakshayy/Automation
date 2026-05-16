package SeleniumPractices;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ShopifyAutomationTest {

    WebDriver driver;
    WebDriverWait wait;

    // =====================================
    // USER CREDENTIALS
    // =====================================

    String email = "akshayb1406@gmail.com";
    String password = "Akshay@123";

    // =====================================
    // SETUP
    // =====================================

    @BeforeClass
    public void setup() {

        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.get("https://sauce-demo.myshopify.com/");
    }

    // =====================================
    // TEST CASE 1 - SUCCESSFUL LOGIN
    // =====================================

    @Test(priority = 1)
    public void successfulLoginTest() {

        // Open Login Page
        driver.get("https://sauce-demo.myshopify.com/account/login");

        // Enter Email
        WebElement emailField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("customer_email")
                )
        );

        emailField.sendKeys(email);

        // Enter Password
        driver.findElement(By.id("customer_password"))
                .sendKeys(password);

        // Click Sign In
        driver.findElement(
                By.xpath("//input[@value='Sign In']")
        ).click();

        // Validation
        wait.until(ExpectedConditions.urlContains("account"));

        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(currentUrl.contains("account"));

        System.out.println("Login Successful");
    }

    // =====================================
    // TEST CASE 2 - ADD PRODUCT TO CART
    // USER ALREADY LOGGED IN
    // =====================================

    @Test(priority = 2)
    public void addProductToCartTest() {

        // Open Homepage
        driver.get("https://sauce-demo.myshopify.com/");

        // Open First Product
        WebElement firstProduct = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("(//a[contains(@href,'/products/')])[1]")
                )
        );

        firstProduct.click();

        // Click Add To Cart
        WebElement addToCartBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//input[@value='Add to Cart']")
                )
        );

        addToCartBtn.click();

        // Open Cart
        WebElement cartBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(@href,'/cart')]")
                )
        );

        cartBtn.click();

        // Validation
        WebElement cartForm = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//form[contains(@action,'/cart')]")
                )
        );

        Assert.assertTrue(cartForm.isDisplayed());

        System.out.println("Add Product To Cart Test Passed");
    }

    // =====================================
    // TEST CASE 3 - SUCCESSFUL CHECKOUT
    // USER ALREADY LOGGED IN
    // =====================================

    @Test(priority = 3)
    public void successfulCheckoutTest() {

        // Open Cart
        driver.get("https://sauce-demo.myshopify.com/cart");

        // Click Checkout Button
        WebElement checkoutBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//input[@value='Check Out']")
                )
        );

        checkoutBtn.click();

        // Validation
        wait.until(ExpectedConditions.urlContains("checkout"));

        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(currentUrl.contains("checkout"));

        System.out.println("Checkout Test Passed");
    }

    // =====================================
    // CLOSE BROWSER
    // =====================================

    @AfterClass
    public void tearDown() {

        driver.quit();
    }
}