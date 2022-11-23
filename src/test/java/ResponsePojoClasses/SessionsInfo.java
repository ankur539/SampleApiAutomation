package ResponsePojoClasses;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SessionsInfo {
	
	public List<SeatInfo> slots;
	public String name;
	public String vaccine;
	
	public List<SeatInfo> getSlots() {
		return slots;
	}
	
	public String getName() {
		return name;
	}
	
	public String getVaccine() {
		return vaccine;
	}

}
