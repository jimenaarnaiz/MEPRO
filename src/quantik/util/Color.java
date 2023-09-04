package quantik.util;

/**
 * Enumeración color.
 * 
 * @author Jimena Arnaiz Gonzalez <a href="jag1033@alu.ubu.es"> Jimena </a>
 * @author Ivan Estepar Rebollo <a href="ier1003@alu.ubu.es"> Ivan </a>
 * @version 1.0
 *
 */

public enum Color {
	/**
	 * Color blanco.
	 */
	BLANCO('B'),
	/**
	 * Color negro.
	 */
	NEGRO('N');
	
	/**
	 * Letra asociada al color.
	 */
	private char letra;
	
	/**
	 * Constructor.
	 * 
	 * @param letra Letra que representa el color
	 */
	private Color(char letra) {
		this.letra = letra;
		
	}
	
	/**
	 * Consulta la letra del color.
	 * 
	 * @return letra del color
	 */
	public char toChar(){
		return letra;
	}
	
	/**
	 * Obtiene el color contrario.
	 * 
	 * @return color contrario
	 */
	public Color obtenerContrario(){
		return this.equals(BLANCO) ? NEGRO : BLANCO; //si el Color es BLANCO, entonces devuelve NEGRO, si no, BLANCO.
		
	}
	
	
	
	
}

