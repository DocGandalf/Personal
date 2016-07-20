package mathematics.significantFigures;
import java.util.Scanner;

import mathematics.MathUtils;
import riddles.GreenGlassDoor;
import java.util.ArrayList;

public class SigFig{
	private boolean isDouble;
	private String Number;

	public SigFig(String Number){
		isDouble=isDouble(Number);
		this.Number=Number;
	}
	
	public static boolean isDouble(String Number){
		return Number.contains(".")&&Number.lastIndexOf('.')!=Number.length()-1;
	}
	
	public boolean isDouble(){
		return isDouble;
	}
	
	public String getNum(){
		return Number;
	}
	
	public int getSigFigs() throws numberIsZeroException{
		if(this.isDouble()){
			return countSigFigs(this.getNum(),getExtraZeroes(this.Number));
		}else{
			return countSigFigs(this.getNum(),0);
		}
	}
	
	public static void main(String[] args) {//Program assumes a zero after point if no number is present.
		//SigFig sf=readSigFig();
		//printSigFigs(sf);
		String[] multiply=new String[3];
		multiply[0]="500";
		multiply[1]="2.5";
		multiply[2]="3.66";
		System.out.println(times(multiply));
	}
	
	public static void printSigFigs(SigFig sf){
		try{
			System.out.println("The number: "+sf.getNum()+" has "+sf.getSigFigs()+" Significant Figures.");
		}catch (numberIsZeroException e){
			System.out.println("The number is Zero, just no.");
		}
	}
	
	public static SigFig readSigFig(){
		Scanner input=new Scanner(System.in);
		String Number=input.nextLine();
		if(Number.charAt(Number.length()-1)=='.'){
			Number=Number+"0";
		}
		input.close();
		return new SigFig(Number);
	}
	
	public static SigFig createSigFig(String n){
		return new SigFig(n);
	}
	
	public static String add(String[] Numbers){
		int sigFigs=0;
		int smallestDistanceFromDecimal=32;// set to some arbitrary high number
		int leastAccurateNumber=0;
		int farthestDigitFromDecimal=0;
		for(int i=0; i<Numbers.length; i++){
			try{
				int[] pos=findOuterNumbers(Numbers[i]);
				int position=pos[1]-(Numbers[i].lastIndexOf(".")==-1? Numbers[i].length():Numbers[i].lastIndexOf("."));
				if(smallestDistanceFromDecimal>position){
					smallestDistanceFromDecimal=position;
					farthestDigitFromDecimal=pos[0]-(Numbers[i].lastIndexOf(".")==-1? Numbers[i].length():Numbers[i].lastIndexOf("."));
					if(farthestDigitFromDecimal<0)farthestDigitFromDecimal++;//for calculation purposes you must offset all negatives to include a zero value
					leastAccurateNumber=i;
				}
			}catch(numberIsZeroException e){}
		}
		try{
			sigFigs=countSigFigs(Numbers[leastAccurateNumber],getExtraZeroes(Numbers[leastAccurateNumber]));
		}catch(numberIsZeroException e){}
		//I now know the least specific number
		double total=0;
		for(int i=0; i<Numbers.length; i++){
			double num=Double.parseDouble(Numbers[i]);
			total+=num;
		}
		total=betterApproximation(total);
		String answer=(total+"").contains("E")? EPowerToRegular(total+""):total+"";
		try{
			int farthest=(findOuterNumbers(answer)[0]-(answer.lastIndexOf(".")==-1? answer.length():answer.lastIndexOf(".")));
			if(farthest<0)farthest++;//same here /\
			sigFigs=farthestDigitFromDecimal-farthest+sigFigs;
		}catch(numberIsZeroException e){}
		answer=round(answer, sigFigs, false)+"";
		if(answer.contains("E"))answer=EPowerToRegular(answer);
		try{
			if(answer.indexOf(".")!=-1&&countSigFigs(answer,0)<sigFigs){
				for(int i=0; i<MathUtils.difference(countSigFigs(answer,0), sigFigs); i++){
					answer=answer+"0";
				}
			}
			if(Double.parseDouble(answer)==(int)Double.parseDouble(answer)&&countSigFigs(answer,0)!=sigFigs){
				answer=(int)Double.parseDouble(answer)+"";
				if(countSigFigs(answer,0)!=sigFigs){
					System.out.println("ERRROR!");// 5+5=1E1 NOT 10
				}
			}
		}catch(numberIsZeroException e){}
		return answer;
	}
	
