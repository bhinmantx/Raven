package raven.game;

public class noTeamException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4L;
	public noTeamException() {super();}
	public noTeamException(String s) {super(s);
	new ExceptionExitPopup("No Team Detected", s);
	}

	
}
