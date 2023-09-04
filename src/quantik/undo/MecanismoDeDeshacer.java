package quantik.undo;

import java.util.Date;

import quantik.control.Partida;
import quantik.util.Color;
import quantik.util.Figura;
import quantik.excepcion.CoordenadasIncorrectasException;

/**
 * Mecanismo de deshacer.
 * 
 * @author Jimena Arnaiz Gonzalez <a href="jag1033@alu.ubu.es"> Jimena </a>
 * @author Ivan Estepar Rebollo <a href="ier1003@alu.ubu.es"> Ivan </a>
 * @version 2.0
 *
 */
public interface MecanismoDeDeshacer {
	/**
	 * Devuelve el número de jugadas que pueden deshacerse hasta el momento. 
	 * 
	 * @return número de jugadas
	 */
	public abstract int consultarNumeroJugadasEnHistorico();
	
	/**
	 * Devuelve un clon en profundidad de la partida en el estado actual, 
	 * según se hayan hecho y deshecho las jugadas.
	 * 
	 * @return partida clon
	 */
	public abstract Partida consultarPartidaActual();
	
	/**
	 * Deshace la última jugada realizada.
	 */
	public abstract void deshacerJugada();
	
	/**
	 * Recibe la última jugada realizada para guardar sus efectos. 
	 * Si las coordenadas no están dentro del tablero, 
	 * lanza una excepción CoordenadasIncorrectasException.
	 * 
	 * @param fila Fila
	 * @param columna Columna
	 * @param figura Figura
	 * @param color Color
	 * @throws CoordenadasIncorrectasException Excepción
	 */
	public abstract void hacerJugada(int fila, int columna, Figura figura, Color color) throws CoordenadasIncorrectasException;
	
	/**
	 * Devuelve la fecha en la que se inicializa el mecanismo de deshacer.
	 * 
	 * @return fecha
	 */
	public abstract Date obtenerFechaInicio();
	
}