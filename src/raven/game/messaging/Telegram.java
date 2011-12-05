package raven.game.messaging;

import raven.game.interfaces.ITeam;

public class Telegram implements Comparable<Telegram> {
	public double dispatchDelay;
	public int senderID;
	public int receiverID;
	public RavenMessage msg;
	public Object extraInfo;
	public ITeam team;
	
	public Telegram() {
		dispatchDelay = -1;
		senderID = -1;
		receiverID = -1;
		msg = RavenMessage.MSG_BLANK;
		team = null;
		
	}
	public Telegram(long dispatchTime, int senderID, int receiverID, RavenMessage msg, Object extraInfo) {
		this.dispatchDelay = dispatchTime;
		this.senderID = senderID;
		this.receiverID = receiverID;
		this.msg = msg;
		this.extraInfo = extraInfo;
	}
	
	/**
	 * A version of Telegram that takes care of teams.
	 * @param dispatchTime
	 * @param senderID
	 * @param receiverTeam
	 * @param msg
	 * @param extraInfo
	 */
	public Telegram(long dispatchTime, int senderID, ITeam receiverTeam, RavenMessage msg, Object extraInfo) {
		this.dispatchDelay = dispatchTime;
		this.senderID = senderID;
		this.team = receiverTeam;
		this.msg = msg;
		this.extraInfo = extraInfo;
	}


	
	
	@Override
	public int compareTo(Telegram other) {
		return (int)(this.dispatchDelay - other.dispatchDelay);
	}
}
