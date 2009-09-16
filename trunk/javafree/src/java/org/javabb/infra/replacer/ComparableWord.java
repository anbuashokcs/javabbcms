package org.javabb.infra.replacer;

import java.util.HashMap;

/**
 * Palavra comparavel. Esta classe facilita a comparação entra as palavras.
 * 
 * @author Fábio Vilela Franco
 * 
 */
public class ComparableWord {
	
	protected static HashMap<Character, Boolean> startChars = null;
	
	private String word = null;
	private String prefix = null;
	private int length = 0;
	private int pos = 0;
	
	public ComparableWord ( String word, String prefix ) {
		this.word = word;
		this.prefix = prefix;
		length = word.length ();
	}
	
	/**
	 * Compara o caracter passado como argumento com o caracter atual da palavra. Retorna 0 se o caracter for igual ao
	 * atual e for o ultimo caractere da palavra. Retorna 1 se o caracter for igual mas não for o ultimo. Retorna -1 se
	 * o caracter for diferente do caracter atual.
	 * 
	 * @param character
	 *            Valor para se comparar com o caractere atual da palavra.
	 * @return 0, 1 ou -1. De acordo com a explicação anterior.
	 */
	public int compare ( Character character, boolean eof ) {
		if ( character != null ) {
			character = Character.toUpperCase ( character );
		}
		
		if ( length == pos ) {
			if ( eof || isStartChar ( character ) ) {
				return 0;
			} else {
				return -1;
			}
		} else if ( !eof && character == Character.toUpperCase ( word.charAt ( pos++ ) ) ) {
			return 1;
		}
		
		return -1;
	}
	
	public String getWord () {
		return word;
	}
	
	public void setWord ( String word ) {
		this.word = word;
	}
	
	public String getPrefix () {
		return prefix;
	}
	
	public static boolean isStartChar ( Character character ) {
		if ( startChars == null ) {
			startChars = new HashMap<Character, Boolean> ();
			startChars.put ( 'q', true );
			startChars.put ( 'w', true );
			startChars.put ( 'e', true );
			startChars.put ( 'r', true );
			startChars.put ( 't', true );
			startChars.put ( 'y', true );
			startChars.put ( 'u', true );
			startChars.put ( 'i', true );
			startChars.put ( 'o', true );
			startChars.put ( 'p', true );
			startChars.put ( 'a', true );
			startChars.put ( 's', true );
			startChars.put ( 'd', true );
			startChars.put ( 'f', true );
			startChars.put ( 'g', true );
			startChars.put ( 'h', true );
			startChars.put ( 'j', true );
			startChars.put ( 'k', true );
			startChars.put ( 'l', true );
			startChars.put ( 'ç', true );
			startChars.put ( 'z', true );
			startChars.put ( 'x', true );
			startChars.put ( 'c', true );
			startChars.put ( 'v', true );
			startChars.put ( 'b', true );
			startChars.put ( 'n', true );
			startChars.put ( 'm', true );
			startChars.put ( 'Q', true );
			startChars.put ( 'W', true );
			startChars.put ( 'E', true );
			startChars.put ( 'R', true );
			startChars.put ( 'T', true );
			startChars.put ( 'Y', true );
			startChars.put ( 'U', true );
			startChars.put ( 'I', true );
			startChars.put ( 'O', true );
			startChars.put ( 'P', true );
			startChars.put ( 'A', true );
			startChars.put ( 'S', true );
			startChars.put ( 'D', true );
			startChars.put ( 'F', true );
			startChars.put ( 'G', true );
			startChars.put ( 'H', true );
			startChars.put ( 'J', true );
			startChars.put ( 'K', true );
			startChars.put ( 'L', true );
			startChars.put ( 'Ç', true );
			startChars.put ( 'Z', true );
			startChars.put ( 'X', true );
			startChars.put ( 'C', true );
			startChars.put ( 'V', true );
			startChars.put ( 'B', true );
			startChars.put ( 'N', true );
			startChars.put ( 'M', true );
			startChars.put ( 'á', true );
			startChars.put ( 'à', true );
			startChars.put ( 'â', true );
			startChars.put ( 'ã', true );
			startChars.put ( 'ä', true );
			startChars.put ( 'é', true );
			startChars.put ( 'è', true );
			startChars.put ( 'ê', true );
			startChars.put ( 'ë', true );
			startChars.put ( 'í', true );
			startChars.put ( 'ì', true );
			startChars.put ( 'î', true );
			startChars.put ( 'ï', true );
			startChars.put ( 'ó', true );
			startChars.put ( 'ò', true );
			startChars.put ( 'õ', true );
			startChars.put ( 'ô', true );
			startChars.put ( 'ö', true );
			startChars.put ( 'ú', true );
			startChars.put ( 'ù', true );
			startChars.put ( 'û', true );
			startChars.put ( 'ü', true );
			startChars.put ( 'Á', true );
			startChars.put ( 'À', true );
			startChars.put ( 'Â', true );
			startChars.put ( 'Ã', true );
			startChars.put ( 'Ä', true );
			startChars.put ( 'É', true );
			startChars.put ( 'È', true );
			startChars.put ( 'Ê', true );
			startChars.put ( 'Ë', true );
			startChars.put ( 'Í', true );
			startChars.put ( 'Ì', true );
			startChars.put ( 'Î', true );
			startChars.put ( 'Ï', true );
			startChars.put ( 'Ó', true );
			startChars.put ( 'Ò', true );
			startChars.put ( 'Ô', true );
			startChars.put ( 'Õ', true );
			startChars.put ( 'Ö', true );
			startChars.put ( 'Ú', true );
			startChars.put ( 'Ù', true );
			startChars.put ( 'Ú', true );
			startChars.put ( 'Û', true );
			startChars.put ( 'Ü', true );
		}
		
		return !startChars.containsKey ( character );
	}
}