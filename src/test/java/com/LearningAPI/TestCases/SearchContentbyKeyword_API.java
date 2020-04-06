package com.LearningAPI.TestCases;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
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
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class SearchContentbyKeyword_API extends BaseTest {

	static String ExpectedResponseValue;
	static Object ExpectedStatusCode = 200;

	@BeforeMethod
	public void init() {
		rep = ExtentManager.getInstance();
		test = rep.startTest("SearchContentbyKeyword_API");

	}

	@AfterMethod
	public void quit() {
		rep.endTest(test);
		rep.flush();

	}

	@Test(dataProvider = "getData", priority = 0, testName = "Verify Content by Keyword")

	public void SearchContentbyKeyword_API_Test(Hashtable<String, String> data) throws Exception {

		String Checksum_Data = data.get("action") + ":" + data.get("text") + ":" + data.get("board_id") + ":"
				+ data.get("class_id") + ":" + data.get("subject_id") + ":" + data.get("chapter_id") + ":"
				+ data.get("content_id") + ":" + data.get("service_id") + ":" + data.get("user_id") + ":"
				+ data.get("type") + ":" + data.get("start") + ":" + data.get("offset") + ":" + Constants.APIKey + ":"
				+ Constants.Salt;

		test.log(LogStatus.INFO, "SearchContentbyKeyword_API Test started");
		if (!DataUtil.isTestRunnable(xls, "SearchContentbyKeyword_API") || data.get("Runmode").equals("N")) {

			test.log(LogStatus.SKIP, "Skipping the test");
		}

		System.out.println(Checksum_Data);
		String checksum = getMd5(Checksum_Data);
		System.out.println(checksum);

		String Resource = "api/v1.0/search";

		String Querystring = "?" + "action=" + data.get("action") + "&text=" + data.get("text") + "&board_id="
				+ data.get("board_id") + "&class_id=" + data.get("class_id") + "&subject_id=" + data.get("subject_id")
				+ "&chapter_id=" + data.get("chapter_id") + "&content_id=" + data.get("content_id") + "&service_id="
				+ data.get("service_id") + "&user_id=" + data.get("user_id") + "&type=" + data.get("type") + "&start="
				+ data.get("start") + "&offset=" + data.get("offset") + "&apikey=" + Constants.APIKey + "&checksum="
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
		Object[][] data = DataUtil.getData(xls, "SearchContentbyKeyword_API");
		return data;
	}

}
