package memorization.spanish;

import java.io.*;
import java.util.*;
import javax.swing.JTextField;

import memorization.spanish.Attributes.letterType;

public class Translator {
	public static final String SLASH= System.getProperty("os.name").contains("Windows")? "\\":"/";
	private boolean isNoun;
	public ArrayList<String> Dictionary=new ArrayList<String>();
	private BufferedReader br;
	
	public static enum Form{
		YO,
		TU,
		EL,
		NOSOTROS,
		VOSOTROS,
		ELLOS
	}
	
	public static enum Tense{
		PRESENT,
		TENER,
		IR,
		PRESENT_PROGRESSIVE,
		COMMANDS,
		PAST,
		PRESENT_PERFECT,
		IMPERFECT
	}
	
	public static ArrayList<String> gogoVerbs=new ArrayList<String>();
	public static HashMap<String,String[]> presentIrregulars=new HashMap<String,String[]>();
	public static HashMap<String,String[]> pastIrregulars=new HashMap<String,String[]>();
	public static HashMap<String,String> commandIrregularsTuPos=new HashMap<String,String>();
	public static HashMap<String,String> commandIrregularsTuNeg=new HashMap<String,String>();
	public static HashMap<String,String> presPerfIrregulars=new HashMap<String,String>();
	public static HashMap<String,String> pastBase=new HashMap<String,String>();
	public static HashMap<String,String[]> imperfectIrregulars=new HashMap<String,String[]>();
	public static HashMap<String,Irregularity> superIrregularities=new HashMap<String,Irregularity>();
	
	static{
		//áéíóúñ¿¡
		gogoVerbs.add("tener");
		gogoVerbs.add("traer");
		gogoVerbs.add("venir");
		gogoVerbs.add("poner");
		
		presentIrregulars.put("ser", new String[]{"soy","eres","es","somos","soís","son"});
		presentIrregulars.put("estar", new String[]{"estoy","estás","está","estamos","estáis","están"});
		presentIrregulars.put("ir", new String[]{"voy","vas","va","vamos","vaís","van"});
		presentIrregulars.put("haber", new String[]{"he","has","ha","hemos","hais","han"});
		
		commandIrregularsTuPos.put("ser", "sé");
		commandIrregularsTuPos.put("ir", "ve");
		commandIrregularsTuPos.put("poner", "pon");
		commandIrregularsTuPos.put("venir","ven");
		
		commandIrregularsTuNeg.put("ser", "seas");
		commandIrregularsTuNeg.put("dar","dés");
		
		pastIrregulars.put("ser", new String[]{"fui","fuiste","fue","fuimos","fuisteis","fuieron"});
		pastIrregulars.put("ir", new String[]{"fui","fuiste","fue","fuimos","fuisteis","fuieron"});
		pastIrregulars.put("hacer", new String[]{"hice", "hicimos","hizo","hicimos","hicisteis","hicieron"});
		pastIrregulars.put("dar", new String[]{"di","diste","dio","dimos","disteis","dieron"});
		pastIrregulars.put("ver", new String[]{"vi","viste","vio","vimos","visteis","vieron"});
		
		pastBase.put("estar", "estuv");
		pastBase.put("tener", "tuv");
		pastBase.put("venir", "vin");
		pastBase.put("querer", "quis");
		pastBase.put("poder","pud");
		pastBase.put("poner","pus");
		pastBase.put("traer","traj");
		pastBase.put("decir", "dij");
		pastBase.put("conducir", "conduj");
		
		presPerfIrregulars.put("hacer", "hecho");
		presPerfIrregulars.put("morir", "muesto");
		presPerfIrregulars.put("romper", "roto");
		
		imperfectIrregulars.put("ser",new String[]{"era","eras","era","éramos","erais","eran"});
		imperfectIrregulars.put("ver",new String[]{"veía","veías","veía","veíamos","veíais","veían"});
		imperfectIrregulars.put("ir",new String[]{"iba","ibas","iba","íbamos","ibais","iban"});
		imperfectIrregulars.put("haber", new String[]{"hacía","hacía","hacía","hacía","hacía","hacía"});
		
		superIrregularities.put("seguir", new Irregularity("sigo", Form.YO, Tense.PRESENT));
		superIrregularities.put("seguir", new Irregularity("siguió", Form.EL, Tense.PAST));
		superIrregularities.put("seguir", new Irregularity("siguieron", Form.ELLOS, Tense.PAST));
		superIrregularities.put("hacer", new Irregularity("hago", Form.YO, Tense.PRESENT));
		
	}
	
