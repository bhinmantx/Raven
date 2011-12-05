package raven.game;

import raven.game.interfaces.ITeam;
import java.awt.Color;
import raven.game.messaging.Telegram;
//import raven.game.RavenBot;
import raven.game.interfaces.IRavenBot;
//import raven.goals.GoalThink;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import raven.math.*;
import raven.ui.GameCanvas;
import raven.utils.Log;
import raven.game.TaskMaster;

import java.util.ArrayList;



public class Team extends BaseGameEntity implements ITeam
{
	
	private static int teamID;
	private Color teamColor;
	private Color captainColor;
	private static int currValidColor = 0;
	private IRavenBot teamCaptain = null;
	
	////A list of bots on the team, should be references, I'll ask
	private	List<IRavenBot> teamBots = new ArrayList<IRavenBot>();
	
	///We need a valid location for spawning
	private ArrayList<Vector2D> teamSpawnPoints;
	
	//public static Color teamColor;
	
	//Goal queue? 
	//private GoalThink teamBrain;
	

	
	
	public Team(int id)
	{
			super(id);
			//Can I just modify this constructor?
			
			//Just so we don't get some null point exception
			//Just for testing.
			//teamSpawnPoints.add( new Vector2D(0,0));
			setEntityType(RavenObject.TEAM);
			//teamBrain = new GoalThink(this);
			
			/////Setting team ID before we register with entity manager.
			teamID = id; 
			//team ID = currValidTeamID;
			//currValidTeamID++;
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
			 
		}



	//We want a way to add a bot to the list of bots on this team
	//But, we're in the middle of gutting out the entity manager
	//so we should probably just accept a reference to a bot.
	//Also using "draft" and drop instead of "add/remove"
	//to avoid confusion
	public void draftBot(IRavenBot draftee) {
	//Ask if this works as a reference
		/*
		if (teamBots.isEmpty()){
			
			draftee.becomeCaptain();
			Log.info("TEAM", "Registered Captain of team " + draftee.getTeam().ID());
			this.teamCaptain = draftee;
		}
		*/
		teamBots.add(draftee);
		Log.info("Drafted");
	}
	
	
	
	
	public boolean teamHasCaptain(){
		return (this.teamCaptain != null);
			
	}
	
	
	
	
	
	
	///We may want to add a clear/remove team association. 
	public void removeBotFromTeam(IRavenBot draftee){
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
	
	
	public RavenTask getNewTask(){
		
		return (TaskMaster.getMaster()).getNewTask(this);
		
		//return RavenTask.TASK_NONE;
//		return RavenTask.TASK_CAPTAIN;
	}



	public void CaptainIsNow(RavenBot ravenBot) {
		teamCaptain = ravenBot;
	}
	
}


