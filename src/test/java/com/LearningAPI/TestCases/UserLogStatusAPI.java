package com.LearningAPI.TestCases;

import org.testng.annotations.Test;
import java.util.Hashtable;
//import org.json.simple.JSONObject;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.LearningAPI.Utils.*;
import com.relevantcodes.extentreports.LogStatus;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.mapper.ObjectMapper;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UserLogStatusAPI extends BaseTest {
	static String ExpectedResponse_paramvalue;
	static int ExpectedStatusCode;

	@BeforeMethod
	public void init() {
		rep = ExtentManager.getInstance();
		test = rep.startTest("UserLogStatusAPI");

	}

	@AfterMethod
	public void quit() {
		rep.endTest(test);
		rep.flush();

	}

	@Test(dataProvider = "getData", priority = 0, testName = "verifyLogStatus")

	public void UserLogStatusAPI_Test(Hashtable<String, String> data) throws Exception {

		test.log(LogStatus.INFO, "UserLogStatusAPI Test started");
		if (!DataUtil.isTestRunnable(xls, "UserLogStatusAPI") || data.get("Runmode").equals("N")) {

			test.log(LogStatus.SKIP, "Skipping the test");

		}

		String PostRequest_checkusm = data.get("log_type") + ":" + data.get("app_version") + ":"
				+ data.get("reports_data") + ":" + data.get("user_id") + ":" + data.get("platform_type") + ":"
				+ data.get("latitude") + ":" + data.get("mode") + ":" + data.get("longitude") + ":"
				+ data.get("app_type") + Constants.APIKey + ":" + Constants.Salt;

		System.out.println(PostRequest_checkusm);
		String checksum = getMd5(PostRequest_checkusm);
		System.out.println(checksum);

		String Resource = "api/v1.2/tabletregistration";
		String baseurl = Constants.BaseURL + Resource;
		System.out.println(baseurl);
		RestAssured.baseURI = baseurl;
		test.log(LogStatus.INFO, "API EndPoint is :-  " + Constants.BaseURL);
		test.log(LogStatus.INFO, "API URL IS :-  " + baseurl);
		test.log(LogStatus.INFO, "Method Type :- POST ");
		RequestSpecification Request = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		requestParams.put("log_type", data.get("log_type"));
		requestParams.put("app_version", data.get("app_version"));
		// requestParams.put("reports_data", data.get("reports_data"));
		requestParams.put("user_id", data.get("user_id"));
		requestParams.put("app_type", data.get("app_type"));
		requestParams.put("platform_type", data.get("platform_type"));
		requestParams.put("apikey", Constants.APIKey);
		requestParams.put("checksum", checksum);
		JSONObject inner_DetailsrequestParams = new JSONObject();
		inner_DetailsrequestParams.put("mode", data.get("mode"));
		inner_DetailsrequestParams.put("latitude", data.get("latitude"));
		inner_DetailsrequestParams.put("longitude", data.get("longitude"));
		requestParams.put("reports_data", inner_DetailsrequestParams.toString());

		System.out.println(requestParams);
		Request.body(requestParams.toString());

		Request.header("accept", "application/json");
		Request.header("Content-Type", "application/json");

		Response response = Request.post(baseurl);

		System.out.println(response);
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
		Object[][] data = DataUtil.getData(xls, "UserLogStatusAPI");
		return data;
	}

}
