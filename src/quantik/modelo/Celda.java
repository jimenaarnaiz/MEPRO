package quantik.modelo;

import java.util.Objects;

/**
 * Celda.
 * 
 * @author Jimena Arnaiz Gonzalez <a href="jag1033@alu.ubu.es"> Jimena </a>
 * @author Ivan Estepar Rebollo <a href="ier1003@alu.ubu.es"> Ivan </a>
 * @version 2.0
 *
 */

public class Celda {
	
	/**
	 * Fila.
	 */
	private int fila;
	/**
	 * Columna.
	 */
	private int columna;
	/**
	 * Pieza.
	 */
	private Pieza pieza;

	/**
	 * Constructor.
	 * 
	 * @param fila Fila de la celda
	 * @param columna Columna de la celda
	 */
	public Celda(int fila, int columna) {
		this.fila = fila;
		this.columna = columna;
	}
	
	/**
	 * Clona una celda con su pieza.
	 * 
	 * @return Clon de la celda actual
	 */
	public Celda clonar() {
		Celda celdaClon = new Celda(fila, columna);
		Pieza piezaClon = null;
		
		if (estaVacia() == true) { //si está la celda vacía, no se clona pieza(null)
			celdaClon.colocar(piezaClon);
		}
		else {
			piezaClon = pieza.clonar();
			celdaClon.colocar(piezaClon);
		}
		return celdaClon;
	}
	
	
	/**
	 * Coloca la pieza en una celda.
	 * 
	 * @param pieza Pieza a colocar
	 */
	public void colocar(Pieza pieza) {
		if(pieza != null && estaVacia() == true) {
			this.pieza = pieza;
		}
	}
	
	
	/**
	 * Devuelve la columna.
	 * 
	 * @return columna Columna
	 */
	public int consultarColumna() {
		return columna;
	}
	
	/**
	 * Devuelve la fila.
	 * 
	 * @return fila Fila
	 */
	public int consultarFila() {
		return fila;
	}
	
	/**
	 * Devuelve la pieza.
	 * 
	 * @return pieza Pieza 
	 */
	public Pieza consultarPieza() {
		if (pieza != null) {
			return pieza.clonar();
		}
		return pieza;
	}
	
	/**
	 * Comprueba si la celda está vacía.
	 * 
	 * @return true si está vacía y false en caso contrario
	 */
	public boolean estaVacia() {
		if(consultarPieza()==null) {
			return true;
		}
		return false;
		
	}

	@Override
	public int hashCode() {
		return Objects.hash(columna, fila, pieza);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Celda other = (Celda) obj;
		return columna == other.columna && fila == other.fila && Objects.equals(pieza, other.pieza);
	}

	@Override
	public String toString() {
		return "Celda [fila=" + fila + ", columna=" + columna + ", pieza=" + pieza + "]";
	}

	

	

}
