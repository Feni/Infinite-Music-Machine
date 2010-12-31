import java.util.*;

public class PoetryMachine{
	
	public static void main(String args[]){
		System.out.println("Poetry Machine Version 1.0");
		new PoetryMachine();
		teach(" The dog ate the cat ");
		teach(" The cat ate the dog ");
		
		start.getNext();
		
		System.out.println(vocabulary);
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
	
	static Random rand = new Random();
	public static void blurt(){
		Word w = start;
		System.out.print(w);
		while(!w.equals(end)){
			w = w.getNext();
			System.out.print(w);
//			System.out.println(w);
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
	
	public String toString(){
		return word+" ";
	}
}