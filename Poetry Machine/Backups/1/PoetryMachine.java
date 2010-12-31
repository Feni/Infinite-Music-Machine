import java.util.*;

public class PoetryMachine implements Runnable{
	
	
	public static void main(String args[]){
		System.out.println("Poetry Machine Version 1.0");
		new PoetryMachine();
	}
	
	public PoetryMachine(){
		tests();
		
		new Thread(this).start();
	}
	
	public void tests(){
		Word sun = new Word("sun");
		Word bun = new Word("bun");
		System.out.println("Self test: "+bun.equals(bun));
		System.out.println("Self test2: "+bun.equals(new Word("bun")));
		
		System.out.println(sun+" "+bun+" Equals: "+sun.equals(bun));
		System.out.println(sun+" "+bun+" Rhymes: "+sun.rhymesWith(bun));
		
		Word morning = new Word("morning");
		Word boring = new Word("boring");
		System.out.println(morning.rhymesWith(boring));
		System.out.println(morning.rhymesWith(bun));
		
		System.out.println("Purge: "+purge("OH! The cruel, cruel world..."));
	}
	
	public void run(){
		while(true){
			try{
				Thread.sleep(100);
				parse();
				generatePoem();
			}
			catch(Exception e){
				System.out.println(e);
			}
		}
	}
	
	ArrayList<Word> words = new ArrayList<Word>();
	
	public void parse(){
		System.out.println("Enter Input:");
		Scanner input = new Scanner(System.in);		
		String str = "";
		
		ArrayList<Word> localWords = new ArrayList<Word>();
		
//		while(!(str = input.nextLine()).equals("") && !str.equals("end")){
		while(!(str = input.nextLine()).equals("end")){
			parseLine(str);
		}
	}
	
	public String purge(String str){
		for(int k = 0; k < str.length(); k++){
			char c = str.charAt(k);
			if(c == '!' || c == '.' || c == ','){
				str = str.substring(0,k) + str.substring(k+1,str.length());
				k--;
			}
		}
		return str;
	}
	
	public void generatePoem(){
		genTwoLinePoem();
	}
	
	public void genTwoLinePoem(){
		Random rand = new Random();
		ArrayList<Word> list = BEG.after;		// List of words that can possibly come next...
		Word w = new Word("NULL");
		System.out.println();
		while(!list.contains(EOL)){
			w = list.get(rand.nextInt(list.size()));
			System.out.print(" "+w+" ");
			list = w.after;
		}
		System.out.println();
		w = w.rhymes.get(rand.nextInt(w.rhymes.size()));
		list = w.before;		// List of words that can possibly come before...
		
		String str = "";
		while(!list.contains(BEG)){
			w = list.get(rand.nextInt(list.size()));
			list = w.before;
			str = w + " " + str;
		}
		System.out.println(" 2: "+str);
	}
	
	public void genOneLinePoem(){
		Random rand = new Random();
		ArrayList<Word> next = BEG.after;
		Word w;
		System.out.println();
		while(!next.contains(EOL)){
			w = next.get(rand.nextInt(next.size()));
			System.out.print(" "+w+" ");
			next = w.after;
//			System.out.println(next);
		}
		System.out.println();		
	}
	
	/*
The dog ate the cat
the cat ate the rat
the rat ate the bat
the bat ate the mat
the mat just sat
	
	*/
	
	
	Word BEG = new Word("BEG");
	Word EOL = new Word("EOL");
	
	public void parseLine(String str){
		str = purge(str.toLowerCase());
		StringTokenizer st = new StringTokenizer(str);
		ArrayList<Word> localWords = new ArrayList<Word>();
		while(st.hasMoreTokens()){
			localWords.add(newWord(st.nextToken()));
		}
		
		for(int k = 0; k < localWords.size(); k++){
			findRhymes(localWords.get(k));
			if(k == 0){
				localWords.get(k).addBefore(BEG);
			}
			else{
				localWords.get(k).addBefore(localWords.get(k-1));
			}
			
			if(k+1 >= localWords.size()){
				localWords.get(k).addAfter(EOL);
			}
		}
	}
	
	public Word newWord(String str){
		str = str.toLowerCase();
		Word w = new Word(str);
		for(int k = 0; k < words.size(); k++){
			if(words.get(k).equals(w)){
//				System.out.println("Found a copy: "+words.get(k)+" "+w);
				return words.get(k);
			}
		}
//		System.out.println(w+" is completly original" +words);
		words.add(w);
		return w;
	}
	
	public void findRhymes(Word w){
		for(int k = 0; k < words.size(); k++){
			if(words.get(k).rhymesWith(w)){
				w.addRhyme(words.get(k));
			}
		}
	}
}

class Word implements Comparable{
	
	String word = "";
	
	ArrayList<Word> before = new ArrayList<Word>();
	ArrayList<Word> after = new ArrayList<Word>();
	ArrayList<Word> rhymes = new ArrayList<Word>();
	
	public Word(String str){
		word = str;
	}
	
	public int compareTo(Object o){
		if(o instanceof Word){
//			System.out.println("Comparing object is a word");
			return word.compareTo( ((Word) o).word);
		}
//		System.out.println("Comparing object is NOT a word");
		return -1;
	}
	
	public boolean equals(Object o){
		if(this.compareTo(o) == 0){
			return true;
		}
		return false;
	}
	
	public boolean rhymesWith(Word w){
		int i = 0;
		
		int rhymeSim = 1;
		
		if(word.length() <= w.word.length()){
//			System.out.println("Rhyme check 1");
			for(int k = 1; k < word.length(); k++){
				if(word.charAt(word.length()-k) == w.word.charAt(w.word.length()-k)){
					i++;
//					System.out.println("i = "+i);
				}
				else{
//					System.out.println("i = "+i);
					break;
				}
			}
		}
		else{
//			System.out.println("Rhyme check 2");
			for(int k = 1; k < w.word.length(); k++){
				if(word.charAt(word.length()-k) == w.word.charAt(w.word.length()-k)){
					i++;
					System.out.println("i = "+i);
				}
				else{
//				System.out.println("Rhyme broken at i = " + i );
					break;
				}
			}
		}

		if(i >= rhymeSim){		return true;		}
		else{	return false;		}
	}
	public String toString(){
		return word;
	}
	
	public void addRhyme(Word w){
		boolean found = false;
		for(int k =0; k < rhymes.size(); k++){
			if(rhymes.get(k) == w){
				found = true;
				break;
			}
		}
		if(!found){
			rhymes.add(w);
			w.addRhyme(this);
		}
	}
	
	public void addAfter(Word w){
		boolean found = false;
		for(int k =0; k < after.size(); k++){
			if(after.get(k) == w){
				found = true;
				break;
			}
		}
		if(!found){
			after.add(w);
			w.addBefore(this);
		}
	}
	public void addBefore(Word w){
		boolean found = false;
		for(int k =0; k < before.size(); k++){
			if(before.get(k) == w){
				found = true;
				break;
			}
		}
		if(!found){
			before.add(w);
			w.addAfter(this);
		}
	}	
}