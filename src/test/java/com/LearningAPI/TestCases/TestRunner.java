package com.LearningAPI.TestCases;

import org.testng.TestNG;

public class TestRunner {

	public static void main(String[] args) {
		TestNG testNG = new TestNG();

		testNG.setTestClasses(new Class[] { IPLocationAPI.class, AllPackageListAPI.class, ApplyVoucherCodeAPI.class,
				BoardClassListAPI.class, ChangePasswordAPI.class, CheckDeviceAPI.class, CountryListAPI.class,
				GcmRegistrationAPI.class, GetCityDetail.class, GetMCQQuestionListAPI.class,
				GetSubjectByPackageId_API.class, LeaderBoardAPI.class, MySubscriptionsListAPI.class,
				ProfileUpdateAPI.class, QuizQuestionListAPI.class, SearchContentbyKeyword_API.class,
				SubjectWiseProgressReportAPI.class, UploadProfileImagesAPI.class, UserChatSectionDataAPI.class,
				UserLoginAPI.class, UserLogStatusAPI.class, UserRegistration_ForgotPasswordAPI.class,
				UserRegistrationAPI.class, UserSubscriptionList.class, UserSubscriptionStatusAPI.class });

		testNG.run();

	}
}
