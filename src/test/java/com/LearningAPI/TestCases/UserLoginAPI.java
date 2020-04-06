package com.LearningAPI.TestCases;

import org.testng.annotations.Test;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.jsoup.helper.HttpConnection.Request;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.LearningAPI.Utils.*;
import com.google.protobuf.Method;
import com.relevantcodes.extentreports.LogStatus;
import com.sun.xml.xsom.impl.scd.Iterators.Map;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.sf.json.JSONObject;

public class UserLoginAPI extends BaseTest {
	static String ExpectedStatusCode;
	static String ExpectedResponseValue;

	@BeforeMethod
	public void init() {
		rep = ExtentManager.getInstance();
		test = rep.startTest("UserLoginAPI");

	}

	@AfterMethod
	public void quit() {
		rep.endTest(test);
		rep.flush();

	}

	@Test(dataProvider = "getData", priority = 0, testName = "Verify LogStatusAPI")

	public void UserLoginAPI_Test(Hashtable<String, String> data) throws Exception {

		test.log(LogStatus.INFO, "UserLoginAPI_Test started");
		if (!DataUtil.isTestRunnable(xls, "UserLoginAPI") || data.get("Runmode").equals("N")) {

			test.log(LogStatus.SKIP, "Skipping the test");

		}

		String PostRequest_checkusm = data.get("action") + ":" + data.get("email_address") + ":" + data.get("password")
				+ ":" + data.get("access_type") + ":" + data.get("accessId") + ":" + data.get("latitude") + ":"
				+ data.get("longitude") + ":" + Constants.APIKey + ":" + Constants.Salt;

		System.out.println(PostRequest_checkusm);
		String checksum = getMd5(PostRequest_checkusm);
		System.out.println(checksum);

		String Resource = "api/v1.2/tabletregistration";
		String baseurl = Constants.BaseURL + Resource;
		System.out.println(baseurl);
		test.log(LogStatus.INFO, "API EndPoint is :-  " + Constants.BaseURL);
		test.log(LogStatus.INFO, "API URL IS :-  " + baseurl);
		test.log(LogStatus.INFO, "Method Type :- POST ");
		RequestSpecification Request = RestAssured.given();
		JSONObject requestParams = new JSONObject();
		requestParams.put("action", data.get("action"));
		System.out.println(data.get("action"));
		JSONObject inner_requestParams = new JSONObject();
		inner_requestParams.put("email_address", data.get("email_address"));
		inner_requestParams.put("password", data.get("password"));
		inner_requestParams.put("access_type", data.get("access_type"));
		inner_requestParams.put("accessId", data.get("accessId"));
		inner_requestParams.put("latitude", data.get("latitude"));
		inner_requestParams.put("longitude", data.get("longitude"));
		requestParams.put("login_details", inner_requestParams);
		JSONObject apikey_DetailsrequestParams = new JSONObject();
		apikey_DetailsrequestParams.put("apikey", Constants.APIKey);
		apikey_DetailsrequestParams.put("checksum", checksum);
		requestParams.put("api_details", apikey_DetailsrequestParams);
		System.out.println(requestParams);
		Request.body(requestParams.toString());

		Request.header("Accept", "application/json");
		Request.header("Content-Type", "application/json");
		Request.body(requestParams);

		System.out.println("Request_body" + requestParams);

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
		Object[][] data = DataUtil.getData(xls, "UserLoginAPI");
		return data;
	}

}
