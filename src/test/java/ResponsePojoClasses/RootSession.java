package ResponsePojoClasses;

import java.util.List;


public class RootSession {

	public List<SessionsInfo> sessions;
	
	public List<SessionsInfo> getSessions() {
		return sessions;
	}
	
	public void setSessions(List<SessionsInfo> sessions) {
		this.sessions=sessions;
	}
}