	public static String times(String[] multiples){
		int lowest=0;
		try{
			for(int i=0; i<multiples.length; i++){
				SigFig sf=createSigFig(multiples[i]);
				if(sf.isDouble()){
					if(countSigFigs(sf.getNum(),getExtraZeroes(sf.Number))<lowest || lowest==0){
						lowest=countSigFigs(sf.getNum(),getExtraZeroes(sf.Number));
					}
				}else{
					if(countSigFigs(sf.getNum(),0)<lowest || lowest==0){
						lowest=countSigFigs(sf.getNum(),0);
					}
				}
			}
		}catch(numberIsZeroException e){
			return "0";
		}
		double total=1;
		for(int i=0; i<multiples.length; i++){
			double m=Double.parseDouble(multiples[i]);
			total=total*m;
		}
		total=betterApproximation(total);
		String answer=round((total+"").contains("E")? EPowerToRegular(total+""):total+"", lowest, true)+"";
		if(answer.contains("E"))answer=EPowerToRegular(answer);
		try{
			double dAnswer=Double.parseDouble(answer);
			if(dAnswer!=(int)dAnswer){
				if(countSigFigs(answer,0)!=lowest){
					int[] pos=findOuterNumbers(answer);
					if(MathUtils.difference(pos[0], pos[1])<lowest){
						for(int i=0; i<MathUtils.difference(MathUtils.difference(pos[0], pos[1]),lowest); i++){
							answer=answer+"0";
						}
					}
				}
			}else{
				boolean intFailed=false;
				boolean doubleFailed=false;
				if(countSigFigs((int)dAnswer+"",0)!=lowest){
					intFailed=true;
				}
				if(countSigFigs(answer,0)!=lowest){
					doubleFailed=true;
				}
				if(doubleFailed&&intFailed){
					answer=(int)dAnswer+"";
					int places=lowest-answer.length()-1;
					int insertPoint=lowest-1;
					answer=answer.substring(0, insertPoint)+"."+answer.substring(insertPoint, insertPoint+1)+"E"+(-places);
				}else if(doubleFailed){
					answer=(int)dAnswer+"";
				}//if intFailed then nothing happens
			}
		}catch(numberIsZeroException e){}
		return answer;
	}
	
	public static double betterApproximation(double n){
		if(n==(int)n)return n;
		String Number=n+"";
		if(Number.contains("0000000")&&Number.lastIndexOf("0000000")>Number.lastIndexOf('.')){
			return Double.parseDouble(Number.substring(0, Number.lastIndexOf("0000000")));
		}
		if(Number.contains("9999999")&&Number.lastIndexOf("9999999")>Number.lastIndexOf('.')){
			return Double.parseDouble(Number.substring(0, Number.lastIndexOf("9999999")));
		}
		if(MathUtils.difference(n, Math.round(n))<=0.0000001)return Math.round(n);
		return n;
	}
	
	private static double moveDecimal(String Number, int places){
		if(!Number.contains("."))Number=Number+".";
		int currentPos=Number.lastIndexOf(".");
		int nextPos=currentPos+places;
		ArrayList<String> digits=new ArrayList<String>();
		boolean isNegative=false;
		for(int i=0; i<Number.length(); i++){
			digits.add(GreenGlassDoor.mid(Number,i, 1));
			if(!isNegative&&digits.get(i).equals("-")){
				digits.remove(i);
				isNegative=true;
				currentPos--;
				nextPos--;
			}
		}
		for(int i=digits.size(); i<=nextPos; i++){
			digits.add("0");
		}
		digits.remove(currentPos);
		if(nextPos<0){
			for(int i=-1; i>=nextPos; i--){
				digits.add(0, "0");
			}
			nextPos=0;
		}
		digits.add(nextPos, ".");
		Number="";
		for(int i=0; i<digits.size(); i++){
			Number=Number+digits.get(i);
		}
		return Double.parseDouble((isNegative? "-":"")+Number);
	}
	
