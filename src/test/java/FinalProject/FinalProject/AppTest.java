package FinalProject.FinalProject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class AppTest {
    
	WebDriver driver = new EdgeDriver();
     String URL =  "https://www.saucedemo.com/";

    @BeforeTest
    public void MySetup() {
        
        
        driver.manage().window().maximize();
    }

    

    // 1. Homepage Accessibility
    @Test(priority = 1 , enabled = false)
    public void testHomepageAccessibility() throws InterruptedException {
    	driver.get(URL);
        Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo"), "Homepage did not load correctly.");
        Assert.assertTrue(driver.findElement(By.id("user-name")).isDisplayed(), "Login interface is not visible.");
        
        Thread.sleep(3000);
    }

    // 2. Valid User Login
    @Test(priority = 2 , enabled = false)
    public void testValidUserLogin() throws InterruptedException {
    	driver.get(URL);
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        Assert.assertTrue(driver.getCurrentUrl().contains("inventory.html"), "User was not redirected to inventory page.");
        
        Thread.sleep(3000);
    }

    // 3. Invalid User Login
    @Test(priority = 3 , enabled = false)
    public void testInvalidUserLogin() throws InterruptedException {
    	driver.get(URL);
        driver.findElement(By.id("user-name")).sendKeys("invalid_user");
        driver.findElement(By.id("password")).sendKeys("invalid_password");
        driver.findElement(By.id("login-button")).click();

        Assert.assertTrue(driver.findElement(By.cssSelector(".error-message-container")).isDisplayed(), "Error message was not displayed for invalid credentials.");
        
        Thread.sleep(3000);
    }

    // 4. Product Sorting Functionality
    @Test(priority = 4 , enabled = false)
    public void testProductSortingByPriceLowToHigh() throws InterruptedException {
        // Login
    	driver.get(URL);
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Sort by price low to high
        driver.findElement(By.cssSelector("[data-test='product-sort-container']")).click();
        driver.findElement(By.xpath("//option[text()='Price (low to high)']")).click();

        // Verify sorting
        List<WebElement> prices = driver.findElements(By.cssSelector(".inventory_item_price"));
        double previousPrice = 0;
        for (WebElement priceElement : prices) {
            double currentPrice = Double.parseDouble(priceElement.getText().replace("$", ""));
            Assert.assertTrue(currentPrice >= previousPrice, "Products are not sorted correctly by price.");
            previousPrice = currentPrice;
            
            Thread.sleep(500);
        }
    }

    // 5. Add Single Product to Cart
    @Test(priority = 5 , enabled = false)
    public void testAddSingleProductToCart() throws InterruptedException {
        // Login
    	driver.get(URL);
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Add product to cart
        driver.findElement(By.cssSelector(".btn_inventory")).click();

        // Go to cart
        driver.findElement(By.cssSelector(".shopping_cart_link")).click();

        // Verify product is in cart
        Assert.assertTrue(driver.findElement(By.cssSelector(".cart_item")).isDisplayed(), "Product was not added to cart.");
        
        
        Thread.sleep(3000);
    }

    // 6. Add Multiple Products to Cart
    @Test(priority = 6 , enabled = false)
    public void testAddMultipleProductsToCart() throws InterruptedException {
        // Login
    	driver.get(URL);
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Add multiple products
        List<WebElement> addToCartButtons = driver.findElements(By.cssSelector(".btn_inventory"));
        for (int i = 0; i < 3; i++) {
            addToCartButtons.get(i).click();
        }

        // Go to cart
        driver.findElement(By.cssSelector(".shopping_cart_link")).click();

        // Verify count
        List<WebElement> cartItems = driver.findElements(By.cssSelector(".cart_item"));
        Assert.assertEquals(cartItems.size(), 3, "Not all products were added to the cart.");
        
        Thread.sleep(3000);
    }

    // 7. Remove Product from Cart
    @Test(priority = 7 , enabled = false)
    public void testRemoveProductFromCart() throws InterruptedException {
        // Login
    	driver.get(URL);
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Add one product
        driver.findElement(By.cssSelector(".btn_inventory")).click();

        // Go to cart and remove
        driver.findElement(By.cssSelector(".shopping_cart_link")).click();
        driver.findElement(By.cssSelector(".cart_button")).click();

        // Verify cart is empty
        List<WebElement> cartItems = driver.findElements(By.cssSelector(".cart_item"));
        Assert.assertTrue(cartItems.isEmpty(), "Product was not removed from cart.");
        
        Thread.sleep(3000);
    }

    // 8. Logout Functionality
    @Test(priority = 8 , enabled = false)
    public void testLogoutFunctionality() throws InterruptedException {
        // Login
    	driver.get(URL); 
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Logout
        driver.findElement(By.id("react-burger-menu-btn")).click();
        driver.findElement(By.id("logout_sidebar_link")).click();

        // Verify login page
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.saucedemo.com/",  "User was not redirected to login page.");
        
        Thread.sleep(3000);
    }

    // 9. Reset App State
    @Test(priority = 9 , enabled = false)
    public void testResetAppState() throws InterruptedException {
        // Login
    	driver.get(URL);
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Add product
        driver.findElement(By.cssSelector(".btn_inventory")).click();

        // Reset app state
        driver.findElement(By.id("react-burger-menu-btn")).click();
        driver.findElement(By.id("reset_sidebar_link")).click();

        // Go to cart
        driver.findElement(By.cssSelector(".shopping_cart_link")).click();

        // Verify cart is empty
        List<WebElement> cartItems = driver.findElements(By.cssSelector(".cart_item"));
        Assert.assertTrue(cartItems.isEmpty(), "Cart was not reset.");
        
        Thread.sleep(3000);
    }

    // 10. Card Badge Count
    @Test(priority = 10 , enabled = false)
    public void testCartBadgeCount() throws InterruptedException {
        // Login
    	driver.get(URL);
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        // Add multiple items
        List<WebElement> addToCartButtons = driver.findElements(By.cssSelector(".btn_inventory"));
        for (int i = 0; i < 3; i++) {
            addToCartButtons.get(i).click();
        }

        // Get badge count
        int badgeCount = Integer.parseInt(driver.findElement(By.cssSelector(".shopping_cart_badge")).getText());

        // Validate
        Assert.assertEquals(badgeCount, 3, "Cart badge count does not match number of items added.");
        
        Thread.sleep(3000);
    }
    
    @AfterTest
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}