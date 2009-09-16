package org.javabb.infra.replacer;

import java.util.ArrayList;
import java.util.List;

/**
 * Nó da arvore de palavras. As palavras são agrupadas pela primeira letra.
 * 
 * @author Fabio Vilela Franco
 * 
 */
public class WordsNode {
	
	protected ArrayList<String> words = new ArrayList<String> ();
	protected char firstChar;
	protected int indexFound = 0;
	
	protected WordsNode childrens[] = new WordsNode[2];
	
	public WordsNode ( String word ) {
		words.add ( word );
		firstChar = Character.toUpperCase ( word.charAt ( 0 ) );
	}
	
	public void addWord ( String word ) {
		words.add ( word );
	}
	
	public List<String> getWords () {
		return words;
	}
	
	public WordsNode[ ] getChildrens () {
		return childrens;
	}
	
	public void setChildrens ( int position, WordsNode children ) {
		this.childrens[ position ] = children;
	}
	
	public int compareTo ( String word ) {
		return compareTo ( word.charAt ( 0 ) );
	}
	
	public int compareTo ( char wordChar ) {
		wordChar = Character.toUpperCase ( wordChar );
		if ( firstChar > wordChar ) {
			return 1;
		}
		if ( firstChar < wordChar ) {
			return -1;
		}
		return 0;
	}
}
