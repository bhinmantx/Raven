package raven.game;
import raven.game.*;
import raven.utils.Log;



	/** Singleton needed of this class
	 * @author Brendan
	 *
	 */

	public class TaskMaster {
		
		private static class TaskMasterHolder{
			public static final TaskMaster TM_INSTANCE = new TaskMaster();
		}
		

		
		public static TaskMaster getMaster() {
			return TaskMasterHolder.TM_INSTANCE;
		}
		

		
		public RavenTask getNewTask(RavenTask curTask, Team team)
		{
			  
			if (!team.teamHasCaptain() || (curTask == RavenTask.TASK_CAPTAIN)){
				return RavenTask.TASK_CAPTAIN;
			}
			Log.debug("Team Tasks", ("The Current task is: " + curTask));
			team.getTaskTable().put(curTask,(team.getTaskTable().get(curTask)-1));
			
			/////On to assignments!
			if (team.getTaskTable().get(RavenTask.TASK_BODYGUARD) > 2){
				Log.debug("Team Tasks", "Sniper Task Given");
				return RavenTask.TASK_SNIPER;
			}
			else{
				team.getTaskTable().put(RavenTask.TASK_BODYGUARD,(team.getTaskTable().get(RavenTask.TASK_BODYGUARD)+1));
				Log.debug("Team Tasks", "Body Guard Task Given");
			return RavenTask.TASK_BODYGUARD;
			}
			
		}
		
		/**
		 * Fuzzy Logic version of the getNewTask
		 * @return
		 */
		public RavenTask getFuzzyNewTask(){
			
			return RavenTask.TASK_BODYGUARD;
		
		}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*
		 
		
		public RavenTask getNewTask(Team botTeam){
			return getNewTask(botTeam, RavenTask.TASK_NONE);
		}
		

		public RavenTask getNewTask(Team botTeam, RavenTask cur_task){
			
			///TODO -  This is where iterators could go in order to create 
			///Different tasks
			
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
		
		*/
		
		
		private TaskMaster() {
			Log.info("TASKMASTER", " Task master private function ");
		}
		
		
		
	}
	
	
	

