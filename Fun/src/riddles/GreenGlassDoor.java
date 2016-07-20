package riddles;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GreenGlassDoor {
	public static void main(String[] args) {
		Scanner input=new Scanner(System.in);
		System.out.println("Riddle me this!");
		int counter=0;
		System.out.println("There is a green glass door, you must tell me 6 objects in a row that will fit through the door.");
		while(counter<6){
			String object=input.nextLine();
			ArrayList<String> words=seperateIntoWords(object);
			if(doubleLetters(words)){
				counter++;
				System.out.println("A(n) "+object+" can fit through a green glass door! "+counter);
			}else{
				counter=0;
				System.out.println("A(n) "+object+" cannot fit through a green glass door. "+counter);
			}
		}
		input.close();
		System.out.println("WHAT?! You must have cheated!! My riddles are UNSOLVABLE!");
	}
	
	public static boolean doubleLetters(ArrayList<String> checker){
		ArrayList<Integer> thisWord=new ArrayList<Integer>();
		for(int i=0; i<checker.size(); i++){
			boolean doubleLetter=false;
			char lastLetter=0;
			for(int j=0; j<checker.get(i).length(); j++){
				if(lastLetter==checker.get(i).charAt(j)){
					thisWord.add(1);
					doubleLetter=true;
				}
				lastLetter=checker.get(i).charAt(j);
			}
			if(!doubleLetter){
				thisWord.add(0);
			}
		}
		for(int i=0; i<thisWord.size(); i++){
			if(thisWord.get(i)==0){
				return false;
			}
		}
		if(thisWord.size()==0){
			return false;
		}
		return true;
	}
	
	public static String mid(String string,int startIndex,int count){
		String answer="";
		for(int i=startIndex; i<count+startIndex; i++){
			answer=answer+string.charAt(i);
		}
		return answer;
	}
	
	public static ArrayList<String> seperateIntoWords(String words){
		List<String> word=new ArrayList<String>();
		int lastSpace=0;
		for(int i=0; i<words.length(); i++){
			if(words.charAt(i)==' '){
				word.add(mid(words,lastSpace,(i-lastSpace)));
				lastSpace=i+1;
			}else if(i==words.length()-1){
				word.add(mid(words,lastSpace,(words.length()-lastSpace)));	
			}
		}
		return (ArrayList<String>)word;
	}
	
}
