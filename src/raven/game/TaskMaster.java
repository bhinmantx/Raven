package raven.game;

import raven.game.*;
import raven.utils.Log;
//import raven.game.EntityManager.EntityManagerHolder;




//public class EntityManager {
//	private static class EntityManagerHolder {
//		public static final EntityManager INSTANCE = new EntityManager();
//	}



/** Singleton needed of this class
 * @author Brendan
 *
 */
//public class TaskMaster extends BaseGameEntity {
public class TaskMaster {
	
	private static class TaskMasterHolder{
		public static final TaskMaster TM_INSTANCE = new TaskMaster();
	}
	
	
//	protected TaskMaster(int id, Team team) {
//		super(id);
//		owningTeam = team;
//		// TODO Auto-generated constructor stub
//	}
	
	public static TaskMaster getMaster() {
		return TaskMasterHolder.TM_INSTANCE;
	}
	
	

//	@Override
//	public void render() {
//		// TODO Auto-generated method stub
		
//	}
	
	/**
	 * This runs the RavenTask version of getNewTask with TASK_NONE, to avoid 
	 * re-writing the same code. 
	 * @return
	 */
	public RavenTask getNewTask(Team botTeam){
		return getNewTask(botTeam, RavenTask.TASK_NONE);
	}
	
	/**
	 * A version of getNewTask that accepts the current task for the purposes of arbitration. 
	 * @param cur_task
	 * @return a string of the new tasks
	 */
	public RavenTask getNewTask(Team botTeam, RavenTask cur_task){
		
		
		if (!botTeam.teamHasCaptain()){
		Log.info("TASKMASTER", " returning TASK_CAPTAIN ");
		return RavenTask.TASK_CAPTAIN;
		}
		else
			return RavenTask.TASK_BODYGUARD;
		
		//return cur_task;
		
	}

	///TODO Is there a captain? Make a captain!
	
	///TODO Other cases
	///If there is already a captain, let's figure out a new role
	///wingman
	///Medic
	
	private TaskMaster() {
		Log.info("TASKMASTER", " Task master private function ");
	}
	
	
	
}
