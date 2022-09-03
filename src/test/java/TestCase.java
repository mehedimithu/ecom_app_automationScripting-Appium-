import io.appium.java_client.AppiumBy;
import org.example.Main;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.stream.IntStream;

public class TestCase extends Main {
    @Test
    public void tc1() throws InterruptedException {
        driver.findElement(By.id("android:id/text1")).click();

        //Scrolling down
        driver.findElement(new AppiumBy.ByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Bangladesh\"));"));
        driver.findElement(By.xpath("//android.widget.TextView[@text= 'Bangladesh']")).click();

        WebElement usernameField = driver.findElement(By.id("com.androidsample.generalstore:id/nameField"));
        usernameField.sendKeys("Mehedi Hasan");
        // driver.hideKeyboard();

        driver.navigate().back();


        driver.findElement(By.id("com.androidsample.generalstore:id/radioMale")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        Thread.sleep(500);

        //Toast massage headlining
        /* String toastMessage = driver.findElement(By.xpath("(//android.widget.Toast)[1]")).getAttribute("name");
            Assert.assertEquals(toastMessage, "Please enter your name");*/

        String pageTitle = driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title")).getText();
        Assert.assertEquals(pageTitle, "Products");

    }


    @Test
    public void tc2() {
        //Scrolling down
        driver.findElement(new AppiumBy.ByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Jordan 6 Rings\"));"));

        int productCount = driver.findElements(By.id("com.androidsample.generalstore:id/productName")).size();

        IntStream.range(0, productCount).forEach(i -> {
            List<WebElement> productName = driver.findElements(By.id("com.androidsample.generalstore:id/productName"));
            String name = productName.get(i).getText();
            if (name.equalsIgnoreCase("Jordan 6 Rings")) {
                List<WebElement> addToCart = driver.findElements(By.id("com.androidsample.generalstore:id/productAddCart"));
                addToCart.get(i).click();
            }
        });

        driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();

        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title")), "text", "Cart"));

        String checkProductName = driver.findElement(By.id("com.androidsample.generalstore:id/productName")).getText();

        Assert.assertEquals(checkProductName, "Jordan 6 Rings");
    }

}