	private static double round(String Number, int sigFigs, boolean multiplication){
		int places=0;
		if(multiplication){
			try{
				places=findOuterNumbers(Number)[0]+sigFigs-Number.lastIndexOf(".")-(findOuterNumbers(Number)[0]>Number.lastIndexOf(".")? 1:0);
			}catch (numberIsZeroException e){}
		}else{
			places=sigFigs-((Number.charAt(0)=='-'? Number.charAt(1)=='0':Number.charAt(0)=='0')? 0:Number.lastIndexOf("."));
		}
		double roundNum=moveDecimal(Number, places);
		if(charToNum((roundNum+"").charAt(sigFigs+(roundNum<0? 2:1)))!=5){
			return moveDecimal((int)Math.round(roundNum)+"",-places);
		}else{
			if(sigFigs+(roundNum<0? 3:2)+(Number.charAt(0)=='-'? (Number.charAt(1)=='0'? 1:0):(Number.charAt(0)=='0'? 1:0))==Number.length()){
				if(charToNum((roundNum+"").charAt(sigFigs+(roundNum<0? 1:0)))%2==0){
					return moveDecimal((int)Math.floor(roundNum)+"",-places);
				}else{
					return moveDecimal((int)Math.round(roundNum)+"",-places);
				}
			}else{
				return moveDecimal((int)Math.round(roundNum)+"",-places);
			}
		}
	}
	
	public static String EPowerToRegular(String n){
		int places=Integer.parseInt(n.substring(n.indexOf('E')+1,n.length()));
		String Number=n.substring(0, n.indexOf('E'));
		int currentPos=n.indexOf('.');
		int nextPos=currentPos+places;
		ArrayList<String> digits=new ArrayList<String>();
		boolean isNegative=false;
		for(int i=0; i<Number.length(); i++){
			digits.add(GreenGlassDoor.mid(Number,i, 1));
			if(digits.get(i).equals("-")){
				digits.remove(i);
				isNegative=true;
				currentPos--;
				nextPos--;
			}
		}
		if(nextPos<0){
			digits.remove(currentPos);
			for(int i=-1; i>=nextPos; i--){
				digits.add(0, "0");
			}
			digits.add(0, ".");
			digits.add(0, "0");
		}else if(nextPos>Number.length()){
			digits.remove(currentPos);
			for(int i=digits.size(); i<nextPos; i++){
				digits.add("0");
			}
			digits.add(".");
			digits.add("0");
		}else{
			digits.remove(currentPos);
			digits.add(nextPos, ".");
		}
		Number="";
		for(int i=0; i<digits.size(); i++){
			Number=Number+digits.get(i);
		}
		return (isNegative? "-":"")+Number;
	}
	
	private static int charToNum(char c){
		return c-48;
	}
	
	public static int getExtraZeroes(String Number) throws numberIsZeroException{//returns how many zeroes are on the end of the number
		return Number.length()-(findOuterNumbers(Number)[1]+1);
	}
	
	private static int[] findOuterNumbers(String Number) throws numberIsZeroException{
		int[] positions= new int[3];
		positions[0]=-1;
		if(Number.charAt(0)=='.')Number="0"+Number;
		if(Number.charAt(Number.length()-1)=='.')Number=Number+"0";
		for(int i=0; i<Number.length(); i++){
			if(Number.charAt(i)!='0'&&Number.charAt(i)!='.'&&Number.charAt(i)!='-'){
				if(positions[0]==-1){
					positions[0]=i;
				}
				positions[1]=i;
			}
		}
		if(positions[0]==-1){
			throw new numberIsZeroException("Your number is Zero.");
		}
		if(positions[1]<Number.length()&&Number.contains(".")){
			positions[2]=1;
		}
		for(int i=0; i<Number.length(); i++){
			if(GreenGlassDoor.mid(Number,i,1).equals("0")&&positions[2]==1&&positions[1]<i){
				positions[1]=i;
			}
		}
		return positions;
	}
	
	public static int countSigFigs(String Number, int extraZeroes) throws numberIsZeroException{
		int sigFigs=0;
		boolean countingZeroes=false;
		if(isDouble(Number)){
			for(int i=0; i<Number.length(); i++){
				if(Number.charAt(i)=='0'){
					if(countingZeroes){
						sigFigs++;
					}
				}else if(Number.charAt(i)=='.'||Number.charAt(i)=='-'){
					
				}else{
					sigFigs++;
					countingZeroes=true;
				}
			}
			return sigFigs+extraZeroes;
		}else{
			int[] positions = new int[2];
			positions = findOuterNumbers(Number);
			for(int i=0; i<Number.length(); i++){
				if(GreenGlassDoor.mid(Number,i,1).equals("0")){
					if(countingZeroes){
						sigFigs++;
					}
				}else if(Number.charAt(i)=='-'){
					
				}else{
					sigFigs++;
					if(i>=positions[0]&&i<positions[1]){
						countingZeroes=true;
					}else{
						countingZeroes=false;
					}
				}
			}
			return sigFigs;
		}
	}
}
