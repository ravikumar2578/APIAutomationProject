package com.LearningAPI.TestCases;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.LearningAPI.Utils.*;
import com.relevantcodes.extentreports.LogStatus;
import com.sun.mail.imap.protocol.Item;
import com.sun.xml.bind.v2.schemagen.xmlschema.List;
import com.thoughtworks.selenium.webdriven.commands.Check;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.concurrent.TimeUnit;

public class AllPackageListAPI extends BaseTest {

	static String ExpectedResponseValue;
	static Object ExpectedStatusCode = 200;

	@BeforeMethod
	public void init() {
		rep = ExtentManager.getInstance();
		test = rep.startTest("AllPackageListAPI");

	}

	@AfterMethod
	public void quit() {
		rep.endTest(test);
		rep.flush();

	}

	@Test(dataProvider = "getData", priority = 0, testName = "Verify Package List API")

	public void AllPackageListAPI_Test(Hashtable<String, String> data) throws Exception {

		String Checksum_Data = Constants.APIKey + ":" + data.get("board_id") + ":" + data.get("class_id") + ":"
				+ data.get("flag_id") + ":" + data.get("user_id") + ":" + data.get("currency") + ":" + Constants.Salt;

		test.log(LogStatus.INFO, "AllPackageListAPI Test started");
		if (!DataUtil.isTestRunnable(xls, "AllPackageListAPI") || data.get("Runmode").equals("N")) {

			test.log(LogStatus.SKIP, "Skipping the test");
		}

		System.out.println(Checksum_Data);
		String checksum = getMd5(Checksum_Data);
		System.out.println(checksum);

		String Resource = "api/v1.0/allpackagelist";

		String Querystring = "?" + "apikey=" + Constants.APIKey + "&checksum=" + checksum + "&board_id="
				+ data.get("board_id") + "&class_id=" + data.get("class_id") + "&flag_id=" + data.get("flag_id")
				+ "&user_id=" + data.get("user_id") + "&currency=" + data.get("currency");
		String Url = Constants.BaseURL + Resource + Querystring;
		System.out.println("API URL IS:" + Url);
		System.out.println("Checksum is ---" + checksum);
		test.log(LogStatus.INFO, "API EndPoint is :-  " + Constants.BaseURL);
		test.log(LogStatus.INFO, "API URL IS :-  " + Url);
		test.log(LogStatus.INFO, "Method Type :- GET ");
		test.log(LogStatus.INFO, "Execute API");
		Response response = RestAssured.get(Url);

		response.prettyPrint();
		String validationParam = data.get("validationParam");
		System.out.println("validation Param is :" + validationParam);
		String ExpectedResponse_paramvalue = data.get("ExpectedResponseValue");
		System.out.println(ExpectedResponse_paramvalue);
		String validatioNodeIndex = data.get("validationNode");
		validationwithnestedArray(response, validationParam, ExpectedResponse_paramvalue, validatioNodeIndex);

	}

	@DataProvider
	public Object[][] getData() {
		Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
		Object[][] data = DataUtil.getData(xls, "AllPackageListAPI");
		return data;
	}

}
