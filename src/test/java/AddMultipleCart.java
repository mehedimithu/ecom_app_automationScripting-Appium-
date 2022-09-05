import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.SupportsContextSwitching;
import org.example.Main;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class AddMultipleCart extends Main {

    @Test
    public void addTwoCart() throws InterruptedException {

        driver.findElement(By.id("android:id/text1")).click();

        //Scrolling down
        driver.findElement(new AppiumBy.ByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Bangladesh\"));"));
        driver.findElement(By.xpath("//android.widget.TextView[@text= 'Bangladesh']")).click();

        WebElement usernameField = driver.findElement(By.id("com.androidsample.generalstore:id/nameField"));
        usernameField.sendKeys("Mehedi Hasan");

        driver.navigate().back();

        driver.findElement(By.id("com.androidsample.generalstore:id/radioMale")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        //  activity = new Activity("com.androidsample.generalstore", "com.androidsample.generalstore.AllProductsActivity");

        //Scrolling down
        driver.findElement(new AppiumBy.ByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Jordan 6 Rings\"));"));

        driver.findElements(By.xpath("//android.widget.TextView[@text= 'ADD TO CART']")).get(0).click();
        driver.findElements(By.xpath("//android.widget.TextView[@text= 'ADD TO CART']")).get(0).click();

        driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title")), "text", "Cart"));

        String productItem1 = driver.findElement(By.xpath("//android.widget.TextView[@text='Jordan 6 Rings']")).getText();
        String productItem2 = driver.findElement(By.xpath("//android.widget.TextView[@text='Jordan Lift Off']")).getText();

        Assert.assertEquals(productItem1, "Jordan 6 Rings");
        Assert.assertEquals(productItem2, "Jordan Lift Off");

        List<WebElement> productPrice = driver.findElements(By.id("com.androidsample.generalstore:id/productPrice"));
        int count = productPrice.size();

        double purchaseAmount = 0;

        for (int i = 0; i < count; i++) {
            String amounts = productPrice.get(i).getText();
            Double totalPrice = formattedAmount(amounts);
            purchaseAmount = purchaseAmount + totalPrice;

        }
        String subTotal = driver.findElement(By.id("com.androidsample.generalstore:id/totalAmountLbl")).getText();

        Double displayAmount = formattedAmount(subTotal);
        System.out.println(purchaseAmount);
        System.out.println(displayAmount);
        Assert.assertEquals(purchaseAmount, displayAmount);

        driver.findElement(AppiumBy.className("android.widget.CheckBox")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();


    }

    @Test
    public void hybridApp() throws InterruptedException, MalformedURLException {
        Thread.sleep(6000);
        UiAutomator2Options options = new UiAutomator2Options().setAutoGrantPermissions(true);

        //ChromedriverExecutable
        options.setChromedriverExecutable(System.getProperty("user.dir") + "/resources/chromedriver");

        AppiumDriver drivers = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), options);

        Set<String> contextNames = ((SupportsContextSwitching) drivers).getContextHandles();
        for (String contextName : contextNames) {
            System.out.println("Context  " + contextName);
        }

        ((SupportsContextSwitching) drivers).context("WEBVIEW_com.androidsample.generalstore");

        drivers.findElement(By.name("q")).sendKeys("Fruits");
        drivers.findElement(By.name("q")).sendKeys(Keys.ENTER);
        drivers.navigate().back();
        ((SupportsContextSwitching) drivers).context("NATIVE_APP");

    }

}
