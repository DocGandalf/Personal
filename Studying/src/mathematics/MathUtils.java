package mathematics;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MathUtils {
	public static void main(String[] args){
		System.out.println(nthRoot(2,12));
	}
	
	public static double difference(double n1, double n2){
		return Math.abs(n1-n2);
	}
	
	public static int difference(int n1, int n2){
		return Math.abs(n1-n2);
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
	
}
