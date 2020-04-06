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

public class UserRegistrationAPI extends BaseTest {
	static int ExpectedStatusCode = 200;
	static boolean isAlreadyRegistered;
	static String ExpectedResponse_paramvalue;
	static String validationParam;

	@BeforeMethod
	public void init() {
		rep = ExtentManager.getInstance();
		test = rep.startTest("UserRegistrationAPI");

	}

	@AfterMethod
	public void quit() {
		rep.endTest(test);
		rep.flush();

	}

	@Test(dataProvider = "getData", priority = 0, testName = "verify UserRegistration")

	public void UserRegistration_Test(Hashtable<String, String> data) throws Exception {
		test.log(LogStatus.INFO, "UserRegistrationAPI Test started");
		if (!DataUtil.isTestRunnable(xls, "UserRegistrationAPI") || data.get("Runmode").equals("N")) {

			test.log(LogStatus.SKIP, "Skipping the test");
		}

		String PostRequest_checkusm = data.get("action") + ":" + data.get("name") + ":" + data.get("email_address")
				+ ":" + data.get("password") + ":" + data.get("access_type") + ":" + data.get("accessId") + ":"
				+ data.get("mobile_nu") + ":" + data.get("device_id") + ":" + Constants.APIKey + ":" + Constants.Salt;

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
		requestParams.put("action", data.get("action"));

		JSONObject inner_requestParams = new JSONObject();

		inner_requestParams.put("name", data.get("name"));
		inner_requestParams.put("email_address", data.get("email_address"));
		inner_requestParams.put("password", data.get("password"));
		inner_requestParams.put("access_type", data.get("access_type"));
		inner_requestParams.put("accessId", data.get("accessId"));
		inner_requestParams.put("mobile_nu", data.get("mobile_nu"));
		inner_requestParams.put("device_id", data.get("device_id"));

		requestParams.put("login_details", inner_requestParams.toString());

		JSONObject apikey_DetailsrequestParams = new JSONObject();
		apikey_DetailsrequestParams.put("apikey", Constants.APIKey);
		apikey_DetailsrequestParams.put("checksum", checksum);
		requestParams.put("api_details", apikey_DetailsrequestParams.toString());

		System.out.println(requestParams);
		Request.body(requestParams.toString());

		Request.header("accept", "application/json");
		Request.header("Content-Type", "application/json");

		Response response = Request.post(baseurl);
		response.prettyPrint();

		String responseBody = response.getBody().asString();
		System.out.println("Response body is " + responseBody);
		String mobileNumber = data.get("mobile_nu");
		String email = data.get("email_address");

		isAlreadyRegistered = SQLConnector.connectionsetup(mobileNumber, email);
		if (isAlreadyRegistered = true) {
			ExpectedResponse_paramvalue = data.get("AlreadyResiteredExpectedResponseValue");
		} else {
			ExpectedResponse_paramvalue = data.get("ExpectedResponseValue");
		}

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
		Object[][] data = DataUtil.getData(xls, "UserRegistrationAPI");
		return data;
	}

}
