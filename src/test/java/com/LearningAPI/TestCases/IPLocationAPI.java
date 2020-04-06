package com.LearningAPI.TestCases;

import java.util.Hashtable;
import java.util.concurrent.TimeUnit;
//import org.openqa.selenium.By;
//import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
//import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
//import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.LearningAPI.Utils.*;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class IPLocationAPI extends BaseTest {
	static Response response;

	String IPREFERENCE;

	@BeforeMethod
	public void init() {
		rep = ExtentManager.getInstance();
		test = rep.startTest("IPLocationAPI");

	}

	@AfterMethod
	public void quit() {
		rep.endTest(test);
		rep.flush();

	}

	@Test(dataProvider = "getData", priority = 1, testName = "verifyIPLocation")

	public void IPLocationAPI_Test(Hashtable<String, String> data) throws Exception {

		test.log(LogStatus.INFO, "IPLocationAPI Test started");
		if (!DataUtil.isTestRunnable(xls, "IPLocationAPI") || data.get("Runmode").equals("N")) {

			test.log(LogStatus.SKIP, "Skipping the test");

		}

		String Checksumdata = data.get("IPREFERENCE") + ":" + Constants.APIKey + ":" + Constants.Salt;
		System.out.println("checksum string is--" + Checksumdata);
		String checksum = getMd5(Checksumdata);
		String Resource = "api/v1.0/iplocationapi";
		String Querystring = "?IPREFERENCE=" + data.get("IPREFERENCE") + "&apikey=" + Constants.APIKey + "&checksum="
				+ checksum;

		String Url = Constants.BaseURL + Resource + Querystring;

		System.out.println("API URL IS :-  " + Url);

		test.log(LogStatus.INFO, "API EndPoint is :-  " + Constants.BaseURL);
		test.log(LogStatus.INFO, "API URL IS :-  " + Url);
		test.log(LogStatus.INFO, "Method Type :- GET ");

		System.out.println("Checksum is ---" + checksum);

		test.log(LogStatus.INFO, "Execute API");

		Response response = RestAssured.get(Url);
		// String responseBody = response.getBody().asString();
		// test.log(LogStatus.INFO, "response is :-" + responseBody);

		response.prettyPrint();
		String validationParam = data.get("validationParam");
		System.out.println("validation Param is :" + validationParam);
		// test.log(LogStatus.INFO, "validation_paramvalue is :- " + validationParam);
		String ExpectedResponse_paramvalue = data.get("ExpectedResponseValue");
		System.out.println(ExpectedResponse_paramvalue);
		// test.log(LogStatus.INFO, "ExpectedResponse_paramvalue is :- " +
		// ExpectedResponse_paramvalue);

		String validatioNodeIndex = data.get("validationNode");
		// test.log(LogStatus.INFO, "validationNode is :- " + validatioNodeIndex);
		// postExecutionvalidation(response, validationParam,
		// ExpectedResponse_paramvalue);
		validationwithnestedArray(response, validationParam, ExpectedResponse_paramvalue, validatioNodeIndex);
	}

	@DataProvider
	public Object[][] getData() {
		Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);
		Object[][] data = DataUtil.getData(xls, "IPLocationAPI");
		return data;
	}

}
