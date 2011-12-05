package raven.game;

/**
 * These are the various tasks (which govern corresponding goals/subgoals in arbitration
 * @author Brendan
 *
 */
public enum RavenTask {
TASK_CAPTAIN (1,.5,.5,.5,.5,1,1),
TASK_BODYGUARD (1,.5,.5,.5,.5,1,0),
TASK_SNIPER (.5,0.0,0.0,1.25,.5,1,1),
TASK_NONE (1,.5,.5,.5,.5,1,1);

RavenTask(double health,
		  double shotgun,
		  double rocket,
		  double rail,
		  double explore,
		  double attack,
		  double hunt){
	
	this.HealthBias = health;
	this.ShotgunBias = shotgun;
	this.RocketLauncherBias = rocket;
	this.RailgunBias = rail;
	this.ExploreBias = explore;
	this.AttackBias = attack;
	this.HuntBias = hunt;
}


public final double HealthBias;
public final double ShotgunBias;
public final double RocketLauncherBias;
public final double RailgunBias;
public final double ExploreBias;
public final double AttackBias;
public final double HuntBias;
}








