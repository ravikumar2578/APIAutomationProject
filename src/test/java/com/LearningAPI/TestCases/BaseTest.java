package com.LearningAPI.TestCases;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.LearningAPI.Utils.*;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import io.restassured.response.Response;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.path.json.JsonPath;

public class BaseTest {

	/*
	 * @BeforeMethod public void init() { rep = ExtentManager.getInstance(); test =
	 * rep.startTest("StudentTest"); }
	 * 
	 * @AfterMethod public void quit() { rep.endTest(test); rep.flush(); }
	 */

	public ExtentReports rep;
	public static ExtentTest test;
	public static WebDriver driver;
	public static int ExpectedStatusCode = 200;
	public static Xls_Reader xls = new Xls_Reader(Constants.XLS_FILE_PATH);

	public static String getMd5(String input) {
		try {

			// Static getInstance method is called with hashing MD5
			MessageDigest md = MessageDigest.getInstance("MD5");

			// digest() method is called to calculate message digest
			// of an input digest() return array of byte
			byte[] messageDigest = md.digest(input.getBytes());

			// Convert byte array into signum representation
			BigInteger no = new BigInteger(1, messageDigest);

			// Convert message digest into hex value
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static void postExecutionvalidation(Response response, String validationParam,
			String expectedResponse_paramvalue) {
		/*
		 * String log4jConfPath = "/path/to/log4j.properties";
		 * PropertyConfigurator.configure(log4jConfPath);
		 */
		test.log(LogStatus.INFO, response.jsonPath().get().toString());
		String sessionID = response.getSessionId();

		test.log(LogStatus.INFO, "sessionID", sessionID);
		System.out.println("SessionID is :" + sessionID);

		Long SLA = response.getTimeIn(TimeUnit.MILLISECONDS);
		test.log(LogStatus.INFO, "SLA in ms", SLA.toString());

		System.out.println("Response SLA is:" + SLA + "( in ms)");
		String responseBody = response.getBody().asString();
		test.log(LogStatus.INFO, "responseBody", responseBody);
		if (SLA <= 200) {
			System.out.println("SLA is Passed");
		} else {
			System.out.println("SLA is Failed");

		}

		/*
		 * boolean Rsbody = responseBody.contains("status"); Assert.assertEquals(Rsbody,
		 * true);
		 */
		System.out.println("Response Body is =>  " + responseBody);

		JsonPath jsonPathEvaluator = response.jsonPath();
		// String ActualResponse_param = jsonPathEvaluator;
		System.out.println("validationParam" + validationParam);

		String ActualResponse_paramValue = jsonPathEvaluator.get(validationParam).toString();

		System.out.println(ActualResponse_paramValue);
		System.out.println(expectedResponse_paramvalue);
		boolean Stat = ActualResponse_paramValue.equals(expectedResponse_paramvalue);
		System.out.println(Stat);

		Assert.assertTrue(ActualResponse_paramValue.equals(expectedResponse_paramvalue),
				"Validation Response_param is " + ":" + "(" + validationParam + ")" + "," + "Actual Response_paramvalue"
						+ "(" + ActualResponse_paramValue + ")" + "did not match with Expected Response_paramvalue"
						+ "(" + expectedResponse_paramvalue + ")");
		int responsestatus = response.getStatusCode();
		Assert.assertEquals(responsestatus, ExpectedStatusCode, "Correct Statuscode");
		System.out.println("Response status is :" + responsestatus);
		String statusline = response.getStatusLine();
		Assert.assertEquals(statusline, "HTTP/1.1 200 OK", " correct SatusLine");
		System.out.println("Response Status Line is :" + statusline);
		String Contenttype = response.getHeader("content-type");
		Assert.assertEquals(Contenttype, "application/json; charset=utf-8");
		System.out.println(Contenttype);

		String ContentEncoding = response.getHeader("Content-Encoding");

		System.out.println("ContentEncoding :" + ContentEncoding);
		Headers allHeaders = response.headers();

		System.out.println("Response Header Description");
		for (Header header : allHeaders) {
			System.out.println("Key: " + header.getName() + "," + " Value: " + header.getValue());

		}
		// test.log(LogStatus.PASS, "CountryList API Working Fine");

		/*
		 * String log4jConfPath = "/path/to/log4j.properties";
		 * PropertyConfigurator.configure(log4jConfPath);
		 */
	}

	public static void validationwithnestedArray(Response response, String validationParam,
			String expectedResponse_paramvalue, String validationNodeIndex) {
		// test.log(LogStatus.INFO, response.jsonPath().get().toString());
		String sessionID = response.getSessionId();
		String responseBody = response.getBody().asString();
		test.log(LogStatus.INFO, "Response is :-  " + responseBody);

		test.log(LogStatus.INFO, "Validation_paramvalue is :-  " + validationParam);

		test.log(LogStatus.INFO, "ExpectedResponse_paramvalue is :-  " + expectedResponse_paramvalue);

		test.log(LogStatus.INFO, "ValidationNode is :-  " + validationNodeIndex);
		test.log(LogStatus.INFO, "SessionID is :- " + sessionID);
		System.out.println("SessionID is :" + sessionID);

		Long SLA = response.getTimeIn(TimeUnit.MILLISECONDS);
		test.log(LogStatus.INFO, "Response Time in ms  :-" + SLA.toString());

		System.out.println("Response SLA is:" + SLA + "( in ms)");

		// test.log(LogStatus.INFO, "responseBody is :-" + responseBody);
		if (SLA <= 200) {
			System.out.println("SLA is Passed");
		} else {
			System.out.println("SLA is Failed");

		}

		/*
		 * boolean Rsbody = responseBody.contains("status"); Assert.assertEquals(Rsbody,
		 * true);
		 */
		// System.out.println("Response Body is => " + responseBody);

		JsonPath jsonPathEvaluator = response.jsonPath();
		// String ActualResponse_param = jsonPathEvaluator;
		System.out.println("ValidationParam" + validationParam);

		Map<Object,Object> ValidationNode = jsonPathEvaluator.getMap(validationNodeIndex);

		/*
		 * float f=ValidationNode.get(validationParam);//F is the suffix for float
		 * String s=String.valueOf(f);
		 */
		int responsestatus = response.getStatusCode();
		test.log(LogStatus.INFO, "Status code is:- " + responsestatus);
		Assert.assertEquals(responsestatus, ExpectedStatusCode, "Correct Statuscode");
		System.out.println("Response status is :" + responsestatus);
		String statusline = response.getStatusLine();
		test.log(LogStatus.INFO, "Response Status Line is :- " + statusline);
		Assert.assertEquals(statusline, "HTTP/1.1 200 OK", " correct SatusLine");
		System.out.println("Response Status Line is :" + statusline);
		String Contenttype = response.getHeader("content-type");
		test.log(LogStatus.INFO, "Content Type is:-" + Contenttype);
		Assert.assertEquals(Contenttype, "application/json; charset=utf-8");
		System.out.println(Contenttype);

		String ContentEncoding = response.getHeader("Content-Encoding");

		System.out.println("ContentEncoding :" + ContentEncoding);
		test.log(LogStatus.INFO, "Content Encoding is:-" + ContentEncoding);
		Headers allHeaders = response.headers();

		System.out.println("Response Header Description");
		for (Header header : allHeaders) {
			System.out.println("Key: " + header.getName() + "," + " Value: " + header.getValue());

		}
		String ActualResponse_paramValue = ValidationNode.get(validationParam).toString();

		System.out.println("ActualResponse_paramValue : " + ActualResponse_paramValue);
		test.log(LogStatus.INFO, "ActualResponse_paramValue  is :- " + ActualResponse_paramValue);
		System.out.println(expectedResponse_paramvalue);
		boolean Stat = ActualResponse_paramValue.equalsIgnoreCase(expectedResponse_paramvalue);
		System.out.println(Stat);

		if (Stat == true) {
			test.log(LogStatus.PASS, "Actual Response Parameter Value is match with Expected Response Parameter Value");
		}

		else

		{
			test.log(LogStatus.FAIL,
					"Actual Response Parameter Value is Not match with Expected Response Parameter Value");

		}

		Assert.assertTrue(ActualResponse_paramValue.equalsIgnoreCase(expectedResponse_paramvalue),
				"Validation Response_param is " + ":" + "(" + validationParam + ")" + "," + "Actual Response_paramvalue"
						+ "(" + ActualResponse_paramValue + ")" + "did not match with Expected Response_paramvalue"
						+ "(" + expectedResponse_paramvalue + ")");

		// test.log(LogStatus.PASS, "CountryList API Working Fine");

	}

}
