import java.util.*;

public class PoetryMachine{
	
	public static void main(String args[]){
		System.out.println("Poetry Machine Version 1.0");
		new PoetryMachine();
		//teach(" The dog ate the cat ");
		//teach(" The cat ate the dog ");
		
		ArrayList<String> poem = new ArrayList<String>();
		
//		poem.add("TWO roads diverged in a yellow wood");	
//		poem.add("And sorry I could not travel both");	
		poem.add("And be one traveler long I stood");	
		poem.add("And looked down one as far as I could");	
//		poem.add("To where it bent in the undergrowth");
		 
//		poem.add("Then took the other, as just as fair");	
//		poem.add("And having perhaps the better claim");	
		poem.add("Because it was grassy and wanted wear");	
		poem.add("Though as for that the passing there");	
//		poem.add("Had worn them really about the same");
		 
//		poem.add("And both that morning equally lay");
//		poem.add("In leaves no step had trodden black");	
		poem.add("	Oh I kept the first for another day");	
		poem.add("Yet knowing how way leads on to way");	
//		poem.add("I doubted if I should ever come back");
		 
//		poem.add("	I shall be telling this with a sigh");	
//		poem.add("Somewhere ages and ages hence");	
		poem.add("Two roads diverged in a wood, and I");	
		poem.add("I took the one less traveled by");	
//		poem.add("And that has made all the difference");
		
//		start.getNext();
		
//		System.out.println(vocabulary);
		teach(poem);
		blurt();
	}
	
	public PoetryMachine(){	}
	static ArrayList<Word> vocabulary = new ArrayList<Word>();
	static Word start = new Word("Start");
	static Word end = new Word("End");
	
	public static void teach(String str){
		StringTokenizer st = new StringTokenizer(str);
		Word previous = start;
		
		while(st.hasMoreTokens()){
			Word wrd = newWord(st.nextToken().toLowerCase());
			wrd.addPrevious(previous);
			previous.addNext(wrd);
			previous = wrd;
		}
		previous.addNext(end);
	}
	
	public static void teach(ArrayList<String> poem){
		Word lastRhyme = end;
		for(int k = 0; k < poem.size(); k++){
			StringTokenizer st = new StringTokenizer(poem.get(k));
			Word previous = start;
			
			while(st.hasMoreTokens()){
				Word wrd = newWord(st.nextToken().toLowerCase());
				wrd.addPrevious(previous);
				previous.addNext(wrd);
				previous = wrd;
			}
			
			if(lastRhyme.equals(end)){
			//	System.out.println("Rhyming with end");
				lastRhyme = previous;
			}
			else{
//				System.out.println(lastRhyme+" Rhymes with "+previous);
				lastRhyme.addRhyme(previous);
				previous.addRhyme(lastRhyme);
				lastRhyme = end;
			}
			previous.addNext(end);				
		}
	}
	
	static Random rand = new Random();
	
	public static void blurt(){
		for(int k = 0; k < 10; k++){
			Word w = start;
			Word temp = w;
			while(!w.equals(end)){
				w = w.getNext();
				
				if(!w.equals(end)){
					System.out.print(w);
					temp = w;
				}
			}
			System.out.println();
//			System.out.println("2: ");
			String str = "";
			while(!w.equals(start)){
				//System.out.println("W = "+w+" pre "+w.previous);
				if(w.equals(end)){
					w = temp.getRhyme();
				}
				else{
					w = w.getPrevious();
				}
				if(!w.equals(start)){
					str = w.toString()+" "+str;
				}
			}
			System.out.println(str);
		}
	}
	
	public static Word newWord(String str){
		for(int k = 0; k < vocabulary.size(); k++){
			if(vocabulary.get(k).word.equals(str)){
				return vocabulary.get(k);
			}
		}
		Word w = new Word(str);
		vocabulary.add(w);
//		System.out.println("new vocab word: "+w);
		return w;
	}
}

class Word implements Comparable{
	ArrayList<Word> previous = new ArrayList<Word>();
	ArrayList<Word> next = new ArrayList<Word>();
	ArrayList<Word> rhymes = new ArrayList<Word>();
	String word = "";
	public Word(String str){
		word = str;
	}
	
	public int compareTo(Object obj){
		Word w = (Word) obj;
		return word.compareTo(w.word);
	}
	
	public void addPrevious(Word w){
		previous.add(w);
	}
	
	public void addNext(Word w){
		next.add(w);
	}
	
	public void addRhyme(Word w){
		rhymes.add(w);
	}
	
	Random rand = new Random();
	public Word getPrevious(){
		if(previous.size() != 0)
			return previous.get(rand.nextInt(previous.size()));
		return PoetryMachine.end;
	}
	
	public Word getNext(){
//		System.out.println("Nexts "+next);
		if(next.size() != 0){
			Word w = next.get(rand.nextInt(next.size()));
//			System.out.println("Picked: "+w);
			return w;
		}
		return PoetryMachine.end;
	}
	
	public Word getRhyme(){
		if(next.size() != 0){
			return rhymes.get(rand.nextInt(rhymes.size()));
		}
		System.out.println("No Rhymes");
		return PoetryMachine.end;
	}
	
	public String toString(){
		return word+" ";
	}
}