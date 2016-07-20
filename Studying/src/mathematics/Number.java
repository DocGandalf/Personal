package mathematics;

public class Number {
	private NumberBehavior n;
	private int priority;
	private int order;
	
	public Number(double value, int priority, int order){
		if(value==(int)value){
			n=new IntBehavior((int)value);
		}else{
			n=new DoubleBehavior(value);
		}
		this.priority=priority;
		this.order=order;
	}
	
	public int getPriority(){
		return priority;
	}
	
	public double getNum(){
		return n.getNum();
	}
	
	public boolean isDouble(){
		return n.isDouble();
	}
	
	public int getOrder(){
		return order;
	}
	
	public void decrementPriority(){
		priority--;
	}
	
	public void incrementPriority(){
		priority++;
	}
	
	public void changeOrder(int order){
		this.order=order;
	}
	
}