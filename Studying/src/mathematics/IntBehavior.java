package mathematics;

public class IntBehavior implements NumberBehavior{
	private int num;
	
	public IntBehavior(int n){
		num=n;
	}
	
	public boolean isDouble(){
		return false;
	}
	
	public double getNum(){
		return this.num;
	}
}