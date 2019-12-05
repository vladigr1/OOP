package tasks;

public class Tasks {
	
	private int[][] task;	//first [i][j] i for task j for dependsiez in the task
	private int[] taskNumDep;	//each task may dpend on 0- n-1 number of depnedies will be shown here
	private int[] taskNumNow;	//after free some task	
	private int taskAmount;
	
	public Tasks(int num) {
		taskAmount = num;
		task = new int[num][num];
		taskNumDep =  new int[10];		//defaultly it set them to 0
		taskNumNow =  new int[10];
	}
	
	public boolean dependsOn(int task1, int task2) {
		if(task1 < 0 || task1 > taskAmount-1) return false;
		if(task2 < 0 || task2 > taskAmount-1) return false;
		if(taskNumDep[task1] == taskAmount) return false;
		
		for(int i = 0;i <taskNumDep[task1];i++) {
			if(task[task1][i] == task2) return true;		//in case this dependencies is existed in the system
		}
		
		task[task1][ taskNumDep[task1] ] = task2;
		taskNumDep[task1]++;
		taskNumNow[task1]++;
		return true;
	}
	
	private void freedependencies(int task) {
		int i,j;
		for(i=0;i<taskAmount;i++) {
			for(j=0;j<taskNumDep[i];j++) {
				if(task == this.task[i][j]) {
					taskNumNow[i]--;
					break;			//dont need to run more in this task
				}
			}
		}
	}
	
	private int[] returnResult(boolean flagAddedTask,int[] result) {
		if(flagAddedTask == false) result = null;	//if we exited the i loop because of the this flag mean their is dependencies loop
		return result;
	}
	
	public int[] order() {
		// i is the index of the result count 
		// j is the index of the task we check at the moment
		// k is the index of the result that we check if the task was reported free
		int[] result = new int[taskAmount];
		int i,j,k;
		boolean flagNewTask, flagAddedTask = true;;
		for(i =0;i<taskAmount && flagAddedTask == true;i++) {
			flagAddedTask = false;
			for(j =0;j<taskAmount;j++) {	//find a task which is free of dependencies
				if(taskNumNow[j] == 0) {
					flagNewTask = true;
					for(k=0;k<i;k++) {		//search in result if this task is out of the system
						if(result[k] == j) {
							flagNewTask = false;
							break;
						}
					}// the k index loop
					if(flagNewTask == true) {
						flagAddedTask = true;
						result[i]=j;
						freedependencies(j);
						break;				//finish search for a task for index i in this moment
					}// this if break j loop
					
				}//if(taskNumDep[j] == 0) 
				
			}// the j index loop	
			
		}// the i index loop
		return returnResult(flagAddedTask,result);
		
	}// method order

}
