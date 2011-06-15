/**
 * 
 */
package raven.armory.model;

import raven.game.interfaces.IRavenBot;
import raven.math.Geometry;
import raven.math.Vector2D;
import raven.messaging.Dispatcher;
import raven.messaging.RavenMessage;
import raven.script.RavenScript;
import raven.ui.GameCanvas;

/**
 * @author chester
 *
 */
public class Bolt extends RavenProjectile {

	public Bolt(IRavenBot shooter, Vector2D target)
	{
		super(target,
				shooter.getWorld(),
				shooter.ID(),
				shooter.pos(),
				shooter.facing(),
				RavenScript.getInt("Bolt_Damage"),
				RavenScript.getDouble("Bolt_Scale"),
				RavenScript.getDouble("Bolt_MaxSpeed"),
				RavenScript.getInt("Bolt_Mass"),
				RavenScript.getDouble("Bolt_MaxForce")
		);	
	}

	public void render() {
		GameCanvas.thickGreenPen();
		GameCanvas.line(position, position.sub(velocity));
	}

	public void update(double delta)
	{
		if(!HasImpacted())
		{
			velocity = heading().mul(maxSpeed());

			//make sure vehicle does not exceed maximum velocity
			velocity.truncate(maxSpeed());

			//update the position
			position = position.add(velocity);

			//if the projectile has reached the target position or it hits an entity
			//or wall it should explode/inflict damage/whatever and then mark itself
			//as dead

			//test to see if the line segment connecting the bolt's current position
			//and previous position intersects with any bots.

			IRavenBot hit = GetClosestIntersectingBot(position.sub(velocity), position);
			if (hit != null) {
				isDead = true;
				isImpacted = true;
				// send a message to the bot to let it know it's been hit, and who the
				// shot came from

				Dispatcher.dispatchMsg(Dispatcher.SEND_MSG_IMMEDIATELY, shooterID, hit.ID(), RavenMessage.MSG_TAKE_THAT_MF, damageInflicted);
			}

			//test for impact with a wall
			if (Geometry.FindClosestPointOfIntersectionWithWalls(position.sub(velocity), position, impactPoint, world.getMap().getWalls()) != null) {
				isDead = true;
				isImpacted = true;

				return;
			}

		}
	}
}
