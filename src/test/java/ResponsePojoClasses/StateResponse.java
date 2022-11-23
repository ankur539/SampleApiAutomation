package ResponsePojoClasses;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StateResponse {
	
	public List<StateInfo> states;
	public int ttl;
	
	public List<StateInfo> getStates() {
		return states;
	}
	
	public void setStates(List<StateInfo> states) {
		this.states = states;
	}
	
	public int getTtl() {
		return ttl;
	}
	
	public void setTtl(int ttl) {
		this.ttl = ttl;
	}

}
