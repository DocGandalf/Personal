package memorization.spanish;

import memorization.Spanish;
import memorization.spanish.*;
import java.util.*;

public class Attributes {
	public String rootWord="";
	public int prefixAmount=0;
	public boolean shoeVerb=false;
	public String shoeRule="";
	public letterType shoeLetter=letterType.NONE;
	public int shoePos=0;
	public boolean reflexive=false;	
	public VerbType verbType;
	public boolean accentedType=false;
	public letterType lastLetter=letterType.NONE;
	
	public static enum VerbType{
		AR,
		ER,
		IR
	}
	
	public static enum letterType{
		NONE,
		U,
		A,
		E,
		O,
		GAR,
		CAR,
		ZAR
	}
	
	private static boolean tryAccent(String lastLetters, String type, Attributes a){
		if(lastLetters.equals(type)){
			return true;
		}else if(lastLetters.equals(Translator.addAccent(type.charAt(0))+"r")){
			a.accentedType=true;
			return true;
		}
		return false;
	}
	
	private static int searchForRootWord(String word, ArrayList<String> wordList){
		for(int i=0;i<wordList.size()-1;i++){
			if(i%2==0){
				if(word.contains(Spanish.removeFlags(wordList.get(i)))){
					return i;
				}
			}
		}
		return -1;
	}
	
	private static int findFirstVowel(String word, Attributes a){
		for(int i=1;i<word.length()-1;i++){//skip first letter for words like almorzar.
			char l=word.charAt(i);
			if(isVowel(l)){
				//get vowel letter
				if(l=='o'||l=='�'){
					a.shoeLetter=letterType.O;
				}else if(l=='e'||l=='�'){
					a.shoeLetter=letterType.E;
				}
				return i;
			}
		}
		return -1;//This will eventually throw an error, which means the word is wrong
	}
	//�����
	private static boolean isVowel(char c){
		if(c=='a'||Translator.addAccent(c).toCharArray()[0]=='�'||c=='e'||Translator.addAccent(c).toCharArray()[0]=='�'||c=='i'||Translator.addAccent(c).toCharArray()[0]=='�'||c=='o'||Translator.addAccent(c).toCharArray()[0]=='�'||c=='u'||Translator.addAccent(c).toCharArray()[0]=='�')return true;
		return false;
	}
	
	public static Attributes scan(String word, ArrayList<String> wordList){
		Attributes a=new Attributes();
		int pos=searchForRootWord(word, wordList);
		if(pos>=0){
			a.rootWord=wordList.get(pos);
			a.prefixAmount=word.indexOf(Spanish.removeFlags(a.rootWord));//May want to remove a.rootWord to condense further------------------------------
			word=a.rootWord;
		}
		
		if(word.contains("(")){
			a.shoeVerb=true;
			a.shoeRule=word.substring(word.indexOf('(')+1, word.lastIndexOf(')'));
			a.shoePos=findFirstVowel(word, a)+a.prefixAmount;
			word=word.substring(0, word.indexOf('('));
		}
		
		if(word.endsWith("se")){
			a.reflexive=true;
			word=word.substring(0, word.length()-2);
		}
		
		String lastLetters=word.substring(word.length()-2, word.length());
		if(tryAccent(lastLetters, "ar", a)){
			a.verbType=VerbType.AR;
		}else if(tryAccent(lastLetters, "er", a)){
			a.verbType=VerbType.ER;
		}else if(tryAccent(lastLetters, "ir", a)){
			a.verbType=VerbType.IR;
		}
		word=word.substring(0, word.length()-2);
		
		char lastLetter=word.charAt(word.length()-1);
		if(isVowel(lastLetter)){
			if(lastLetter=='i'||lastLetter=='�'||(a.shoeVerb&&word.length()-1==a.shoePos)){
			}else if(lastLetter=='u'||lastLetter=='�'){
				a.lastLetter=letterType.U;
			}else if(lastLetter=='a'||lastLetter=='�'){
				a.lastLetter=letterType.A;
			}else if(lastLetter=='e'||lastLetter=='�'){
				a.lastLetter=letterType.E;
			}else{
				a.lastLetter=letterType.O;
			}
		}
		if(a.verbType==VerbType.AR){
			if(lastLetter=='g'){
				a.lastLetter=letterType.GAR;
			}else if(lastLetter=='c'){
				a.lastLetter=letterType.CAR;
			}else if(lastLetter=='z'){
				a.lastLetter=letterType.ZAR;
			}
		}
		return a;
	}
	
}
