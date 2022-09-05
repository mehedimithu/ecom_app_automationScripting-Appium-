package org.example;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.openqa.selenium.DeviceRotation;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class Main {
    public AppiumDriver driver;
    //Appium server start
    public AppiumDriverLocalService service;
    public boolean canScrollMore;
    public DeviceRotation deviceRotation;
    public Activity activity;
    public WebDriverWait wait;

    @BeforeClass
    public void appiumConfigure() throws MalformedURLException {
        /* service = new AppiumServiceBuilder().withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js")).withIPAddress("127.0.0.1").usingPort(4723).build();
        service.start();*/

        DesiredCapabilities caps = new DesiredCapabilities();


        URL uri = new URL("http://127.0.0.1:4723/wd/hub/");
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "12");
        caps.setCapability("deviceName", "sdk_gphone64_arm64");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("app", System.getProperty("user.dir") + "/resources/General_Store.apk");

       /* caps.setCapability("appPackage", "io.appium.android.apis");
        caps.setCapability("appActivity", ".ApiDemos");*/

        driver = new AppiumDriver(uri, caps);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

    }

    public Double  formattedAmount(String amount){
        Double totalPrice = Double.parseDouble(amount.substring(1));
        return totalPrice;
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
        //service.stop();
    }

}