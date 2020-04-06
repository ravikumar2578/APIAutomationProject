package com.LearningAPI.TestCases;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.LearningAPI.Utils.*;
import org.openqa.selenium.chrome.ChromeDriver;



public class Checksum_genrate extends BaseTest {

	public String Checksum_genrator(String param1) throws Exception {

		System.setProperty("webdriver.chrome.driver", Constants.CHROME_PATH);
		driver = new ChromeDriver();
		driver.get("https://www.md5hashgenerator.com/");
		String checksumdata = param1 + ":" + Constants.APIKey + ":" + Constants.Salt;
		System.out.println(checksumdata);
		driver.findElement(By.xpath("//*[@id='string']")).sendKeys(checksumdata);
		driver.findElement(By.xpath("//*[@id='cap']/button")).click();
		String checksum = driver.findElement(By.xpath("//*[@id='cap']//strong")).getText();
		driver.close();
		return checksum;

	}

	public String Checksum_genrator() {
		System.setProperty("webdriver.chrome.driver", Constants.CHROME_PATH);
		driver = new ChromeDriver();
		driver.get("https://www.md5hashgenerator.com/");
		String checksumdata = Constants.APIKey + ":" + Constants.Salt;
		System.out.println(checksumdata);
		driver.findElement(By.xpath("//*[@id='string']")).sendKeys(checksumdata);
		driver.findElement(By.xpath("//*[@id='cap']/button")).click();
		String checksum = driver.findElement(By.xpath("//*[@id='cap']//strong")).getText();
		driver.close();
		return checksum;
	}
}