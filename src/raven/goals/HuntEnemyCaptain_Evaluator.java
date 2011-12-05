package raven.goals;

import raven.game.EntityManager;
import raven.game.RavenBot;
import raven.game.RavenTask;
import raven.goals.Goal.GoalType;
import raven.math.Vector2D;
import raven.ui.GameCanvas;
import raven.utils.Log;

public class HuntEnemyCaptain_Evaluator extends Goal_Evaluator {
	public HuntEnemyCaptain_Evaluator(Double inp) {
		super(inp, GoalType.goal_HuntEnemyCaptain);
	}

///////THIS EVALUATOR DOES NOT WORK YET
	///CURRENTLY DO NOT HAVE A WAY TO DETECT "EnemyCaptain" that isn't hardcoded to 2 teams


	//---------------- CalculateDesirability -------------------------------------
	//-----------------------------------------------------------------------------
	public double calculateDesirability(RavenBot pBot)
	{

		return 1;
	}
	
	public double calculateDesirability(RavenBot pBot, double huntBias){
		return huntBias;
	}

	//----------------------------- SetGoal ---------------------------------------
	//-----------------------------------------------------------------------------
	public void setGoal(RavenBot pBot)
	{
		//TODO Change this so that it can determine an enemyCaptain!!!
		
		if(pBot.getTeam().ID() == 0){	
			pBot.getBrain().queueGoal_moveToPosition((EntityManager.getTeamFromID(1)).getCaptainLocation());
		}
		else
		pBot.getBrain().queueGoal_moveToPosition((EntityManager.getTeamFromID(0)).getCaptainLocation());
		
		
		//Log.info("GoalSet for move to position I guess");
	}

	//-------------------------- RenderInfo ---------------------------------------
	//-----------------------------------------------------------------------------
	public void renderInfo(Vector2D Position, RavenBot pBot)
	{
		GameCanvas.textAtPos(Position, "EX: " + "Desire: "+ calculateDesirability(pBot));
	}

}
