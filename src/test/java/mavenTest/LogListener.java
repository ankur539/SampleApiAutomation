package mavenTest;

import io.qameta.allure.Attachment;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class LogListener implements ITestListener {

    private ByteArrayOutputStream request = new ByteArrayOutputStream();
    private ByteArrayOutputStream response = new ByteArrayOutputStream();
    private PrintStream requestVar = new PrintStream(request, true);
    private PrintStream responseVar = new PrintStream(response, true);

    public void onTestStart(ITestResult result) {
        System.out.println("******** Test execution started : " + result.getMethod().getMethodName() + "********");
    }

    public void onTestSuccess(ITestResult result) {
    	System.out.println("--------onTestSuccessStarted--------");
        logRequest(request);
        logResponse(response);
        System.out.println("--------onTestSuccessFinished--------");
    }

    public void onTestFailure(ITestResult result) {
        logRequest(request);
        logResponse(response);
    }

    public void onTestSkipped(ITestResult result) {

    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    public void onStart(ITestContext context) {
    	System.out.println("--------onStartStarted--------");
        RestAssured.filters(new ResponseLoggingFilter(LogDetail.ALL, responseVar),
                new RequestLoggingFilter(LogDetail.ALL, requestVar));
        System.out.println("--------onStartFinished--------");
    }

    public void onFinish(ITestContext context) {

    }

    @Attachment(value="request")
    public byte[] logRequest(ByteArrayOutputStream stream) {
        return attach(stream);
    }

    @Attachment(value="response")
    public byte[] logResponse(ByteArrayOutputStream stream) {
        return attach(stream);
    }

    public byte[] attach(ByteArrayOutputStream log) {
        byte[] array = log.toByteArray();
        log.reset();
        return array;
    }
}
