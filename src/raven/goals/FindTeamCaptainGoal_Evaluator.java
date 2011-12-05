package raven.goals;

import raven.game.RavenBot;
import raven.game.RavenTask;
import raven.goals.Goal.GoalType;
import raven.math.Vector2D;
import raven.ui.GameCanvas;

public class FindTeamCaptainGoal_Evaluator extends Goal_Evaluator {
	public FindTeamCaptainGoal_Evaluator(Double inp) {
		super(inp, GoalType.goal_FindTeamCaptain);
	}




	//---------------- CalculateDesirability -------------------------------------
	//-----------------------------------------------------------------------------
	public double calculateDesirability(RavenBot pBot)
	{
		double Desirability = 0.0;

		if (pBot.getTask() == RavenTask.TASK_BODYGUARD)
		return 3.0;
		else
			
		

		return Desirability;
	}

	//----------------------------- SetGoal ---------------------------------------
	//-----------------------------------------------------------------------------
	public void setGoal(RavenBot pBot)
	{
		pBot.getBrain().queueGoal_moveToPosition(pBot.getTeam().getCaptainLocation());
	}

	//-------------------------- RenderInfo ---------------------------------------
	//-----------------------------------------------------------------------------
	public void renderInfo(Vector2D Position, RavenBot pBot)
	{
		GameCanvas.textAtPos(Position, "EX: " + "Desire: "+ calculateDesirability(pBot));
	}

}
