package de.lsn.resource;

public enum UIPhase {

	READ_MENU("1"),
	SEND_GET("11"),
	WRITE_MENU("2"),
	SEND_PUT("21"),
	MAIN_MENU("3"),
	RETURN("RETURN"),
	EXIT("EXIT"),
	UNKNOWN("-1");

	String stringValue = "";
	
	private UIPhase(String stringValue) {
		this.stringValue = stringValue;
	}
	
	public String stringValue() {
		return stringValue;
	}
	
	public static UIPhase stringToPhase(String val) {
		UIPhase result = UIPhase.UNKNOWN;
		for (UIPhase phase : UIPhase.values()) {
			if(phase.stringValue().startsWith(val)) {
				result = phase;
				break;
			}
		}
		return result;
	}
	
}
