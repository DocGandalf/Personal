package memorization.chemistry;

public class PeriodicTable {
	public Element[] elements=new Element[72];
	
	public PeriodicTable(){
		int i=-1;
		i++;elements[i]=new Element("H","Hydrogen",1,1,1,1,"1.00794");
		i++;elements[i]=new Element("He","Helium",1,18,2,0,"4.00260");
		i++;elements[i]=new Element("Li", "Lithium", 2, 1, 3, 1, "6.941");
		i++;elements[i]=new Element("Be","Berylium",2,2,4,2,"9.012182");
		i++;elements[i]=new Element("B","Boron",2,13,5,3,"10.811");
		i++;elements[i]=new Element("C", "Carbon",2,14,6,4,"12.0107");
		i++;elements[i]=new Element("N","Nitrogen",2,15,7,-3,"14.0067","nitr");
		i++;elements[i]=new Element("O","Oxygen",2,16,8,-2,"15.9994","ox");
		i++;elements[i]=new Element("F","Fluorine",2,17,9,-1,"18.99840","fluor");
		i++;elements[i]=new Element("Ne","Neon",2,18,10,0,"20.1797");
		i++;elements[i]=new Element("Na","Sodium",3,1,11,1,"22.989770");
		i++;elements[i]=new Element("Mg","Magnesium",3,2,12,2,"24.3050");
		i++;elements[i]=new Element("Al","Aluminum",3,13,13,3,"26.981538");
		i++;elements[i]=new Element("Si","Silicon",3,14,14,4,"28.0855");
		i++;elements[i]=new Element("P","Phosphorus",3,15,15,-3,"30.97376","phosph");
		i++;elements[i]=new Element("S","Sulfur",3,16,16,-2,"32.065","sulf");
		i++;elements[i]=new Element("Cl","Chlorine",3,17,17,-1,"35.453","chlor");
		i++;elements[i]=new Element("Ar","Argon",3,18,18,0,"39.948");
		i++;elements[i]=new Element("K","Potassium",4,1,19,1,"39.0983");
		i++;elements[i]=new Element("Ca","Calcium",4,2,20,2,"40.078");
		i++;elements[i]=new TransitionalElement("Sc","Scandium",4,3,21,new int[]{3},"44.95591");
		i++;elements[i]=new TransitionalElement("Ti","Titanium",4,4,22,new int[]{2,3,4},"47.867");
		i++;elements[i]=new TransitionalElement("V","Vanadium",4,5,23,new int[]{2,3,4,5},"50.9415");
		i++;elements[i]=new TransitionalElement("Cr","Chromium",4,6,24,new int[]{2,3,6},"51.996");
		i++;elements[i]=new TransitionalElement("Mn","Manganese",4,7,25,new int[]{2,3,4,7},"54.9380");
		i++;elements[i]=new TransitionalElement("Fe","Iron",4,8,26,new int[]{2,3},"55.845");
		i++;elements[i]=new TransitionalElement("Co","Cobalt",4,9,27,new int[]{2,3},"58.9332");
		i++;elements[i]=new TransitionalElement("Ni","Nickel",4,10,28,new int[]{2,3},"58.6934");
		i++;elements[i]=new TransitionalElement("Cu","Copper",4,11,29,new int[]{1,2},"63.546");
		i++;elements[i]=new TransitionalElement("Zn","Zinc",4,12,30,new int[]{2},"65.409");
		i++;elements[i]=new TransitionalElement("Ga","Gallium",4,13,31,new int[]{3},"69.723");
		i++;elements[i]=new Element("Ge","Germanium",4,14,32,4,"72.64");
		i++;elements[i]=new Element("As","Arsenic",4,15,33,-3,"74.9216","arsen");
		i++;elements[i]=new Element("Se","Selenium",4,16,34,-2,"78.96","selen");
		i++;elements[i]=new Element("Br","Bromine",4,17,35,-1,"79.904","brom");
		i++;elements[i]=new Element("Kr","Krypton",4,18,36,0,"83.80");
		i++;elements[i]=new Element("Rb","Rubidium",5,1,37,1,"85.4678");
		i++;elements[i]=new Element("Sr","Strontium",5,2,38,2,"87.62");
		i++;elements[i]=new TransitionalElement("Y","Yttrium",5,3,39,new int[]{3},"88.9059");
		i++;elements[i]=new TransitionalElement("Zr","Zirconium",5,4,40,new int[]{4},"91.224");
		i++;elements[i]=new TransitionalElement("Nb","Niobium",5,5,41,new int[]{3,5},"92.90638");
		i++;elements[i]=new TransitionalElement("Mo","Molybdenum",5,6,42,new int[]{6},"95.94");
		i++;elements[i]=new TransitionalElement("Tc","Technetium",5,7,43,new int[]{4,6,7},"98");
		i++;elements[i]=new TransitionalElement("Ru","Ruthenium",5,8,44,new int[]{3},"101.07");
		i++;elements[i]=new TransitionalElement("Rh","Rhodium",5,9,45,new int[]{3},"102.90550");
		i++;elements[i]=new TransitionalElement("Pd","Palladium",5,10,46,new int[]{2,4},"106.42");
		i++;elements[i]=new TransitionalElement("Ag","Silver",5,11,47,new int[]{1},"107.8682");
		i++;elements[i]=new TransitionalElement("Cd","Cadmium",5,12,48,new int[]{2},"112.41");
		i++;elements[i]=new TransitionalElement("In","Indium",5,13,49,new int[]{3},"114.82");
		i++;elements[i]=new TransitionalElement("Sn","Tin",5,14,50,new int[]{2,4},"118.710");
		i++;elements[i]=new Element("Sb","Antimony",5,15,51,-3,"121.700","antim");
		i++;elements[i]=new Element("Te","Tellurium",5,16,52,-2,"127.60","tellur");
		i++;elements[i]=new Element("I","Iodine",5,17,53,-1,"126,90447","iod");
		i++;elements[i]=new Element("Xe","Xenon",5,18,54,0,"131.29");
		i++;elements[i]=new Element("Cs","Cesium",6,1,55,1,"132.90545");
		i++;elements[i]=new Element("Ba","Barium",6,2,56,2,"137.327");
		i++;elements[i]=new TransitionalElement("La","Lanthanum",6,3,57,new int[]{3},"138.9055");
		i++;elements[i]=new TransitionalElement("Hf","Hafnium",6,4,72,new int[]{4},"178.49");
		i++;elements[i]=new TransitionalElement("Ta","Tantalum",6,5,73,new int[]{5},"180.9479");
		i++;elements[i]=new TransitionalElement("W","Tungsten",6,6,74,new int[]{6},"183.84");
		i++;elements[i]=new TransitionalElement("Re","Rhenium",6,7,75,new int[]{4,6,7},"186.207");
		i++;elements[i]=new TransitionalElement("Os","Osmium",6,8,76,new int[]{3,4},"190.23");
		i++;elements[i]=new TransitionalElement("Ir","Iridium",6,9,77,new int[]{3,4},"192.217");
		i++;elements[i]=new TransitionalElement("Pt","Platinum",6,10,78,new int[]{2,4},"195.08");
		i++;elements[i]=new TransitionalElement("Au","Gold",6,11,79,new int[]{1,3},"196.96655");
		i++;elements[i]=new TransitionalElement("Hg","Mercury",6,12,80,new int[]{1,2},"200.59");
		i++;elements[i]=new TransitionalElement("Tl","Thallium",6,13,81,new int[]{1,3},"204.3833");
		i++;elements[i]=new TransitionalElement("Pb","Lead",6,14,82,new int[]{2,4},"207.2");
		i++;elements[i]=new Element("Bi","Bismuth",6,15,83,-3,"208.9804","bism");
		i++;elements[i]=new Element("Po","Polonium",6,16,84,-2,"209","polon");
		i++;elements[i]=new Element("At","Astatine",6,17,85,-1,"210","astat");
		i++;elements[i]=new Element("Rn","Radon",6,18,86,0,"222");
	}
	
