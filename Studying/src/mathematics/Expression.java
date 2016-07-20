package mathematics;

import java.util.*;
import mathematics.Operator.Operation;
import mathematics.significantFigures.SigFig;
import mathematics.significantFigures.numberIsZeroException;

public class Expression {
	private ArrayList<Number> numbers=new ArrayList<Number>();
	private ArrayList<Operator> operations=new ArrayList<Operator>();
	private boolean useSigFigs;
	private int order=0;
	
	public Expression(String e, boolean useSigFigs) throws SyntaxException{
		if(e.contains("--")){
			throw new SyntaxException("Too many -\'s in a row");
		}
		if(e.contains("()")){
			throw new SyntaxException("Parentheses incorrect");
		}
		this.useSigFigs=useSigFigs;
		int[] positions={-1, 0};
		int parentheseCount=0;
		boolean wasNumber=false;
		boolean seenParenthese=false;
		for(int i=0; i<e.length(); i++){
			if(isOperation(e.charAt(i))){
				if(wasNumber){
					if(!seenParenthese){
						if(!isLegalNumber(e.substring(positions[0], positions[1]+1))){
							throw new SyntaxException("The input: "+e.substring(positions[0], positions[1]+1)+" is not a number.");
						}
						numbers.add(new Number(Double.parseDouble(e.substring(positions[0], positions[1]+1)), parentheseCount, order));
						order++;
					}
					positions[0]=i;
				}else{
					if(e.charAt(i)!='-'&&seenParenthese&&e.charAt(i-1)=='('){
						throw new SyntaxException("Syntax Error: "+i);
					}
					if(positions[0]!=i-1||positions[0]==-1){
						positions[0]=i;
					}
				}
				positions[1]=i;
				wasNumber=false;
				seenParenthese=false;
			}else if(e.charAt(i)=='('){
				if(!wasNumber){
					if(i!=0&&!numbers.isEmpty()){
						if(seenParenthese){
							operations.add(new Operator("*", parentheseCount, order));
						}else{
							operations.add(new Operator(e.substring(positions[0], positions[1]+1), parentheseCount, order));
						}
						order++;
					}
				}else{
					if(!isLegalNumber(e.substring(positions[0], positions[1]+1))){
						throw new SyntaxException("The input: "+e.substring(positions[0], positions[1]+1)+" is not a number.");
					}
					numbers.add(new Number(Double.parseDouble(e.substring(positions[0], positions[1]+1)), parentheseCount, order));
					order++;
					operations.add(new Operator("*", parentheseCount, order));
					order++;
				}
				parentheseCount++;
				wasNumber=false;
				seenParenthese=true;
			}else if(e.charAt(i)==')'){
				if(wasNumber){
					if(!isLegalNumber(e.substring(positions[0], positions[1]+1))){
						throw new SyntaxException("The input: "+e.substring(positions[0], positions[1]+1)+" is not a number.");
					}
					numbers.add(new Number(Double.parseDouble(e.substring(positions[0], positions[1]+1)), parentheseCount, order));
					order++;
				}
				//Not sure why this code was here.
//				boolean breakout=true;
//				for(int j=i; j<e.length(); j++){
//					if(e.charAt(j)!=')'){
//						breakout=false;
//					}
//				}
				--parentheseCount;
				seenParenthese=true;
//				if(breakout){break;}
				if(isOperation(e.charAt(i-1))){
					throw new SyntaxException("Syntax Error: "+i);
				}
				if(parentheseCount<0){
					throw new SyntaxException("Parentheses are incorrect");
				}
				wasNumber=false;
			}else{
				if(!wasNumber){
					if(i!=0&&!seenParenthese){
						operations.add(new Operator(e.substring(positions[0], positions[1]+1), parentheseCount, order));
						order++;
					}else if(seenParenthese&&e.charAt(i-1)==')'){
						operations.add(new Operator("*", parentheseCount, order));
						order++;
					}
					positions[0]=i;
				}
				positions[1]=i;
				wasNumber=true;
				seenParenthese=false;
			}
		}
		if(!wasNumber&&!seenParenthese){
			throw new SyntaxException("Syntax Error: "+order);
		}else{
			if(numbers.isEmpty()||operations.isEmpty()){
				throw new SyntaxException("Why did you think that would work?");
			}
			if(!seenParenthese){
				if(!isLegalNumber(e.substring(positions[0], positions[1]+1))){
					throw new SyntaxException("The input: "+e.substring(positions[0], positions[1]+1)+" is not a number.");
				}
				numbers.add(new Number(Double.parseDouble(e.substring(positions[0], positions[1]+1)), parentheseCount, order));
			}
		}
		if(parentheseCount!=0){
			throw new SyntaxException("Parentheses are incorrect");
		}
		if(numbers.isEmpty()||operations.isEmpty()){
			throw new SyntaxException("Why did you think that would work?");
		}
		if(useSigFigs&&((operations.contains(Operation.ADD)||operations.contains(Operation.SUBTRACT))&&(operations.contains(Operation.TIMES)||operations.contains(Operation.TIMESMINUS)||operations.contains(Operation.DIVIDE)||operations.contains(Operation.DIVIDEMINUS)))){
			throw new SyntaxException("Sig Figs+different operations=BAD");
		}
		if(useSigFigs&&(operations.contains(Operation.POWER)||operations.contains(Operation.POWERMINUS))){
			throw new SyntaxException("No Powers and sigFigs");
		}
		//Order of Operations
		//(P-taken care of above) EMD (AS-by doing these last, nothing has to be done)
		
		//Exponent Search
		for(int i=0; i<operations.size(); i++){
			Operator operation=operations.get(i);
			if(operation.getOperation()==Operation.POWER||operation.getOperation()==Operation.POWERMINUS){
				int j=1;//to the left
				boolean exit=false;
				boolean firstLoop=true;
				while(!exit){
					if(typeOfPreviousThing(operation.getOrder()-j+1).equals("Operator")){
						Operator o=getOperatorWithOrder(operation.getOrder()-j);
						if(o.getPriority()>operation.getPriority()){
							o.incrementPriority();
						}else if(o.getPriority()==operation.getPriority()){
							exit=true;
						}
					}else if(typeOfPreviousThing(operation.getOrder()-j+1).equals("Number")){
						Number n=getNumberWithOrder(operation.getOrder()-j);
						if(n.getPriority()>operation.getPriority()){
							n.incrementPriority();
						}else if(n.getPriority()==operation.getPriority()){
							if(firstLoop)n.incrementPriority();
							exit=true;
						}
					}else{
						exit=true;
					}
					j++;
					firstLoop=false;
				}
				j=-1;//to the right
				exit=false;
				firstLoop=true;
				while(!exit){
					if(typeOfPreviousThing(operation.getOrder()-j+1).equals("Operator")){
						Operator o=getOperatorWithOrder(operation.getOrder()-j);
						if(o.getPriority()>operation.getPriority()){
							o.incrementPriority();
						}else if(o.getPriority()==operation.getPriority()){
							exit=true;
						}
					}else if(typeOfPreviousThing(operation.getOrder()-j+1).equals("Number")){
						Number n=getNumberWithOrder(operation.getOrder()-j);
						if(n.getPriority()>operation.getPriority()){
							n.incrementPriority();
						}else if(n.getPriority()==operation.getPriority()){
							if(firstLoop)n.incrementPriority();
							exit=true;
						}
					}else{
						exit=true;
					}
					j--;
					firstLoop=false;
				}
				operation.incrementPriority();
			}
		}
		//Multiplication and Division
		for(int i=0; i<operations.size(); i++){
			Operator operation=operations.get(i);
			if(operation.getOperation()==Operation.TIMES||operation.getOperation()==Operation.TIMESMINUS||operation.getOperation()==Operation.DIVIDE||operation.getOperation()==Operation.DIVIDEMINUS){
				int j=1;//to the left
				boolean exit=false;
				boolean firstLoop=true;
				while(!exit){
					if(typeOfPreviousThing(operation.getOrder()-j+1).equals("Operator")){
						Operator o=getOperatorWithOrder(operation.getOrder()-j);
						if(o.getPriority()>operation.getPriority()){
							o.incrementPriority();
						}else if(o.getPriority()==operation.getPriority()){
							exit=true;
						}
					}else if(typeOfPreviousThing(operation.getOrder()-j+1).equals("Number")){
						Number n=getNumberWithOrder(operation.getOrder()-j);
						if(n.getPriority()>operation.getPriority()){
							n.incrementPriority();
						}else if(n.getPriority()==operation.getPriority()){
							if(firstLoop)n.incrementPriority();
							exit=true;
						}
					}else{
						exit=true;
					}
					j++;
					firstLoop=false;
				}
				j=-1;//to the right
				exit=false;
				firstLoop=true;
				while(!exit){
					if(typeOfPreviousThing(operation.getOrder()-j+1).equals("Operator")){
						Operator o=getOperatorWithOrder(operation.getOrder()-j);
						if(o.getPriority()>operation.getPriority()){
							o.incrementPriority();
						}else if(o.getPriority()==operation.getPriority()){
							exit=true;
						}
					}else if(typeOfPreviousThing(operation.getOrder()-j+1).equals("Number")){
						Number n=getNumberWithOrder(operation.getOrder()-j);
						if(n.getPriority()>operation.getPriority()){
							n.incrementPriority();
						}else if(n.getPriority()==operation.getPriority()){
							if(firstLoop)n.incrementPriority();
							exit=true;
						}
					}else{
						exit=true;
					}
					j--;
					firstLoop=false;
				}
				operation.incrementPriority();
			}
		}
	}
	
