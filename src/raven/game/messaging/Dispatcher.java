package raven.game.messaging;

import java.util.HashSet;
import java.util.TreeSet;

import raven.game.BaseGameEntity;
import raven.game.EntityManager;
import raven.game.interfaces.IRavenBot;
import raven.game.interfaces.ITeam;
import raven.utils.Log;

public class Dispatcher {
	// Singleton Dispatcher, just like the original.
	private static class DispatcherHolder {
		public static final Dispatcher INSTANCE = new Dispatcher();
	}

	public static Dispatcher getInstance() {
		return DispatcherHolder.INSTANCE;
	}
	
	/** 
	 * a TreeSet is used as the container for the delayed message because of
	 * the benefit of automatic sorting and avoidance of duplicates. Messages
	 * are sorted by their dispatch time. */
	private TreeSet<Telegram> priorityQueue;
	
	private static void discharge(BaseGameEntity receiver, Telegram msg) {
		if (!receiver.handleMessage(msg)) {
			Log.error("Dispatcher", "The receiving object could not handle the message.");
		}
	}
	
	private static void discharge(ITeam receiver, Telegram msg){
		if (!receiver.handleMessage(msg)) {
			Log.error("Dispatcher", "The receiving object could not handle the message.");
		}
	}
	
	private static void discharge(IRavenBot receiver, Telegram msg) {
		if (!receiver.handleMessage(msg)) {
			Log.error("Dispatcher", "The receiving object could not handle the message.");
		}
	}
	
	private Dispatcher() { }

	public static final double SEND_MSG_IMMEDIATELY = 0.0;
	public static final int    NO_ADDITIONAL_INFO   = 0;
	public static final int    SENDER_ID_IRRELEVANT = -1;
	
	/**
	 * send a message to another agent. Receiving agent is referenced by ID.
	 * @param delay Delay in seconds until the message should be sent
	 * @param senderID the ID of the sender
	 * @param receiverID the ID of the sender 
	 * @param msg the type of the message to send
	 * @param extraInfo optional object to attach to the message
	 */
	public static void dispatchMsg(double delay,
							int senderID,
							int receiverID,
							RavenMessage msg,
							Object extraInfo) {
		
		// get the receiver
		BaseGameEntity receiver = EntityManager.getEntityFromID(receiverID);
		IRavenBot bot = null;
		// make sure the receiver is valid
		if (receiver == null) {
			//try to get the bot now
			bot = EntityManager.getBotFromID(receiverID);
		}
		
		if(bot == null) System.err.println("Warning! No receiver with ID of " + receiverID + " found.");
		
		
		// create the telegram
		Telegram telegram = new Telegram(0, senderID, receiverID, msg, extraInfo);
		
		// if there is no delay, route telegram immediately
		if (delay <= 0.0) {
			if(receiver != null) discharge(receiver, telegram);
			else discharge(bot, telegram);
		}
		// else add the telegram to be dispatched
		else {
			telegram.dispatchDelay = delay;
			
			getInstance().priorityQueue.add(telegram);
		}
	}
	
	/**
	 * send out any delayed messages. This method is called each time through
	 * the main game loop.
	 */
	public static void dispatchDelayedMessages(double delta) {
		HashSet<Telegram> toRemove = new HashSet<Telegram>();
		for (Telegram telegram : getInstance().priorityQueue) {
			telegram.dispatchDelay -= delta;
			if (telegram.dispatchDelay <= 0.0) {
				toRemove.add(telegram);
				BaseGameEntity receiver = EntityManager.getEntityFromID(telegram.receiverID);
				discharge(receiver, telegram);
			}
		}
		getInstance().priorityQueue.removeAll(toRemove);
	}

	

/**
 * In order to handle messages broadcast to the whole team, we have to account for the different ID system
 * that teams use. 
 * I'm removing the null receiver checks, because if there weren't any teams the system
 * would have already exited. 
 * @param delay
 * @param id
 * @param teamID
 * @param broadCastMessage
 * @param senderID
 */

	public static void dispatchMsg(double delay, int senderID, ITeam receiverTeam, RavenMessage broadCastMsg, Object extraInfo) {
		//EntityManager.getTeamFromID(teamID);
		
		// get the receiver
		ITeam receiver = receiverTeam;
//		ITeam team = null;
//		// make sure the receiver is valid
//		if (receiver == null) {
			//try to get the bot now
//			team = EntityManager.getTeamFromID(teamID);
//		}
	//	Log.info("Dispatcher", "Trying to broadcast");
		if(receiver == null) System.err.println("Warning! No Team receiver found.");
		
		
		// create the telegram
		Telegram telegram = new Telegram(0, senderID, receiver, broadCastMsg, extraInfo);
	//	Log.info("Dispatcher", "Created Telegram");
		// if there is no delay, route telegram immediately
		if (delay <= 0.0) {
		//	Log.info("Dispatcher", "Created Telegram");
			discharge(receiver, telegram);
		
		}
		// else add the telegram to be dispatched
		else {
			telegram.dispatchDelay = delay;
			
			getInstance().priorityQueue.add(telegram);
		}
	}



	
	
	
}


