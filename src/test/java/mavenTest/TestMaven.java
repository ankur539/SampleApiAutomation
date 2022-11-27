package mavenTest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.opentelemetry.exporter.logging.SystemOutLogExporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import ResponsePojoClasses.RootSession;
import ResponsePojoClasses.SeatInfo;
import ResponsePojoClasses.SessionsInfo;
import ResponsePojoClasses.StateInfo;
import ResponsePojoClasses.StateResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public class TestMaven {
	
	WebDriver driver;
	
	@BeforeSuite
	public void beforeSuite() {
		RestAssured.baseURI = "https://cdn-api.co-vin.in/api";
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//chromedriver.exe");
		System.out.println(System.getProperty("user.dir"));
		driver = new ChromeDriver();
	}
	
	
	@Test(enabled = true)
	public void openGoogle(Method m) {
		System.out.println("--------------"+m.getName()+"--------------");	
		
		driver.get("https://www.google.co.in");
		driver.manage().window().maximize();
		System.out.println("Title of the page opened : "+driver.getTitle());
		driver.quit();
		System.out.println("Goggle is successfully opened and closed");
	}
	
	@Test
	public void stateDetails(Method m) {
		System.out.println("--------------"+m.getName()+"--------------");	
		
		Map<String, Integer> states = getNoOfStates();
		
		for(Map.Entry<String, Integer> me : states.entrySet()) {
			System.out.println("StateName is : " + me.getKey());
			System.out.println("Respective StateId is : "+me.getValue());
		}
	}
	
	@Test
	public void availableSeatsDetails(Method m) {
		System.out.println("--------------"+m.getName()+"--------------");	
		
		int num = getVaccineData("512","21-11-2022","SJPR CITY HOSPITAL NEEMRANA");
		System.out.println("Total Seats : " + num);	
	}
	
	
	public Map<String,Integer> getNoOfStates() {
		
		Map<String,Integer> statesInfo = new HashMap<String,Integer>();
		
		StateResponse res = RestAssured.given().
				get("/v2/admin/location/states")
				.then().assertThat().statusCode(200)
				.extract().response().as(StateResponse.class);
		
		List<StateInfo> totalStates = res.getStates();
		
		for(int i=0;i<totalStates.size();i++) {
			statesInfo.put(totalStates.get(i).getStateName(),totalStates.get(i).getStateId());
		}
		
		
		return statesInfo;	
		
	}
	
	public int getVaccineData(String distId, String reqDate, String hospitalName) {
		
		RootSession response = RestAssured.given()
				.contentType(ContentType.JSON).with().queryParam("district_id", distId).queryParam("date", reqDate)
				.when().get("/v2/appointment/sessions/public/findByDistrict")
				.then().assertThat().statusCode(200)
				.extract().response().as(RootSession.class);
				
		List<SessionsInfo> sessions = response.getSessions();
		System.out.println("Total Sessions Available for Given Input : " + sessions.size());
		int totalSeats=0;
		List<SeatInfo> slots;
		
		for(int i=0;i<sessions.size();i++) {
			
			if(sessions.get(i).getName().equals(hospitalName) && sessions.get(i).getVaccine().equals("COVISHIELD")) {
				slots = sessions.get(i).getSlots();
				System.out.println("Available Slots in the session "+ (i+1) + " are : " + slots.size());
				for(int j=0;j<slots.size();j++) {
					totalSeats = totalSeats + slots.get(j).getSeats();
					System.out.println("TimeSlot : "+slots.get(j).getTime());
					System.out.println("Seats Available : "+slots.get(j).getSeats());	
				}
			}			
		}
			
		return totalSeats;
			
	}
	
	
	
	

}