	private boolean isOperation(char c){
		if(c=='+'||c=='x'||c=='*'||c=='-'||c=='/'||c=='^'){
			return true;
		}
		return false;
	}
	
	private int getCase(String n, int i){
		if(n.charAt(i)=='0'||n.charAt(i)=='1'||n.charAt(i)=='2'||n.charAt(i)=='3'||n.charAt(i)=='4'||n.charAt(i)=='5'||n.charAt(i)=='6'||n.charAt(i)=='7'||n.charAt(i)=='8'||n.charAt(i)=='9'){
			return 0;
		}else if(n.charAt(i)=='.'){
			return 1;
		}else{
			return 2;
		}
	}
	
	private boolean isLegalNumber(String n){
		boolean[] accepting={false,false,true,false,true};
		int[][] transition={{2,3,1},{1,1,1},{2,4,1},{4,1,1},{4,1,1}};
		int state=0;
		for(int i=0; i<n.length(); i++){
			state=transition[state][getCase(n,i)];
		}
		return accepting[state];
	}
	
	public String evaluate(){
		int highestPriority=0;
		for(int i=0; i<numbers.size(); i++){
			if(highestPriority<numbers.get(i).getPriority()){
				highestPriority=numbers.get(i).getPriority();
			}
		}
		if(!useSigFigs){
			for(int p=highestPriority; p>=0; p--){
				for(int i=0; operationsPrioritySize(p)>0; i++){
					if(operations.get(i).getPriority()==p){
						calculate(i--, p);
					}
				}
				//multiply parentheses with anything needing it.
				ArrayList<Integer> positions=new ArrayList<Integer>();
				boolean chainActive=false;
				for(int i=0; i<numbers.size(); i++){
					if(numbers.get(i).getPriority()==p){
						if(chainActive){
							if(numbers.get(i).getOrder()-1==numbers.get(i-1).getOrder()){
								positions.add(i);
							}
							if(i!=numbers.size()-1){
								if(numbers.get(i).getOrder()+1!=numbers.get(i+1).getOrder()){
									chainActive=false;
								}
							}
						}else{
							if(i!=numbers.size()-1){
								if(numbers.get(i).getOrder()+1==numbers.get(i+1).getOrder()){
									positions.add(i);
									chainActive=true;
								}
							}
						}
					}
				}
				for(int i=0; positions.size()>1; i++){
					if(positions.get(i)+1==positions.get(i+1)){
						numbers.add(positions.get(i), new Number(Operator.times(numbers.get(i).getNum(), numbers.get(i+1).getNum()), p, numbers.get(positions.get(i)).getOrder()));
						numbers.remove(positions.get(i+1));
						numbers.remove(positions.get(i+1));
						positions.remove(i);
						positions.remove(i);
						for(int j=0; j<positions.size(); j++){
							positions.add(positions.get(j)-2);
							positions.remove(j+1);
						}
						recalculateOrder();
					}
				}
				for(int i=0; i<numbers.size(); i++){
					if(numbers.get(i).getPriority()==p){
						numbers.get(i).decrementPriority();
					}
				}
			}
		}else{
			int amountOfSigFigs=0;
			for(int p=highestPriority; p>=0; p--){
				for(int i=0; i<order;i++){
					Operation flag=Operation.POWER;
					String t=typeOfPreviousThing(i+1);
					if(t.equals("Number")&&getNumberWithOrder(i).getPriority()==p){
						ArrayList<String> numList=new ArrayList<String>();
						int j=i;
						while(!typeOfPreviousThing(j+1).equals("null")&&(typeOfPreviousThing(j+1).equals("Number")? getNumberWithOrder(j).getPriority()==p:getOperatorWithOrder(j).getPriority()==p)){
							if(typeOfPreviousThing(j+1).equals("Number")){
								Number n=getNumberWithOrder(j);
								numbers.remove(n);
								if(amountOfSigFigs==0){
									try{
										// Copy and Paste This--> n.isDouble()? n.getNum()+"":(n.getNum()+"").substring(0, (n.getNum()+"").lastIndexOf("."))
										amountOfSigFigs=SigFig.countSigFigs(n.isDouble()? n.getNum()+"":(n.getNum()+"").substring(0, (n.getNum()+"").lastIndexOf(".")), SigFig.getExtraZeroes(n.isDouble()? n.getNum()+"":(n.getNum()+"").substring(0, (n.getNum()+"").lastIndexOf("."))));
									}catch(numberIsZeroException e){}
								}
								switch (flag){
								case ADD:
									numList.add(n.isDouble()? n.getNum()+"":(n.getNum()+"").substring(0, (n.getNum()+"").lastIndexOf(".")));
									break;
								case DIVIDE:
									try{
										if(amountOfSigFigs>SigFig.countSigFigs(n.isDouble()? n.getNum()+"":(n.getNum()+"").substring(0, (n.getNum()+"").lastIndexOf(".")),SigFig.getExtraZeroes(n.isDouble()? n.getNum()+"":(n.getNum()+"").substring(0, (n.getNum()+"").lastIndexOf("."))))){
											amountOfSigFigs=SigFig.countSigFigs(n.isDouble()? n.getNum()+"":(n.getNum()+"").substring(0, (n.getNum()+"").lastIndexOf(".")),SigFig.getExtraZeroes(n.isDouble()? n.getNum()+"":(n.getNum()+"").substring(0, (n.getNum()+"").lastIndexOf("."))));
										}
									}catch (numberIsZeroException e){}
									numList.add((1/n.getNum())+"");
									break;
								case DIVIDEMINUS:
									try{
										if(amountOfSigFigs>SigFig.countSigFigs(n.isDouble()? n.getNum()+"":(n.getNum()+"").substring(0, (n.getNum()+"").lastIndexOf(".")),SigFig.getExtraZeroes(n.isDouble()? n.getNum()+"":(n.getNum()+"").substring(0, (n.getNum()+"").lastIndexOf("."))))){
											amountOfSigFigs=SigFig.countSigFigs(n.isDouble()? n.getNum()+"":(n.getNum()+"").substring(0, (n.getNum()+"").lastIndexOf(".")),SigFig.getExtraZeroes(n.isDouble()? n.getNum()+"":(n.getNum()+"").substring(0, (n.getNum()+"").lastIndexOf("."))));
										}
									}catch (numberIsZeroException e){}
									numList.add((-1/n.getNum())+"");
									break;
								case SUBTRACT:
									numList.add((n.getNum()*-1)+"");
									break;
								case TIMES:
									try{
										if(amountOfSigFigs>SigFig.countSigFigs(n.isDouble()? n.getNum()+"":(n.getNum()+"").substring(0, (n.getNum()+"").lastIndexOf(".")),SigFig.getExtraZeroes(n.isDouble()? n.getNum()+"":(n.getNum()+"").substring(0, (n.getNum()+"").lastIndexOf("."))))){
											amountOfSigFigs=SigFig.countSigFigs(n.isDouble()? n.getNum()+"":(n.getNum()+"").substring(0, (n.getNum()+"").lastIndexOf(".")),SigFig.getExtraZeroes(n.isDouble()? n.getNum()+"":(n.getNum()+"").substring(0, (n.getNum()+"").lastIndexOf("."))));
										}
									}catch (numberIsZeroException e){}
									numList.add(n.isDouble()? n.getNum()+"":(n.getNum()+"").substring(0, (n.getNum()+"").lastIndexOf(".")));
									break;
								case TIMESMINUS:
									try{
										if(amountOfSigFigs>SigFig.countSigFigs(n.isDouble()? n.getNum()+"":(n.getNum()+"").substring(0, (n.getNum()+"").lastIndexOf(".")),SigFig.getExtraZeroes(n.isDouble()? n.getNum()+"":(n.getNum()+"").substring(0, (n.getNum()+"").lastIndexOf("."))))){
											amountOfSigFigs=SigFig.countSigFigs(n.isDouble()? n.getNum()+"":(n.getNum()+"").substring(0, (n.getNum()+"").lastIndexOf(".")),SigFig.getExtraZeroes(n.isDouble()? n.getNum()+"":(n.getNum()+"").substring(0, (n.getNum()+"").lastIndexOf("."))));
										}
									}catch (numberIsZeroException e){}
									numList.add((n.getNum()*-1)+"");
									break;
								default:
									numList.add(n.isDouble()? n.getNum()+"":(n.getNum()+"").substring(0, (n.getNum()+"").lastIndexOf(".")));
									break;
								}
							}else if(typeOfPreviousThing(j+1).equals("Operator")){
								Operator o=getOperatorWithOrder(j);
								operations.remove(o);
								switch (o.getOperation()){
								case ADD:
									flag=Operation.ADD;
									break;
								case DIVIDE:
									flag=Operation.DIVIDE;
									break;
								case DIVIDEMINUS:
									flag=Operation.DIVIDEMINUS;
									break;
								case POWER:
									break;
								case POWERMINUS:
									break;
								case SUBTRACT:
									flag=Operation.SUBTRACT;
									break;
								case TIMES:
									flag=Operation.TIMES;
									break;
								case TIMESMINUS:
									flag=Operation.TIMESMINUS;
									break;
								default:
									break;
								}
							}
							j++;
						}
						String Answer="";
						String[] dummy=new String[1];
						switch (flag){
						case ADD:
							Answer=SigFig.add(numList.toArray(dummy));
							break;
						case DIVIDE:
							Answer=SigFig.times(numList.toArray(dummy));
							break;
						case DIVIDEMINUS:
							Answer=SigFig.times(numList.toArray(dummy));
							break;
						case SUBTRACT:
							Answer=SigFig.add(numList.toArray(dummy));
							break;
						case TIMES:
							Answer=SigFig.times(numList.toArray(dummy));
							break;
						case TIMESMINUS:
							Answer=SigFig.times(numList.toArray(dummy));
							break;
						default:
							break;
						}
						if(SigFig.isDouble(Answer)){
							numbers.add(i, new Number(Double.parseDouble(Answer), p-1, i));
						}else{
							numbers.add(i, new Number(Integer.parseInt(Answer), p-1, i));
						}
						recalculateOrder();
					}
				}
			}
			return numbers.get(0).isDouble()? numbers.get(0).getNum()+"":(numbers.get(0).getNum()+"").substring(0, (numbers.get(0).getNum()+"").lastIndexOf("."));
		}
		return numbers.get(0).getNum()+"";
	}
	