	class Element {
		private String atomicSymbol;
		private String elementName;
		private int period;
		private int group;
		private int atomicNumber;
		private int oxidationNumber;
		private String atomicMass;
		private String root;
		
		public Element(String sym, String eName, int row, int col, int protons, int oxNum, String atomicMass){
			atomicSymbol=sym;
			elementName=eName;
			period=row;
			group=col;
			atomicNumber=protons;
			oxidationNumber=oxNum;
			this.atomicMass=atomicMass;
		}
		
		public Element(String sym, String eName, int row, int col, int protons, int oxNum, String atomicMass, String root){
			atomicSymbol=sym;
			elementName=eName;
			period=row;
			group=col;
			atomicNumber=protons;
			oxidationNumber=oxNum;
			this.atomicMass=atomicMass;
			this.root=root;
		}
		
		public int getOxNum() throws TransitionalElementException{
			return oxidationNumber;
		}
		
	}
	
	class TransitionalElement extends Element{
		private int[] oxNums;
		
		public TransitionalElement(String sym, String eName, int row, int col, int protons, int[] oxNums, String atomicMass) {
			super(sym, eName, row, col, protons, 8, atomicMass);
			this.oxNums=oxNums;
		}
		
		@Override
		public int getOxNum() throws TransitionalElementException{
			throw new TransitionalElementException();
		}
		
		public int[] getOxNums(){
			return oxNums;
		}
		
	}
}