	public Translator(boolean noun){
		isNoun=noun;
		try{
			br=new BufferedReader(new InputStreamReader(new FileInputStream("src"+SLASH+"memorization"+SLASH+"spanish"+SLASH+"SpanToEngDictionary.txt")));
		}catch(FileNotFoundException e){
			e.printStackTrace();
			System.exit(0);
		}
		Dictionary=loadDic(noun);
	}
	
	public boolean isNoun(){
		return isNoun;
	}
	
	public ArrayList<String> loadDic(boolean noun){
		ArrayList<String> words=new ArrayList<String>();
		try{
			if(noun){
				while(!br.readLine().equals("#Nouns")){}
			}else{
				while(!br.readLine().equals("#Verbs")){}
			}
			while(br.ready()){
				String temp=br.readLine();
				if(temp.contains("#"))break;
				words.add(temp);
			}
		}catch(IOException e){
			e.printStackTrace();
			System.exit(0);
		}
		return words;
	}
	
	public void conjugate(String word, Form form, JTextField[] answers, ArrayList<String> wordList){
		
		
		
	}
	
	public void conjugate(String word, Tense tense, JTextField[] answers, ArrayList<String> wordList){
		
		
	}
	
	private String conjugate(String word, Form form, Tense tense, ArrayList<String> wordList){
		Attributes a=Attributes.scan(word, wordList);
		if(tense==Tense.PRESENT){
			String[] checker;
			Irregularity checker2;
			if(form==Form.YO){
				//scan for irregulars
				checker=presentIrregulars.get(word);
				if(checker!=null)return checker[0];
				checker2=superIrregularities.get(word);
				if(checker2!=null){
					if(checker2.getForm()==form&&checker2.getTense()==tense){
						return checker2.getChange();
					}
				}
				boolean gogo=false;
				for(int i=0; i<gogoVerbs.size()-1;i++)if(gogoVerbs.get(i).equals(word))gogo=true;
				//make changes (e.g. shoe verb, reflexive)
				if(a.shoeVerb&&!gogo){
					word=word.substring(0, a.prefixAmount+a.shoePos)+a.shoeRule+word.substring(a.prefixAmount+a.shoePos+1, word.length());
				}
				if(a.reflexive){
					word=word.substring(0, word.length()-2);
					word="me "+word;
				}
				//apply ending
				word=word.substring(0, word.length()-2);
				if(gogo){
					if(a.lastLetter==Attributes.letterType.A){
						return word+"igo";
					}else{
						return word+"go";
					}
				}else{
					return word+"o";
				}
			}else if(form==Form.TU){
				checker=presentIrregulars.get(word);
				if(checker!=null)return checker[1];
				checker2=superIrregularities.get(word);
				if(checker2!=null){
					if(checker2.getForm()==form&&checker2.getTense()==tense){
						return checker2.getChange();
					}
				}
				if(a.shoeVerb){
					word=word.substring(0, a.prefixAmount+a.shoePos)+a.shoeRule+word.substring(a.prefixAmount+a.shoePos+1, word.length());
				}
				if(a.reflexive){
					word=word.substring(0, word.length()-2);
					word="te "+word;
				}
				if(a.verbType==Attributes.VerbType.AR){
					return word+"as";
				}else{
					return word+"es";
				}
			}else if(form==Form.EL){
				checker=presentIrregulars.get(word);
				if(checker!=null)return checker[2];
				checker2=superIrregularities.get(word);
				if(checker2!=null){
					if(checker2.getForm()==form&&checker2.getTense()==tense){
						return checker2.getChange();
					}
				}
				if(a.shoeVerb){
					word=word.substring(0, a.prefixAmount+a.shoePos)+a.shoeRule+word.substring(a.prefixAmount+a.shoePos+1, word.length());
				}
				if(a.reflexive){
					word=word.substring(0, word.length()-2);
					word="se "+word;
				}
				if(a.verbType==Attributes.VerbType.AR){
					return word+"a";
				}else{
					return word+"e";
				}
			}else if(form==Form.NOSOTROS){
				checker=presentIrregulars.get(word);
				if(checker!=null)return checker[3];
				checker2=superIrregularities.get(word);
				if(checker2!=null){
					if(checker2.getForm()==form&&checker2.getTense()==tense){
						return checker2.getChange();
					}
				}
				if(a.reflexive){
					word=word.substring(0, word.length()-2);
					word="nos "+word;
				}
				if(a.verbType==Attributes.VerbType.AR){
					return word+"amos";
				}else if(a.verbType==Attributes.VerbType.ER){
					return word+"emos";
				}else{
					return word+"imos";
				}
			}else if(form==Form.VOSOTROS){
				checker=presentIrregulars.get(word);
				if(checker!=null)return checker[4];
				checker2=superIrregularities.get(word);
				if(checker2!=null){
					if(checker2.getForm()==form&&checker2.getTense()==tense){
						return checker2.getChange();
					}
				}
				if(a.reflexive){
					word=word.substring(0, word.length()-2);
					word="os "+word;
				}
				if(a.verbType==Attributes.VerbType.AR){
					return word+"áis";
				}else if(a.verbType==Attributes.VerbType.ER){
					return word+"éis";
				}else{
					return word+"ís";
				}
			}else if(form==Form.ELLOS){
				checker=presentIrregulars.get(word);
				if(checker!=null)return checker[5];
				checker2=superIrregularities.get(word);
				if(checker2!=null){
					if(checker2.getForm()==form&&checker2.getTense()==tense){
						return checker2.getChange();
					}
				}
				if(a.shoeVerb){
					word=word.substring(0, a.prefixAmount+a.shoePos)+a.shoeRule+word.substring(a.prefixAmount+a.shoePos+1, word.length());
				}
				if(a.reflexive){
					word=word.substring(0, word.length()-2);
					word="se "+word;
				}
				if(a.verbType==Attributes.VerbType.AR){
					return word+"an";
				}else{
					return word+"en";
				}
			}
		}else if(tense==Tense.TENER){
			if(form==Form.YO){
				return conjugate("tener",form,Tense.PRESENT,wordList)+" que "+(a.reflexive? word.substring(0, word.length()-2)+"me":word);
			}else if(form==Form.TU){
				return conjugate("tener",form,Tense.PRESENT,wordList)+" que "+(a.reflexive? word.substring(0, word.length()-2)+"te":word);
			}else if(form==Form.EL){
				return conjugate("tener",form,Tense.PRESENT,wordList)+" que "+word;
			}else if(form==Form.NOSOTROS){
				return conjugate("tener",form,Tense.PRESENT,wordList)+" que "+(a.reflexive? word.substring(0, word.length()-2)+"nos":word);
			}else if(form==Form.VOSOTROS){
				return conjugate("tener",form,Tense.PRESENT,wordList)+" que "+(a.reflexive? word.substring(0, word.length()-2)+"os":word);
			}else if(form==Form.ELLOS){
				return conjugate("tener",form,Tense.PRESENT,wordList)+" que "+word;
			}
		}else if(tense==Tense.IR){
			if(form==Form.YO){
				return conjugate("ir",form,Tense.PRESENT,wordList)+" a "+(a.reflexive? word.substring(0, word.length()-2)+"me":word);
			}else if(form==Form.TU){
				return conjugate("ir",form,Tense.PRESENT,wordList)+" a "+(a.reflexive? word.substring(0, word.length()-2)+"te":word);
			}else if(form==Form.EL){
				return conjugate("ir",form,Tense.PRESENT,wordList)+" a "+word;
			}else if(form==Form.NOSOTROS){
				return conjugate("ir",form,Tense.PRESENT,wordList)+" a "+(a.reflexive? word.substring(0, word.length()-2)+"nos":word);
			}else if(form==Form.VOSOTROS){
				return conjugate("ir",form,Tense.PRESENT,wordList)+" a "+(a.reflexive? word.substring(0, word.length()-2)+"os":word);
			}else if(form==Form.ELLOS){
				return conjugate("ir",form,Tense.PRESENT,wordList)+" a "+word;
			}
		}else if(tense==Tense.PRESENT_PROGRESSIVE){
			if(form==Form.YO){
				if(a.verbType==Attributes.VerbType.AR){
					word=word.substring(0, word.length()-(a.reflexive? 4:2))+"ando";
				}else if(a.verbType==Attributes.VerbType.ER){
					word=word.substring(0, word.length()-(a.reflexive? 4:2))+"iendo";
				}else{
					word=word.substring(0, word.length()-(a.reflexive? 4:2))+"iendo";
					if(a.shoeVerb){
						String shoe="";
						if(a.shoeLetter==letterType.E){
							shoe="i";
						}else if(a.shoeLetter==letterType.O){
							shoe="u";
						}
						word=word.substring(0, a.prefixAmount+a.shoePos)+shoe+word.substring(a.prefixAmount+a.shoePos+1, word.length());
					}
				}
				return conjugate("estar",form,Tense.PRESENT,wordList)+" a "+(a.reflexive? word+"me":word);
			}else if(form==Form.TU){
				if(a.verbType==Attributes.VerbType.AR){
					word=word.substring(0, word.length()-(a.reflexive? 4:2))+"ando";
				}else if(a.verbType==Attributes.VerbType.ER){
					word=word.substring(0, word.length()-(a.reflexive? 4:2))+"iendo";
				}else{
					word=word.substring(0, word.length()-(a.reflexive? 4:2))+"iendo";
					if(a.shoeVerb){
						String shoe="";
						if(a.shoeLetter==letterType.E){
							shoe="i";
						}else if(a.shoeLetter==letterType.O){
							shoe="u";
						}
						word=word.substring(0, a.prefixAmount+a.shoePos)+shoe+word.substring(a.prefixAmount+a.shoePos+1, word.length());
					}
				}
				return conjugate("estar",form,Tense.PRESENT,wordList)+" a "+(a.reflexive? word+"te":word);
			}else if(form==Form.EL){
				if(a.verbType==Attributes.VerbType.AR){
					word=word.substring(0, word.length()-(a.reflexive? 4:2))+"ando";
				}else if(a.verbType==Attributes.VerbType.ER){
					word=word.substring(0, word.length()-(a.reflexive? 4:2))+"iendo";
				}else{
					word=word.substring(0, word.length()-(a.reflexive? 4:2))+"iendo";
					if(a.shoeVerb){
						String shoe="";
						if(a.shoeLetter==letterType.E){
							shoe="i";
						}else if(a.shoeLetter==letterType.O){
							shoe="u";
						}
						word=word.substring(0, a.prefixAmount+a.shoePos)+shoe+word.substring(a.prefixAmount+a.shoePos+1, word.length());
					}
				}
				return conjugate("estar",form,Tense.PRESENT,wordList)+" a "+word;
			}else if(form==Form.NOSOTROS){
				if(a.verbType==Attributes.VerbType.AR){
					word=word.substring(0, word.length()-(a.reflexive? 4:2))+"ando";
				}else if(a.verbType==Attributes.VerbType.ER){
					word=word.substring(0, word.length()-(a.reflexive? 4:2))+"iendo";
				}else{
					word=word.substring(0, word.length()-(a.reflexive? 4:2))+"iendo";
					if(a.shoeVerb){
						String shoe="";
						if(a.shoeLetter==letterType.E){
							shoe="i";
						}else if(a.shoeLetter==letterType.O){
							shoe="u";
						}
						word=word.substring(0, a.prefixAmount+a.shoePos)+shoe+word.substring(a.prefixAmount+a.shoePos+1, word.length());
					}
				}
				return conjugate("estar",form,Tense.PRESENT,wordList)+" a "+(a.reflexive? word+"nos":word);
			}else if(form==Form.VOSOTROS){
				if(a.verbType==Attributes.VerbType.AR){
					word=word.substring(0, word.length()-(a.reflexive? 4:2))+"ando";
				}else if(a.verbType==Attributes.VerbType.ER){
					word=word.substring(0, word.length()-(a.reflexive? 4:2))+"iendo";
				}else{
					word=word.substring(0, word.length()-(a.reflexive? 4:2))+"iendo";
					if(a.shoeVerb){
						String shoe="";
						if(a.shoeLetter==letterType.E){
							shoe="i";
						}else if(a.shoeLetter==letterType.O){
							shoe="u";
						}
						word=word.substring(0, a.prefixAmount+a.shoePos)+shoe+word.substring(a.prefixAmount+a.shoePos+1, word.length());
					}
				}
				return conjugate("estar",form,Tense.PRESENT,wordList)+" a "+(a.reflexive? word+"os":word);
			}else if(form==Form.ELLOS){
				if(a.verbType==Attributes.VerbType.AR){
					word=word.substring(0, word.length()-(a.reflexive? 4:2))+"ando";
				}else if(a.verbType==Attributes.VerbType.ER){
					word=word.substring(0, word.length()-(a.reflexive? 4:2))+"iendo";
				}else{
					word=word.substring(0, word.length()-(a.reflexive? 4:2))+"iendo";
					if(a.shoeVerb){
						String shoe="";
						if(a.shoeLetter==letterType.E){
							shoe="i";
						}else if(a.shoeLetter==letterType.O){
							shoe="u";
						}
						word=word.substring(0, a.prefixAmount+a.shoePos)+shoe+word.substring(a.prefixAmount+a.shoePos+1, word.length());
					}
				}
				return conjugate("estar",form,Tense.PRESENT,wordList)+" a "+word;
			}
		}else if(tense==Tense.COMMANDS){
			//Some sort of cycling mechanic to go through all of the commands without telling it which one it's on.
			
		}
		
		
	}
	
	
	//áéíóú
	public static String addAccent(char letter){
		if(letter=='a'){
			return "á";
		}else if(letter=='e'){
			return "é";
		}else if(letter=='i'){
			return "í";
		}else if(letter=='o'){
			return "ó";
		}else if(letter=='u'){
			return "ú";
		}
		return Character.toString(letter);
	}
	
}
