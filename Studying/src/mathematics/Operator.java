package mathematics;

import java.util.*;

public class Operator {
	private Operation operation;
	private int priority;
	private int order;
	public static enum Operation{
		ADD,
		SUBTRACT,
		DIVIDE,
		DIVIDEMINUS,
		TIMES,
		TIMESMINUS,
		POWER,
		POWERMINUS
	}
	
	public Operator(String operation, int priority, int order){
		if(operation.equals("+")){
			this.operation=Operation.ADD;
		}else if(operation.equals("+-")||operation.equals("-")){
			this.operation=Operation.SUBTRACT;
		}else if(operation.equals("/")){
			this.operation=Operation.DIVIDE;
		}else if(operation.equals("/-")){
			this.operation=Operation.DIVIDEMINUS;
		}else if(operation.equals("x")||operation.equals("*")){
			this.operation=Operation.TIMES;
		}else if(operation.equals("x-")||operation.equals("*-")){
			this.operation=Operation.TIMESMINUS;
		}else if(operation.equals("^")){
			this.operation=Operation.POWER;
		}else if(operation.equals("^-")){
			this.operation=Operation.POWERMINUS;
		}
		this.priority=priority;
		this.order=order;
	}
	
	public int getPriority(){
		return priority;
	}
	
	public Operation getOperation(){
		return operation;
	}
	
	public int getOrder(){
		return order;
	}
	
	public void incrementPriority(){
		priority++;
	}
	
	public double operate(double n1, double n2){
		if(getOperation()==Operation.ADD){
			return add(n1,n2);
		}else if(getOperation()==Operation.SUBTRACT){
			return sub(n1,n2);
		}else if(getOperation()==Operation.DIVIDE){
			return divide(n1,n2);
		}else if(getOperation()==Operation.DIVIDEMINUS){
			return divide(n1,-n2);
		}else if(getOperation()==Operation.TIMES){
			return times(n1,n2);
		}else if(getOperation()==Operation.TIMESMINUS){
			return times(n1,-n2);
		}else if(getOperation()==Operation.POWER){
			if(n2==(int)n2){
				return power(n1,(int)n2);
			}else{
				return power(n1, n2);
			}
		}else if(getOperation()==Operation.POWERMINUS){
			if(n2==(int)n2){
				return power(n1, -(int)n2);
			}else{
				return power(n1, -n2);
			}
		}
		throw new IllegalArgumentException("HOW!?!? HOW!!?!?!?!");
	}
	
	public static double add(double n1, double n2){
		return n1+n2;
	}
	
	public static double sub(double n1, double n2){
		return n1-n2;
	}
	
	public static double times(double n1, double n2){
		return n1*n2;
	}
	
	public static double divide(double n1, double n2){
		return n1/n2;
	}
	
	public static double power(double number, int power){
		double answer=1;
		for(int i=0; i<Math.abs(power); i++){
			answer=answer*number;
		}
		if(power>0){
			return answer;
		}else if(power<0){
			return 1/answer;
		}else{
			return 1;
		}
	}
	
	public static int factorial(int n){
		int answer=1;
		for(int i=n; i>1; i--){
			answer=answer*i;
		}
		return answer;
	}
	
	private static int amountRepeated(double p){
		String s=p+"";
		int[] positions=new int[2];
		for(int i=0; i<s.length()/2; i++){
			if(i>s.lastIndexOf('.')){
				if(positions[0]==0){
					positions[0]=i;
				}
				positions[1]=i;
				if(s.substring(positions[0],positions[1]+1).equals(s.substring(positions[0]+(positions[1]+1-positions[0]),positions[1]+1+(positions[1]+1-positions[0])))){
					return positions[1]+1-positions[0];
				}
			}
		}
		return 0;
	}
	
	public static double power(double a, double p){
		int amountOfPower=(p+"").length()-(p+"").lastIndexOf('.')-1;
		if(amountRepeated(p)>0){
			return power(a,(int)(p*power(10,amountRepeated(p)))*10+"/"+(power(10,amountRepeated(p)/2+1)*(power(10,amountRepeated(p)/2+1)-1)));
		}
		return power(a,p*power(10,amountOfPower)+"/"+power(10,amountOfPower));
	}
	
	public static double nthRoot(double a, int n){
		double answer=1;
		ArrayList<Double> lastAnswers=new ArrayList<Double>();
		while(!lastAnswers.contains(answer)){
			lastAnswers.add(answer);
			answer=(1./n)*((n-1)*answer+(a/(power(answer,(n-1)))));//got formula from wikipedia, it is more accurate than the calculators I checked it on.
		}
		return answer;
	}
	
	public static double power(double number, String complicatedPower){
		StringTokenizer s=new StringTokenizer(complicatedPower, "/");
		int power=(int)Double.parseDouble(s.nextToken());
		int root=(int)Double.parseDouble(s.nextToken());
		return power(nthRoot(number, root), power);//implement into Expression class, or not, whichever.
	}
	
	public void changeOrder(int order){
		this.order=order;
	}
	
	public static void main(String[] args){
		System.out.println(power(64,(1/3.)));
	}
	
}
