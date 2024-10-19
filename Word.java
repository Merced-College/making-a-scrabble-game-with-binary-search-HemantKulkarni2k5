//luis FLores Taichi Ite, Word Scrabble game

public class Word implements Comparable<word>{
    private String word; 
    
    public Word(String word) {
        this.word = word;
    }
    
    public Word() {
        this.word = "no word";
    }
    
    public String getWord(){
        return word; 
    }

    public int compareTo(Word other){
        return this.word.compareTo(other.word);
    }

    public String toString(){
        return "Word: " + word;
    }
}