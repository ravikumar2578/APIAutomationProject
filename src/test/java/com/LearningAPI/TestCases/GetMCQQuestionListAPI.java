package com.LearningAPI.TestCases;

import java.util.Hashtable;
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

import com.LearningAPI.Utils.*;
import com.relevantcodes.extentreports.LogStatus;
import com.thoughtworks.selenium.webdriven.commands.Check;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetMCQQuestionListAPI extends BaseTest {

	static String ExpectedResponseValue;
	static Object ExpectedStatusCode = 200;

	@BeforeMethod
	public void init() {
		rep = ExtentManager.getInstance();
		test = rep.startTest("GetMCQQuestionListAPI");

	}

	@AfterMethod
	public void quit() {
		rep.endTest(test);
		rep.flush();

	}

	@Test(dataProvider = "getData", priority = 0, testName = "Verify MCQ Question List")

	public void GetMCQQuestionListAPI_Test(Hashtable<String, String> data) throws Exception {

		String Checksum_Data = data.get("board_id") + ":" + data.get("user_id") + ":" + data.get("session_id") + ":"
				+ data.get("container_id") + ":" + data.get("service_id") + ":" + data.get("level") + ":"
				+ data.get("test") + ":" + data.get("test_service_type") + ":" + Constants.APIKey + ":"
				+ Constants.Salt;

		test.log(LogStatus.INFO, "GetMCQQuestionListAPI Test started");
		if (!DataUtil.isTestRunnable(xls, "GetMCQQuestionListAPI") || data.get("Runmode").equals("N")) {

			test.log(LogStatus.SKIP, "Skipping the test");
		}

		System.out.println(Checksum_Data);
		String checksum = getMd5(Checksum_Data);
		System.out.println(checksum);

		String Resource = "api/v1.0/mcqquestionlist";

		String Querystring = "?" + "board_id=" + data.get("board_id") + "&user_id=" + data.get("user_id")
				+ "&session_id=" + data.get("session_id") + "&container_id=" + data.get("container_id") + "&service_id="
				+ data.get("service_id") + "&level=" + data.get("level") + "&test=" + data.get("test")
				+ "&test_service_type=" + data.get("test_service_type") + "&apikey=" + Constants.APIKey + "&checksum="
				+ checksum;
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
		Object[][] data = DataUtil.getData(xls, "GetMCQQuestionListAPI");
		return data;
	}

}