	private int operationsPrioritySize(int priority){
		int count=0;
		for(int i=0; i<operations.size(); i++){
			if(operations.get(i).getPriority()==priority){
				count++;
			}
		}
		return count;
	}
	
	private String typeOfPreviousThing(int order){
		Number testNum=getNumberWithOrder(order-1);
		Operator testOp=getOperatorWithOrder(order-1);
		if(testOp!=null){
			return "Operator";
		}else if(testNum!=null){
			return "Number";
		}else{
			return "null";
		}
	}
	
	private void calculate(int count, int priority){
		String Type=typeOfPreviousThing(operations.get(count).getOrder());
		if(Type.equals("Number")&&getNumberWithOrder(operations.get(count).getOrder()-1).getPriority()<priority){
			numbers.add(numbers.indexOf(getNumberWithOrder(operations.get(count).getOrder()+1)), new Number(-numbers.get(numbers.indexOf(getNumberWithOrder(operations.get(count).getOrder()+1))).getNum(), priority, operations.get(count).getOrder()+1));
			numbers.remove(numbers.indexOf(getNumberWithOrder(operations.get(count).getOrder()+1))+1);
			operations.remove(operations.get(count));
		}else if(Type.equals("Operator")&&getOperatorWithOrder(operations.get(count).getOrder()-1).getPriority()<priority){
			numbers.add(numbers.indexOf(getNumberWithOrder(operations.get(count).getOrder()+1)), new Number(-numbers.get(numbers.indexOf(getNumberWithOrder(operations.get(count).getOrder()+1))).getNum(), priority, operations.get(count).getOrder()+1));
			numbers.remove(numbers.indexOf(getNumberWithOrder(operations.get(count).getOrder()+1))+1);
			operations.remove(operations.get(count));
		}else if(Type.equals("null")){
			numbers.add(0, new Number(-numbers.get(0).getNum(), priority, 0));
			numbers.remove(1);
			operations.remove(0);
		}else{
			numbers.add(numbers.indexOf(getNumberWithOrder(operations.get(count).getOrder()-1)), new Number(operations.get(count).operate(getNumberWithOrder(operations.get(count).getOrder()-1).getNum(), getNumberWithOrder(operations.get(count).getOrder()+1).getNum()), priority, operations.get(count).getOrder()-1));
			numbers.remove(1+numbers.indexOf(getNumberWithOrder(operations.get(count).getOrder()-1)));
			numbers.remove(1+numbers.indexOf(getNumberWithOrder(operations.get(count).getOrder()-1)));
			operations.remove(operations.get(count));
		}
		recalculateOrder();
	}
	
