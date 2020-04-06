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

public class GcmRegistrationAPI extends BaseTest {
	static String ExpectedStatusCode;
	static String ExpectedResponseValue;

	@BeforeMethod
	public void init() {
		rep = ExtentManager.getInstance();
		test = rep.startTest("GcmRegistrationAPI");

	}

	@AfterMethod
	public void quit() {
		rep.endTest(test);
		rep.flush();

	}

	@Test(dataProvider = "getData", priority = 0, testName = "verifyGcmRegistartion API")

	public void GcmRegistrationAPI_Test(Hashtable<String, String> data) throws Exception {

		test.log(LogStatus.INFO, "GcmRegistrationAPI Test started");
		if (!DataUtil.isTestRunnable(xls, "GcmRegistrationAPI") || data.get("Runmode").equals("N")) {

			test.log(LogStatus.SKIP, "Skipping the test");

		}

		String PostRequest_checkusm = data.get("reg_id") + ":" + data.get("os") + ":" + data.get("os_version") + ":"
				+ data.get("app_version") + ":" + data.get("type") + ":" + Constants.APIKey + ":" + Constants.Salt;

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
		JSONObject outerrequestParams = new JSONObject();
		outerrequestParams.put("", "");
		JSONObject requestParams = new JSONObject();
		requestParams.put("os", data.get("os"));
		requestParams.put("os_version", data.get("os_version"));
		requestParams.put("app_version", data.get("app_version"));
		requestParams.put("type", data.get("type"));
		requestParams.put("reg_details", requestParams);
		JSONObject apikey_DetailsrequestParams = new JSONObject();
		apikey_DetailsrequestParams.put("apikey", Constants.APIKey);
		apikey_DetailsrequestParams.put("checksum", checksum);
		requestParams.put("api_details", apikey_DetailsrequestParams);
		outerrequestParams.put("", " ");
		System.out.println(requestParams);
		Request.body(requestParams.toString());

		Request.header("accept", "application/json");
		Request.header("Content-Type", "application/json");
		Request.body(requestParams);

		System.out.println("Request_body" + requestParams);

		Response response = Request.post(baseurl);
		System.out.println(baseurl);
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
		Object[][] data = DataUtil.getData(xls, "GcmRegistrationAPI");
		return data;
	}

}
