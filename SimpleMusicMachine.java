import jm.JMC;
import jm.music.data.*;
import jm.audio.*;
import jm.util.*;

import javax.sound.midi.*;
import java.io.*;
import java.util.*;
import jm.music.tools.*;


public class SimpleMusicMachine implements JMC{


	static ArrayList<Note> notes = new ArrayList<Note>();	
	static TreeMap<String, Integer> pitches = new TreeMap<String, Integer>();
    static Random rand = new Random();
    static ArrayList<MusicNote> musicNotes = new ArrayList<MusicNote>();

	
	public static void generateNotes(){
		pitches.put("C1", C1);
		pitches.put("C2", C2);
		pitches.put("C3", C3);
		pitches.put("C4", C4);
		pitches.put("C5", C5);
		pitches.put("C6", C6);
		pitches.put("C7", C7);
		pitches.put("C8", C8);
		pitches.put("C9", C9);
		pitches.put("D1", D1);
		pitches.put("D2", D2);
		pitches.put("D3", D3);
		pitches.put("D4", D4);
		pitches.put("D5", D5);
		pitches.put("D6", D6);
		pitches.put("D7", D7);
		pitches.put("D8", D8);
		pitches.put("D9", D9);
		pitches.put("E1", E1);
		pitches.put("E2", E2);
		pitches.put("E3", E3);
		pitches.put("E4", E4);
		pitches.put("E5", E5);
		pitches.put("E6", E6);
		pitches.put("E7", E7);
		pitches.put("E8", E8);
		pitches.put("E9", E9);
		pitches.put("F1", F1);
		pitches.put("F2", F2);
		pitches.put("F3", F3);
		pitches.put("F4", F4);
		pitches.put("F5", F5);
		pitches.put("F6", F6);
		pitches.put("F7", F7);
		pitches.put("F8", F8);
		pitches.put("F9", F9);
		pitches.put("G1", G1);
		pitches.put("G2", G2);
		pitches.put("G3", G3);
		pitches.put("G4", G4);
		pitches.put("G5", G5);
		pitches.put("G6", G6);
		pitches.put("G7", G7);
		pitches.put("G8", G8);
		pitches.put("G9", G9);
		pitches.put("A1", A1);
		pitches.put("A2", A2);
		pitches.put("A3", A3);
		pitches.put("A4", A4);
		pitches.put("A5", A5);
		pitches.put("A6", A6);
		pitches.put("A7", A7);
		pitches.put("A8", A8);
		pitches.put("B1", B1);
		pitches.put("B2", B2);
		pitches.put("B3", B3);
		pitches.put("B4", B4);
		pitches.put("B5", B5);
		pitches.put("B6", B6);
		pitches.put("B7", B7);
		pitches.put("B8", B8);	
		
		ArrayList<Integer> ps = new ArrayList<Integer>(pitches.values());
//		System.out.println("Values: "+ps);
			
		for(int i = 0; i < ps.size(); i++){
			for(double k = 0.5; k <= 4.0; k+=0.5){
				notes.add(new Note(ps.get(i), k));
			}
		}
//		System.out.println("Num Combos: "+notes.size());
	}
	
	// E6,E5,G5,B6,C6
	
	public static void ff7intro(){
		int[] pitch = {
				E6, 
				E5,
				G5,
				B5,
				C6
		};
		
		Phrase phrase = new Phrase();
		
		for(int k = 0; k < pitch.length; k++){
			phrase.addNote(new Note(pitch[k], 1));
		}
		
		CPhrase c1 = new CPhrase();
		Note[] d6b5 = {new Note(D6, 1.0), new Note(B5,1.0)};
		c1.addChord(d6b5);
		
		Part pianoPart = new Part("Piano", PIANO);
		pianoPart.add(phrase);
		
		pianoPart.addCPhrase(c1);
		
		CPhrase c2 = new CPhrase();
		Note[] d6b5_2 = {new Note(D6, 4.0), new Note(B5,1.0)};
		c2.addChord(d6b5_2);
		
		pianoPart.addCPhrase(c2);
		
		
		int tempo = 170;
		
		Score daisy = new Score("Temp", tempo, pianoPart);
		daisy.setKeySignature(-1);
		daisy.setNumerator(6);	// Earlier, it was 3 and denom was 4 (3/4)
		daisy.setDenominator(4);
		
		View.notate(daisy);
		
		Write.midi(daisy, "Temp.mid");
	//	playFile();	
	}
	
	
	public static void requiem(){
		int[] p = {B5, G5, G5, D5};
		Part prt = new Part("Piano", 0, 0);
		
		CPhrase chord = new CPhrase();
		
		Note[] n = new Note[p.length];
		
		for(int k = 0; k < p.length; k++){
			n[k] = new Note(p[k], 1.0);
			n[k].setOffset(0.0 * k);
		}
		chord.addChord(n);

		prt.addCPhrase(chord);
		
		Score daisy = new Score("Chord Test");
			
		daisy.addPart(prt);
		daisy.setTempo(120);
		
		daisy.setKeySignature(-1);
		daisy.setNumerator(4);	// Earlier, it was 3 and denom was 4 (3/4)
		daisy.setDenominator(4);
		
		
		View.show(daisy);
		View.notate(daisy);
		
		Write.midi(daisy, "Temp.mid");
		
//		playFile();
	}
	
