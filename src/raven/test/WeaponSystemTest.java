/**
 * 
 */
package raven.test;

import java.util.Timer;

import junit.framework.Assert;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import raven.armory.model.Blaster;
import raven.armory.model.Railgun;
import raven.armory.model.RocketLauncher;
import raven.armory.model.Shotgun;
import raven.game.RavenObject;
import raven.game.RavenWeaponSystem;
import raven.game.interfaces.IRavenBot;
import raven.game.interfaces.IRavenGame;
import raven.game.interfaces.IRavenMap;
import raven.game.interfaces.IRavenTargetingSystem;
import raven.math.Vector2D;
import raven.script.RavenScript;

/**
 * @author chester
 *
 */
public class WeaponSystemTest {

	Mockery mocker;
	IRavenBot bot;
	IRavenBot target;
	IRavenTargetingSystem targeting;
	Vector2D botPos;
	
	@Before
	public void SetUp(){
		mocker = new Mockery();
		bot = mocker.mock(IRavenBot.class, "sourceBot");
		target = mocker.mock(IRavenBot.class, "targetBot");
		targeting = mocker.mock(IRavenTargetingSystem.class);
		botPos = new Vector2D(5, 5);
	}
	
	@Test
	public void Weapon_Chosen_With_Close_Enemy_Is_Shotgun(){
		
		final Vector2D targetPos = new Vector2D(10, 10);
		
		mocker.checking(new Expectations(){{
			allowing(bot).getTargetSys(); will(returnValue(targeting));
			allowing(target).pos(); will(returnValue(targetPos));
			oneOf(targeting).isTargetPresent(); will(returnValue(true));
			allowing(bot).pos(); will(returnValue(botPos));
			oneOf(targeting).getTarget(); will(returnValue(target));
		}});
		
		RavenWeaponSystem weapons = new RavenWeaponSystem(bot, 0.0, 1.0, 0.0);
		weapons.addWeapon(RavenObject.SHOTGUN);
		weapons.addWeapon(RavenObject.BLASTER);
		weapons.addWeapon(RavenObject.ROCKET_LAUNCHER);
		weapons.addWeapon(RavenObject.RAIL_GUN);
		weapons.selectWeapon();
		Assert.assertTrue(weapons.getCurrentWeapon().getClass() == Shotgun.class);
	}
	
	@Test
	public void Weapon_Chosen_With_Far_Enemy_Is_Rail_Gun(){
			
		final Vector2D targetPos = new Vector2D(150, 150);
		
		mocker.checking(new Expectations(){{
			allowing(bot).getTargetSys(); will(returnValue(targeting));
			allowing(target).pos(); will(returnValue(targetPos));
			oneOf(targeting).isTargetPresent(); will(returnValue(true));
			allowing(bot).pos(); will(returnValue(botPos));
			oneOf(targeting).getTarget(); will(returnValue(target));
		}});
		
		RavenWeaponSystem weapons = new RavenWeaponSystem(bot, 0.0, 1.0, 0.0);
		weapons.addWeapon(RavenObject.SHOTGUN);
		weapons.addWeapon(RavenObject.BLASTER);
		weapons.addWeapon(RavenObject.ROCKET_LAUNCHER);
		weapons.addWeapon(RavenObject.RAIL_GUN);
		weapons.selectWeapon();
		Assert.assertTrue(weapons.getCurrentWeapon().getClass() == Railgun.class);
	}
	
	@Test
	public void Weapon_Chosen_When_Medium_Range_Is_Rocket(){
		final Vector2D targetPos = new Vector2D(100, 100);
		
		mocker.checking(new Expectations(){{
			allowing(bot).getTargetSys(); will(returnValue(targeting));
			allowing(target).pos(); will(returnValue(targetPos));
			oneOf(targeting).isTargetPresent(); will(returnValue(true));
			allowing(bot).pos(); will(returnValue(botPos));
			oneOf(targeting).getTarget(); will(returnValue(target));
		}});
		
		RavenWeaponSystem weapons = new RavenWeaponSystem(bot, 0.0, 1.0, 0.0);
		weapons.addWeapon(RavenObject.SHOTGUN);
		weapons.addWeapon(RavenObject.BLASTER);
		weapons.addWeapon(RavenObject.ROCKET_LAUNCHER);
		weapons.addWeapon(RavenObject.RAIL_GUN);
		weapons.selectWeapon();
		Assert.assertTrue(weapons.getCurrentWeapon().getClass() == RocketLauncher.class);
	}
	
	@Test
	public void Weapon_Chosen_When_Close_And_Empty_Railgun_Is_Blaster(){
		
		final Vector2D targetPos = new Vector2D(10, 10);
		
		mocker.checking(new Expectations(){{
			allowing(bot).getTargetSys(); will(returnValue(targeting));
			allowing(target).pos(); will(returnValue(targetPos));
			oneOf(targeting).isTargetPresent(); will(returnValue(true));
			allowing(bot).pos(); will(returnValue(botPos));
			oneOf(targeting).getTarget(); will(returnValue(target));
		}});
		
		RavenWeaponSystem weapons = new RavenWeaponSystem(bot, 0.0, 1.0, 0.0);
		weapons.addWeapon(RavenObject.BLASTER);
		weapons.addWeapon(RavenObject.RAIL_GUN);
		weapons.getWeaponFromInventory(RavenObject.RAIL_GUN).setCurrentRounds(0);
		weapons.selectWeapon();
		Assert.assertTrue(weapons.getCurrentWeapon().getClass() == Blaster.class);
	}

	@Test
	public void Blaster_Fires_No_More_Than_The_Configured_Amount(){
		double blasterRate = RavenScript.getDouble("Blaster_FiringFreq");
		int duration = 10; //seconds
		int timesFiredMax = (int)(duration/blasterRate); // this is the theoretical maximum.  We'll 
												  		 // floor this and say we can't fire more than this.
		int timesFired = 0;
		final IRavenGame gameStub = mocker.mock(IRavenGame.class);
		final IRavenMap mapStub = mocker.mock(IRavenMap.class);
		Vector2D targetPos = new Vector2D(0, 0);
		
		mocker.checking(new Expectations() {{
			allowing(bot).getWorld(); will(returnValue(gameStub));
			allowing(gameStub).addBolt(with(any(IRavenBot.class)), with(any(Vector2D.class)));
			allowing(gameStub).getMap(); will(returnValue(mapStub));
			allowing(mapStub).addSoundTrigger(with(any(IRavenBot.class)), with(any(Double.class)));
		}});
		
		Blaster blaster = new Blaster(bot);
		double timeStart = System.nanoTime();
		double time = timeStart;
		double newTime;
		while(timesFiredMax > 0){
			newTime = System.nanoTime();
			blaster.update(newTime-time);
			timesFired += blaster.ShootAt(targetPos) ? 1 : 0;
			time = System.nanoTime();
		}
		double durationReal = time-timeStart;
		Assert.assertTrue((int)durationReal >= duration);
	}

}
