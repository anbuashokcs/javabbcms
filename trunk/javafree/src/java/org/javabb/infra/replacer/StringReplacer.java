package org.javabb.infra.replacer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* ********************************************************* */
/* ********************************************************* */
/* ********************************************************* */
/* **************** REPLACER \o/ *************************** */
/* ************************** | **************************** */
/* ************************* / \ *************************** */
/* ********************************************************* */

public class StringReplacer {
	
	private static WordsNode main = null;
	
	private static synchronized WordsNode getMainNode ( HashMap<String, String> words ) {
		WordsNode mainNode = null;
		mainNode = main;
		
		// Alimenta a arvore de badWords
		if ( mainNode == null ) {
			for ( String key : words.keySet () ) {
				if ( mainNode == null ) {
					mainNode = new WordsNode ( key );
				} else {
					addWord ( mainNode, key );
				}
			}
			main = mainNode;
		}
		
		return mainNode;
	}
	
	/**
	 * Substitui as palavras dentro um texto.
	 * 
	 * @param input
	 * @param words
	 * @return
	 */
	public static String replace ( String input, HashMap<String, String> words ) {
		WordsNode mainNode = getMainNode ( words );
		
		char chars[] = input.toCharArray ();
		LinkedList<ComparableWord> comparables = new LinkedList<ComparableWord> ();
		StringBuffer output = new StringBuffer ();
		StringBuffer currentWord = new StringBuffer ();
		boolean startWord = true;
		// Percorre a string
		for ( char character : chars ) {
			// Busca pelas palavras iniciadas com a letra atual quando uma palavra estiver iniciando.
			if ( startWord ) {
				List<String> wordsFound = findWords ( mainNode, character );
				if ( wordsFound != null ) {
					for ( String word : wordsFound ) {
						// Inclui as palavras encontrada na lista de palavras a se verificar
						comparables.add ( new ComparableWord ( word, currentWord.toString () ) );
					}
				}
			}
			
			checkWords ( character, output, currentWord, comparables, words, false );
			startWord = ComparableWord.isStartChar ( character );
		}
		checkWords ( null, output, currentWord, comparables, words, true );
		output.append ( currentWord );
		
		return output.toString ();
	}
	
	protected static void checkWords ( Character character, StringBuffer output, StringBuffer currentWord,
			LinkedList<ComparableWord> comparables, HashMap<String, String> words, boolean eof ) {
		boolean found = false;
		// Verifica as palavras
		Iterator<ComparableWord> iterator = comparables.iterator ();
		while ( iterator.hasNext () ) {
			ComparableWord comparableWord = iterator.next ();
			int comp = comparableWord.compare ( character, eof );
			if ( comp == -1 ) {
				// Se esta palavra for diferente então remove da lista
				iterator.remove ();
			} else if ( comp == 0 ) {
				// Se a palavra for igual a atual então substitui a palavra e inicia a busca pela proxima
				output.append ( comparableWord.getPrefix () );
				output.append ( words.get ( comparableWord.getWord () ) );
				if ( character != null ) {
					output.append ( character );
				}
				comparables.removeAll ( comparables );
				currentWord.delete ( 0, currentWord.length () );
				found = true;
				break;
			}
		}
		
		if ( !found ) {
			// Se nenhuma palavra foi enconrada então inclui o caracter atual na palavra em andamento
			if ( character != null ) {
				currentWord.append ( character );
			}
			
			if ( comparables.size () == 0 ) {
				output.append ( currentWord );
				currentWord.delete ( 0, currentWord.length () );
			}
		}
	}
	
	/**
	 * Busca as palavras que iniciam com um determinado caractere na arvore.
	 * 
	 * @param node
	 * @param character
	 * @return
	 */
	protected static List<String> findWords ( WordsNode node, char character ) {
		if ( node != null && node.compareTo ( character ) == 0 ) {
			return node.getWords ();
		} else if ( node != null && node.compareTo ( character ) < 0 ) {
			WordsNode child = node.getChildrens ()[ 1 ];
			if ( child == null ) {
				return null;
			}
			return findWords ( child, character );
		} else if ( node != null && node.compareTo ( character ) > 0 ) {
			WordsNode child = node.getChildrens ()[ 0 ];
			if ( child == null ) {
				return null;
			}
			return findWords ( child, character );
		}
		
		return null;
	}
	
	/**
	 * Inclui uma plavra na arvore.
	 * 
	 * @param node
	 * @param word
	 */
	public static void addWord ( WordsNode node, String word ) {
		if ( node.compareTo ( word ) == 0 ) {
			node.addWord ( word );
			return;
		} else if ( node.compareTo ( word ) < 0 ) {
			WordsNode child = node.getChildrens ()[ 1 ];
			if ( child == null ) {
				node.setChildrens ( 1, new WordsNode ( word ) );
				return;
			}
			addWord ( child, word );
		} else if ( node.compareTo ( word ) > 0 ) {
			WordsNode child = node.getChildrens ()[ 0 ];
			if ( child == null ) {
				node.setChildrens ( 0, new WordsNode ( word ) );
				return;
			}
			addWord ( child, word );
		}
	}
	
	public static void clearWordNode () {
		main = null;
	}
}
