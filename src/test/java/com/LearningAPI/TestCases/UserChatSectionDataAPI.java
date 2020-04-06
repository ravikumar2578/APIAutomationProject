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

public class UserChatSectionDataAPI extends BaseTest {
	static int ExpectedStatusCode = 200;
	static boolean isAlreadyRegistered;
	static String ExpectedResponse_paramvalue;
	static String validationParam;

	@BeforeMethod
	public void init() {
		rep = ExtentManager.getInstance();
		test = rep.startTest("UserChatSectionDataAPI");

	}

	@AfterMethod
	public void quit() {
		rep.endTest(test);
		rep.flush();

	}

	@Test(dataProvider = "getData", priority = 0, testName = "verify UserChatSectionData")

	public void UserChatSectionDataAPI_Test(Hashtable<String, String> data) throws Exception {
		test.log(LogStatus.INFO, "UserChatSectionDataAPI Test started");
		if (!DataUtil.isTestRunnable(xls, "UserChatSectionDataAPI") || data.get("Runmode").equals("N")) {

			test.log(LogStatus.SKIP, "Skipping the test");
		}

		String PostRequest_checkusm = data.get("action") + ":" + Constants.APIKey + ":" + Constants.Salt;

		System.out.println(PostRequest_checkusm);
		String checksum = getMd5(PostRequest_checkusm);
		System.out.println(checksum);

		String Resource = "api/v1.0/chatsectiondata";
		String baseurl = Constants.BaseURL + Resource;
		System.out.println(baseurl);
		RestAssured.baseURI = baseurl;
		test.log(LogStatus.INFO, "API EndPoint is :-  " + Constants.BaseURL);
		test.log(LogStatus.INFO, "API URL IS :-  " + baseurl);
		test.log(LogStatus.INFO, "Method Type :- POST ");
		RequestSpecification Request = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		requestParams.put("action", data.get("action"));
		requestParams.put("user_id", data.get("user_id"));
		requestParams.put("apikey", Constants.APIKey);
		requestParams.put("checksum", checksum);

		JSONObject inner_requestParams = new JSONObject();

		inner_requestParams.put("animation_id", data.get("animation_id"));
		inner_requestParams.put("service_id", data.get("service_id"));
		inner_requestParams.put("seek_time", data.get("seek_time"));
		requestParams.put("seek_details", inner_requestParams.toString());

		System.out.println(requestParams);
		Request.body(requestParams.toString());

		Request.header("accept", "application/json");
		Request.header("Content-Type", "application/json");

		Response response = Request.post(baseurl);

		String responseBody = response.getBody().asString();
		System.out.println("Response body is " + responseBody);
		response.prettyPrint();
		/*
		 * String mobileNumber = data.get("mobile_nu"); String email =
		 * data.get("email_address");
		 * 
		 * isAlreadyRegistered = SQLConnector.connectionsetup(mobileNumber, email); if
		 * (isAlreadyRegistered = true) { ExpectedResponse_paramvalue =
		 * data.get("AlreadyResiteredExpectedResponseValue"); } else {
		 * ExpectedResponse_paramvalue = data.get("ExpectedResponseValue"); }
		 */

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
		Object[][] data = DataUtil.getData(xls, "UserChatSectionDataAPI");
		return data;
	}

}
