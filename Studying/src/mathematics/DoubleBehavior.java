package mathematics;

public class DoubleBehavior implements NumberBehavior{
	private double num;
	
	public DoubleBehavior(double n){
		num=n;
	}
	
	public boolean isDouble(){
		return true;
	}
	
	public double getNum(){
		return this.num;
	}
}