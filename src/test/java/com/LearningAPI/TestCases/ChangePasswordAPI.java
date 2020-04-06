package com.LearningAPI.TestCases;

import org.testng.annotations.Test;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.SkipException;
import com.LearningAPI.Utils.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

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

public class ChangePasswordAPI extends BaseTest {
	static String ExpectedResponseValue;
	static int ExpectedStatusCode = 200;

	@BeforeMethod
	public void init() {
		rep = ExtentManager.getInstance();
		test = rep.startTest("ChangePasswordAPI");

	}

	@AfterMethod
	public void quit() {
		rep.endTest(test);
		rep.flush();

	}

	@Test(dataProvider = "getData", priority = 0, testName = "Verify ChangePassword")

	public void Registration_ChangePasswordAPI_Test(Hashtable<String, String> data) throws Exception {
		test.log(LogStatus.INFO, "ChangePasswordAPI Test started");
		if (!DataUtil.isTestRunnable(xls, "ChangePasswordAPI") || data.get("Runmode").equals("N")) {

			test.log(LogStatus.SKIP, "Skipping the test");

		}

		String PostRequest_checkusm = data.get("action") + ":" + data.get("app_name") + ":" + data.get("user_id") + ":"
				+ data.get("oldpwd") + ":" + data.get("newpwd") + ":" + Constants.APIKey + ":" + Constants.Salt;
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
		requestParams.put("app_name", data.get("app_name"));
		JSONObject inner_requestParams = new JSONObject();
		inner_requestParams.put("user_id", data.get("user_id"));
		inner_requestParams.put("oldpwd", data.get("oldpwd"));
		inner_requestParams.put("newpwd", data.get("newpwd"));
		requestParams.put("change_pwd_details", inner_requestParams);
		JSONObject apikey_DetailsrequestParams = new JSONObject();
		apikey_DetailsrequestParams.put("apikey", Constants.APIKey);
		apikey_DetailsrequestParams.put("checksum", checksum);
		requestParams.put("api_details1", apikey_DetailsrequestParams);
		// Request.body(requestParams.toString());
		// RequestSpecification req= request.body(requestParams.toString());
		Request.header("Accept", "application/json");
		Request.header("Content-Type", "application/json");
		Request.body(requestParams);

		System.out.println("Request_body" + requestParams);
		test.log(LogStatus.INFO, "Request Body is:- " + requestParams);

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
		Object[][] data = DataUtil.getData(xls, "ChangePasswordAPI");
		return data;
	}

}
