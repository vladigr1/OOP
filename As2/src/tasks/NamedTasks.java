package tasks;

public class NamedTasks extends Tasks{
	
	private String names[];
	
	public NamedTasks(String[] names) {
		super(names.length);
		this.names = new String[names.length];
		int i;
		for(i=0;i<names.length;i++) {
			this.names[i] = names[i];
		}
	}
	
	public boolean dependsOn(String task1, String task2) {
		int i,task1id,task2id;
		task1id = task2id = -1;	//in case the task is missing we will send -1 which fail the son method
		for(i=0;i<names.length;i++) {
			if(task1.equals(names[i]))task1id = i;
			if(task2.equals(names[i]))task2id = i;
		}
		return super.dependsOn(task1id, task2id);
	}
	
	private String[] returnStringResult(int[] result) {
		if(result == null) return null;	//if we exited the i loop because of the this flag mean their is dependencies loop
		int i;
		String[] namedResult = new String[names.length];
		for(i=0;i<names.length;i++) {
			namedResult[i] = names[result[i]];
		}
		return namedResult;
	}
	
	
	public String[] nameOrder(){
		int[] result = super.order();
		return returnStringResult(result);
		
	}
}