	private Number getNumberWithOrder(int order){
		for(int i=0; i<numbers.size(); i++){
			if(numbers.get(i).getOrder()==order){
				return numbers.get(i);
			}
		}
		return null;
	}
	
	private Operator getOperatorWithOrder(int order){
		for(int i=0; i<operations.size(); i++){
			if(operations.get(i).getOrder()==order){
				return operations.get(i);
			}
		}
		return null;
	}
	
	private void recalculateOrder(){
		int neededOrder=-1;
		boolean forceBack=false;
		for(int i=0; i<=largestOrder(); i++){
			Number testNum=getNumberWithOrder(i);
			Operator testOp=getOperatorWithOrder(i);
			if(testNum==null&&testOp==null){
				if(neededOrder==-1){
					neededOrder=i;
				}
				forceBack=true;
			}
			if(forceBack&&neededOrder!=i){
				if(testNum==null&&testOp==null){
					//do nothing
				}else if(testNum==null){
					testOp.changeOrder(neededOrder++);
				}else if(testOp==null){
					testNum.changeOrder(neededOrder++);
				}
			}
		}
		order=largestOrder();
	}
	
	private int largestOrder(){
		int largestOrder=0;
		for(int i=0; i<numbers.size(); i++){
			if(numbers.get(i).getOrder()>largestOrder){
				largestOrder=numbers.get(i).getOrder();
			}
		}
		return largestOrder;
	}
	
	public static void main(String[] args){
		try{
			System.out.println(new Expression("-15+(-10-(5+2))+13", false).evaluate());
		}catch(SyntaxException e){
			e.printStackTrace();
		}
	}
	
}