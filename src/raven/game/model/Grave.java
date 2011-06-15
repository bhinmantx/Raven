/**
 * 
 */
package raven.game.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import raven.math.Vector2D;
import raven.script.RavenScript;

/**
 * @author chester
 *
 */
public class Grave extends BaseGameEntity {

	public Vector2D position;
	public double timeLeft;
	public static double lifeTime = RavenScript.getDouble("GraveLifetime");
	
	private List<Vector2D> vecRIPVB;
	private List<Vector2D> vecRIBVBTrans;
	
	public Grave(Vector2D position) {
		super(BaseGameEntity.getNextValidID());
		this.position = position;
		timeLeft = lifeTime;
		
		vecRIPVB = new ArrayList<Vector2D>();
		vecRIPVB.add(new Vector2D(-4, -5));
		vecRIPVB.add(new Vector2D(-4, 3));
		vecRIPVB.add(new Vector2D(-3, 5));
		vecRIPVB.add(new Vector2D(-1, 6));
		vecRIPVB.add(new Vector2D(1, 6));
		vecRIPVB.add(new Vector2D(3, 5));
		vecRIPVB.add(new Vector2D(4, 3));
		vecRIPVB.add(new Vector2D(4, -5));
		vecRIPVB.add(new Vector2D(-4, -5));
	}
	
	@Override
	public void update(double delta) {
		timeLeft -= delta;
	}

}
