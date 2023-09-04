package quantik.undo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import quantik.control.Partida;
import quantik.excepcion.CoordenadasIncorrectasException;
import quantik.modelo.Caja;
import quantik.modelo.Tablero;
import quantik.util.Color;
import quantik.util.Figura;

/**
 *	Maquina del tiempo con jugadas.
 * 
 * @author Jimena Arnaiz Gonzalez <a href="jag1033@alu.ubu.es"> Jimena </a>
 * @author Ivan Estepar Rebollo <a href="ier1003@alu.ubu.es"> Ivan </a>
 * @version 2.0
 *
 */

public class MaquinaDelTiempoConJugadas extends MaquinaDelTiempoAbstracta{
	/**
	 * Historial que almacena las jugadas realizasdas.
	 */
	protected List<Jugada> historial;
	/**
	 * Filas del tablero.
	 */
	protected int filas;
	/**
	 * Columnas del tablero.
	 */
	protected int columnas;
	/**
	 * Partida actual.
	 */
	protected Partida partida;
	/**
	 * Jugada actual.
	 */
	protected Jugada jugada;
	
	/**
	 * Constructor. 
	 * 
	 * @param fecha Fecha
	 * @param filas Filas
	 * @param columnas Columnas
	 */
	public MaquinaDelTiempoConJugadas(Date fecha, int filas, int columnas){
		super(fecha, filas, columnas);
		historial = new ArrayList<Jugada>();
	}
	
	
	@Override
	public int consultarNumeroJugadasEnHistorico() {
		return historial.size(); //jugadas almacenadas
	}

	@Override
	public Partida consultarPartidaActual() {
		partida = new Partida(new Tablero(), new Caja(Color.BLANCO), new Caja(Color.NEGRO));
		
		if(!historial.isEmpty()) {
			for (int i = 0; i < historial.size(); i++) {
				try { //haces las jugadas almacenadas (supuestamente no estando ya la borrada)
					partida.colocarPiezaEnTurnoActual(historial.get(i).consultarFila(), historial.get(i).consultarColumna(),
							historial.get(i).consultarFigura());
					partida.cambiarTurno();
				} catch (CoordenadasIncorrectasException e) {
					throw new RuntimeException("Coordenadas incorrectas" , e);
				}
			}
		} 
		return partida.clonar();
			
	} 

	@Override
	public void deshacerJugada() {
		if(!historial.isEmpty()) { //solo deshace si hay jugadas hechas 
			historial.remove(historial.size() - 1); //eliminas la última jugada hecha
		}
	}
		

	@Override
	public void hacerJugada(int fila, int columna, Figura figura, Color color) throws CoordenadasIncorrectasException{
		partida = consultarPartidaActual(); //sí
		if(partida.consultarTablero().estaEnTablero(fila, columna) == true) {
			historial.add(new Jugada(fila, columna, figura, color));
		}else {
			throw new CoordenadasIncorrectasException();
		}
	}
	
	
}









