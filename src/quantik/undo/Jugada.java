package quantik.undo;

import quantik.util.Color;
import quantik.util.Figura;

/**
 * Jugada.
 * 
 * @author Jimena Arnaiz Gonzalez <a href="jag1033@alu.ubu.es"> Jimena </a>
 * @author Ivan Estepar Rebollo <a href="ier1003@alu.ubu.es"> Ivan </a>
 * @version 2.0
 *
 */

public class Jugada {
	/**
	 * fila.
	 */
	private final int fila;
	/**
	 * columna.
	 */
	private final int columna;
	/**
	 * figura.
	 */
	private final Figura figura;
	/**
	 * color.
	 */
	private final Color color;
	
	
	/**
	 * Constructor que inicializa jugada.
	 * 
	 * @param fila Fila 
	 * @param columna Columna
	 * @param figura Figura
	 * @param color Color
	 */
	public Jugada(int fila, int columna, Figura figura, Color color) {
		this.fila = fila;
		this.columna = columna;
		this.figura = figura;
		this.color = color;
	}
	
	/**
	 * Retorna la fila.
	 * 
	 * @return fila
	 */
	public int consultarFila() {
		return fila;
	}
	
	/**
	 * Retorna la columna.
	 * 
	 * @return columna
	 */
	public int consultarColumna() {
		return columna;
	}
	
	/**
	 * Retorna la figura.
	 * 
	 * @return figura
	 */
	public Figura consultarFigura() {
		return figura;
	}
	
	/**
	 * Retorna el color.
	 * 
	 * @return color
	 */
	public Color consultarColor() {
		return color;
	}
}
