package raven.game;

import raven.game.interfaces.ITeam;
import java.awt.Color;
import raven.game.messaging.Telegram;
//import raven.game.RavenBot;
import raven.game.interfaces.IRavenBot;
//import raven.goals.GoalThink;

import java.awt.Color;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import raven.math.*;
import raven.ui.GameCanvas;
import raven.utils.Log;
//import raven.game.TaskMaster;

import java.util.ArrayList;




public class Team extends BaseGameEntity implements ITeam
{
	
	private static int teamID;
	private Color teamColor;
	private Color captainColor;
	private static int currValidColor = 0;
	private IRavenBot teamCaptain = null;
	
	////A list of bots on the team
	private	List<IRavenBot> teamBots = new ArrayList<IRavenBot>();
	////Current Active roles, for Task Assignments
	private List<Integer> activeRoles = new ArrayList<Integer>();
	
	///We need a valid location for spawning
	private ArrayList<Vector2D> teamSpawnPoints;
	
	//public static Color teamColor;
	
	//Goal queue? 
	//private GoalThink teamBrain;
	
	///TaskMaster Related
	///We have a list of all tasks, there should be a way to use that to create a list of active tasks.
	int bodyguardsActive = 0;
	int snipersActive = 0;
	private Hashtable<RavenTask, Integer> taskTable;
	
	
	public Team(int id)
	{
			super(id);

			
			//Just so we don't get some null point exception
			//Just for testing.
			//teamSpawnPoints.add( new Vector2D(0,0));

			
			/////Setting team ID before we register with entity manager.
			teamID = id; 
			setEntityType(RavenObject.TEAM);
	
			/////we want this to register a team by ID but let's
			/////just get the teams working
			EntityManager.registerEntity(this);
			
			///Time to generate the team color. 
			//trying to come up with an interesting way to do this
			
			//Current, inelegant solution is to find out if
			//the last team color was zero (red) and if so, 
			//the new one is blue.

			///TODO Support for additional team colors
			 if (currValidColor == 0)
			 {
			 teamColor = new Color(250,0,0);
			 //captainColor = (copyColor(teamColor)).brighter();
			 captainColor = createCaptainColor(teamColor);
			 currValidColor = 1;
			 }
			 else
			 {
				 teamColor = new Color(0,250,0);
				 captainColor = createCaptainColor(teamColor);
				 currValidColor = 0;
			 }
			 
			 ///This may be a bad way to do this, but very every element in the
			 ///RavenTask enum, we now have a way of tracking how many of them are active!
			 
			 taskTable = new Hashtable<RavenTask, Integer>();
			 for(RavenTask rt : RavenTask.values()){
				 taskTable.put(rt, 0);
			 }
			 
		}



	//We want a way to add a bot to the list of bots on this team
	//But, we're in the middle of gutting out the entity manager
	//so we should probably just accept a reference to a bot.
	//Also using "draft" and drop instead of "add/remove"
	//to avoid confusion
	public void draftBot(IRavenBot draftee) {
		teamBots.add(draftee);
		Log.info("Drafted");
	}
	

	public boolean teamHasCaptain(){
		return (this.teamCaptain != null);
			
	}

	///We may want to add a clear/remove team association. 
	public void removeBotFromTeam(IRavenBot draftee){
		
		if (draftee.getTask() == RavenTask.TASK_BODYGUARD)
			bodyguardsActive--;
		else if (draftee.getTask() == RavenTask.TASK_CAPTAIN)
			teamCaptain = null;
		else if (draftee.getTask() == RavenTask.TASK_SNIPER)
			snipersActive--;
		///TODO There HAS to be a way to use the enum to populate a list with what's active.
		teamBots.remove(draftee);
		
	}

	@Override
	public void render()
	{}
	
	@Override
	public boolean handleMessage(Telegram msg) {
		Log.info("Team", "Received Broadcast");	
		Iterator<IRavenBot> iterator = teamBots.iterator();
		while(iterator.hasNext()){
			
			((IRavenBot)iterator.next()).handleMessage(msg);
			
		}		
	return true;
	}

	//TODO If we implement a "Base" so that bots don't 
	//spawn into a group of opponents every time
	public Vector2D getTeamSpawnPoint(){
		return teamSpawnPoints.get(0);
	}

	public Color getTeamColor(){
		return teamColor;
	}
	



	public Color getCaptainColor() {
		return captainColor;
	}
	
	///Instead of implementing a copy constructor for a class we didn't create,
	///building a helper function
	private Color copyColor(Color source){
		return (new Color(source.getRGB()));		
	}
	
	/**
	 * Creates a darker version of the existing team color
	 * @param teamColor -> existing color of this team
	 * @return new color (R.G.B values divided by 2)
	 */
	private Color createCaptainColor(Color teamColor) {
//		return copyColor(teamColor).brighter();
		return new Color((teamColor.getRed()/2),(teamColor.getGreen()/2),(teamColor.getBlue()/2));
	}
	
	
public RavenTask getNewTask(RavenTask curTask){
	return TaskMaster.getMaster().getNewTask(curTask, this);
}

public RavenTask getNewTask(){
	return getNewTask(RavenTask.TASK_NONE);
}
	

	
	public Hashtable<RavenTask, Integer> getTaskTable()
	{
		return taskTable;
	}

	public void CaptainIsNow(RavenBot ravenBot) {
		teamCaptain = ravenBot;
	}
	
	public Vector2D getCaptainLocation()
	{
		return teamCaptain.pos();
	}
	
}


