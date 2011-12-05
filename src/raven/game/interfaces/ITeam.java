/**
 * 
 */
package raven.game.interfaces;

import java.awt.Color;

import raven.game.RavenBot;
import raven.game.RavenObject;
import raven.game.RavenTask;
import raven.game.messaging.Telegram;
import raven.goals.GoalThink;
import raven.math.Vector2D;

/**
 * @author brendan
 *
 */
public interface ITeam {


	//public GoalThink getBrain();
	public RavenTask getNewTask(RavenTask curTask);
	public void CaptainIsNow(RavenBot bot);
	public Color getTeamColor();
	public Color getCaptainColor();
	public boolean handleMessage(Telegram msg);
	public void update(double delta);
	public Vector2D getTeamSpawnPoint();
	public void draftBot(IRavenBot draftee);
	public void removeBotFromTeam(IRavenBot draftee);
	public RavenObject entityType();
	public int ID();
}
