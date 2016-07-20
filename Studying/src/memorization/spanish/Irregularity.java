package memorization.spanish;

public class Irregularity {
	private String change;
	private Translator.Form form;
	private Translator.Tense tense;
	
	public Irregularity(String change, Translator.Form form, Translator.Tense tense){
		this.change=change;
		this.form=form;
		this.tense=tense;
	}
	
	public String getChange(){
		return change;
	}
	
	public Translator.Form getForm(){
		return form;
	}
	
	public Translator.Tense getTense(){
		return tense;
	}
}
