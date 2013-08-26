package de.lsn.resource;

public enum UIPhase {

	EXIT(0),
	READ_MENU(1),
	WRITE_MENU(2),
	MAIN_MENU(3),
	UNKNOWN(-1);

	int val = 0;
	
	private UIPhase(int val) {
		this.val = val;
	}
	
	public int val() {
		return val;
	}
	
	public static UIPhase stringToPhase(int val) {
		UIPhase result = UIPhase.UNKNOWN;
		for (UIPhase phase : UIPhase.values()) {
			if(phase.val() == val) {
				result = phase;
				break;
			}
		}
		return result;
	}
	
}
