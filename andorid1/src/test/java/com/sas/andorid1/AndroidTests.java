package com.sas.andorid1;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class AndroidTests {

	private final String APPIUM_SERVER_URL = "http://127.0.0.1:4723/wd/hub";
	private AndroidDriver driver;
	private static final String RESOURCE_ID_INPUT_CONTROLS = "com.amazonaws.devicefarm.android.referenceapp:id/drawer_input_controls";
	
	@Before
	public void setup() {
		URL url;
		try {
			url = new URL(APPIUM_SERVER_URL);
			DesiredCapabilities capabilities = new DesiredCapabilities();
		    capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		    capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		    capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "7.1.1");
		    capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "appium_demo");
		    capabilities.setCapability(MobileCapabilityType.UDID, "emulator-5554");
		    capabilities.setCapability(MobileCapabilityType.APP, "C:\\Users\\User\\Downloads\\aws-device-farm-sample-app-for-android-master\\app\\build\\outputs\\apk\\app-debug.apk");
		    capabilities.setCapability(MobileCapabilityType.ORIENTATION, "PORTRAIT");
		    capabilities.setCapability(MobileCapabilityType.NO_RESET, false);

		    driver = new AndroidDriver<MobileElement>(url, capabilities);

		    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			
		}

	    
	}
	
	
	@Test
    public void testAppLaunchOnHomePage() {
        MobileElement headline = (MobileElement) driver.findElementById("com.amazonaws.devicefarm.android.referenceapp:id/homepage_headline");
        MobileElement subheader = (MobileElement) driver.findElementById("com.amazonaws.devicefarm.android.referenceapp:id/homepage_subheadline");

        Assert.assertTrue("Headline not match!", headline.getText().equals("AWS Device Farm Sample App for Android"));
        Assert.assertTrue("Subheader not match!", subheader.getText().equals("version 1"));
    }
	
	
	private void navigateToCategory(String categoryId) throws InterruptedException {
	    MobileElement toggle = (MobileElement) driver.findElementByAccessibilityId("ReferenceApp");
	    toggle.click();

	    MobileElement categoryItem = (MobileElement) driver.findElementById(categoryId);
	    categoryItem.click();

	    TimeUnit.SECONDS.sleep(1);
	}
	
	@Test
	public void testEditingText() throws InterruptedException {
	    navigateToCategory(RESOURCE_ID_INPUT_CONTROLS);

	    final String TEXT_TO_ENTER = "Hello Android";
	    MobileElement textBox = (MobileElement) driver.findElementByXPath("//android.widget.EditText[@resource-id='com.amazonaws.devicefarm.android.referenceapp:id/input_edit_text']");
	    textBox.sendKeys(TEXT_TO_ENTER);
	    Assert.assertEquals(TEXT_TO_ENTER, textBox.getText());

	    textBox.clear();
	    Assert.assertEquals("Enter Text", textBox.getText());
	}

	@After
	public void teardown() {
		if (driver != null) {
			driver.quit();
		}
	}

}