	public static void playFile(){
        try{
        	System.out.println("Playing song");
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.setSequence(MidiSystem.getSequence(new File("Temp.mid")));
            sequencer.open();
            sequencer.start();
            while(true) {
                if(sequencer.isRunning()) {
                    try {
                        Thread.sleep(1000); // Check every second
                    } catch(InterruptedException ignore) {
                        break;
                    }
                } else {
                    throw new Exception("Error playing file");
                }
            }
            // Close the MidiDevice & free resources
            sequencer.stop();
            sequencer.close();
        }
        catch(Exception e){System.out.println(e);}
	}
	
	public static void playFile(File f){
		try{
			Scanner input = new Scanner(f);
			String str = "";
			
			ArrayList<Note> inSong = new ArrayList<Note>();
						
			while( input.hasNextLine() && (str = input.nextLine()) != null && !str.equals("")){
				System.out.println("Read line: "+str);
				StringTokenizer st = new StringTokenizer(str, " ");
				
				String pitchStr = st.nextToken();
				int pitch = getPitch(pitchStr);
				double rhythm = Double.parseDouble(st.nextToken());
								
				inSong.add(new Note(pitch,rhythm));
			}
			
			int[] songScript = new int[inSong.size()];
			
			for(int k = 0; k < inSong.size(); k++){
				songScript[k] = getIndex(inSong.get(k).getPitch(), inSong.get(k).getRhythmValue());
			}
			
			teach(songScript);
			
			Note[] songNotes = new Note[inSong.size()];
			for (int i = 0; i < songNotes.length; i++) {
		  		songNotes[i] = inSong.get(i);
			}
			playSong(songNotes);
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	public static void teachFile(File f){
		try{
			Scanner input = new Scanner(f);
			String str = "";
			ArrayList<Note> inSong = new ArrayList<Note>();
			ArrayList<Double> rhythms = new ArrayList<Double>();
			while( input.hasNextLine() && (str = input.nextLine()) != null && !str.equals("")){
	//			System.out.println("Read line: "+str);
				StringTokenizer st = new StringTokenizer(str, " ");
				
				String pitchStr = st.nextToken();
				int pitch = getPitch(pitchStr);
				double rhythm = Double.parseDouble(st.nextToken());
								
				inSong.add(new Note(pitch, rhythm));
				rhythms.add(rhythm);	// idk the method to get rhythm from a note...
			}
			
			int[] songScript = new int[inSong.size()];
			
			for(int k = 0; k < inSong.size(); k++){
				songScript[k] = getIndex(inSong.get(k).getPitch(), rhythms.get(k));
			}
			
			teach(songScript);
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	public static void playSong(Note[] notes){
		Phrase phrase = new Phrase(notes);
		Part pianoPart = new Part("Piano", PIANO);
		pianoPart.add(phrase);
		int tempo = 120;
		
		Score daisy = new Score("Temp", tempo, pianoPart);
//		daisy.setKeySignature(-1);
		daisy.setNumerator(4);	// Earlier, it was 3 and denom was 4 (3/4)
		daisy.setDenominator(4);
		
		View.notate(daisy);
		
		Write.midi(daisy, "Temp.mid");
		playFile();
	}
	
	public static int getPitch(String str){
		return pitches.get(str);
	}
	
	public static int getIndex(int pitch, double rhythm){
		ArrayList<Integer> ps = new ArrayList<Integer>(pitches.values());
		
		for(int i = 0; i < ps.size(); i++){
			if(ps.get(i) == pitch){
				return (int) ( i * 8) + ( (int)(rhythm / 0.5) -1);
			}
		}
		return -1;
	}
	    
    public static void teach(int[] song){
    	for(int k = 0; k < song.length-1; k++){
    		getMusicNote(song[k]).addNext(getMusicNote(song[k+1]));
    	}
    }

    public static void playRandomSong(){
    	MusicNote mn = randomNote();
    	
    	int length = 1000;
    		
		Note[] song = new Note[length];
		Phrase phrase = new Phrase();
		
		for(int k = 0; k < length; k++){
			song[k] = notes.get(mn.i);
			phrase.addNote(song[k]);
			
			mn = mn.getNext();
			if(mn.i == -1){
				mn = randomNote();
			}
		}
		
		Part pianoPart = new Part("Piano", PIANO);
		pianoPart.add(phrase);
		Score daisy = new Score("Temp", 170, pianoPart);
		daisy.setKeySignature(-1);
		daisy.setNumerator(6);	// Earlier, it was 3 and denom was 4 (3/4)
		daisy.setDenominator(4);
		View.notate(daisy);
		Write.midi(daisy, "GeneratedSong.mid");
//		playSong(song);			// Comment this line out in Linux if you get an Audio Device Unavailable error
    }
    
    public static void randomNoise(){
    	Note[] song = new Note[100];
    	
    	for(int k = 0; k < 100; k++){
    		song[k] = notes.get(rand.nextInt(notes.size()-1));
    	}
    	playSong(song);
    }
    
    public static void randomNoise2(){
    	Note[] song = new Note[100];
    	
    	song[0] = notes.get(0);
    	
    	for(int k = 1; k < 100; k++){
    		
    		int randPitch = (rand.nextInt(5) * 2); 
    		
    		if(rand.nextInt(2) == 0){
    			randPitch*=-1;
    		}
    		
    		randPitch += song[k-1].getPitch();
    		if(randPitch <= 0){
    			randPitch = 0;
    		}
    		else if(randPitch > C8){
    			randPitch = C8;
    		}
    		
    		song[k] = new Note(randPitch, 1 );
    	}
    	playSong(song);    	
    }
    
    public static void randomNoise3(){
    	Note[] song = new Note[100];
    	Note[] song2 = new Note[100];
    	
    	song[0] = notes.get(0);
    	song2[0] = notes.get(0);
    	
    	
    	for(int k = 1; k < 100; k++){
    		
    		int randPitch = (rand.nextInt(5) * 2); 
    		
    		if(rand.nextInt(2) == 0){
    			randPitch*=-1;
    		}
    		
    		
    		int randPitch1 = randPitch+song[k-1].getPitch();
    		int randPitch2 = randPitch-song2[k-1].getPitch();
       		
    		if(randPitch1 <= 0){
    			randPitch1 = 0;
    		}
    		else if(randPitch1 > C8){
    			randPitch1 = C8;
    		}
    		
    		if(randPitch2 <= 0){
    			randPitch2 = 0;
    		}
    		else if(randPitch2 > C8){
    			randPitch2 = C8;
    		}    		
    		
    		song[k] = new Note(randPitch1, 1 );
    		
    		song2[k] = new Note(randPitch2, 1 ); 
    	}
    	
		Part prt = new Part("Piano", 0, 0);
		Phrase phrase = new Phrase(song);
		
		Part prt2 = new Part("Piano", 0, 0);
		Phrase phrase2 = new Phrase(song2);
		
		Score s = new Score("Random Noise Test");
		s.addPart(prt);
		s.addPart(prt2);
		
		s.addPart(prt);
		s.setTempo(120);
		
		s.setKeySignature(-1);
		s.setNumerator(4);	// Earlier, it was 3 and denom was 4 (3/4)
		s.setDenominator(4);
		
		
	//	View.notate(s);
		
		Write.midi(s, "Temp.mid");
    	  
		playFile();
    }
  
    public static MusicNote randomNote(){
    	return musicNotes.get(rand.nextInt(musicNotes.size()));
    }
      
    public static MusicNote getMusicNote(int i){
    	for(int k = 0; k < musicNotes.size(); k++){
    		if(musicNotes.get(k).i == i){
    			return musicNotes.get(k);
    		}
    	}
    	MusicNote mn = new MusicNote(i);
    	musicNotes.add(mn);
    	return mn;
    }
    
    static MusicNote nullNote = new MusicNote(-1);
    
    public static void main(String[] args){
    	generateNotes();
//    	int i = getIndex(G8, 4);
//    	System.out.println(i+ " " +notes.get(i));
    	
  //  	teachFile(new File("Bicycle built for two.txt"));
    	
    	playFile(new File("Bicycle built for two.txt"));
    	
 //   	playFile(new File("Requiem.txt"));
    	//teachFile(new File("Requiem.txt"));
    	
   // 	playFile(new File("RandomSong.txt"));
    
    	//requiem();	
    	//ff7intro();
   // 	randomNoise3();
    	
    	playRandomSong();
    	
//    	daisy();
//    	daisy();
    }
    
}

class MusicNote{
	int i = 0;
	
	// Need a list of nodes that can follow directly before...
	// Need a list of nodes that can follow two notes earlier...
	// ect.
	// Need a compatability value for each of those...

	ArrayList<MusicNote> nextNotes = new ArrayList<MusicNote>();
	
	public MusicNote(int index){
		i = index;
	}
	
	public void addNext(MusicNote mn){
		nextNotes.add(mn);	// A single note can be added multiple times, automatic compatability...
	}
	
	static Random rand = new Random();
	public MusicNote getNext(){
		if(nextNotes.size() > 0)
			return nextNotes.get(rand.nextInt(nextNotes.size()));
		return SimpleMusicMachine.nullNote;
	}
}