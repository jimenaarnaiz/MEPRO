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
 * Máquina del tiempo con partidas.
 * 
 * @author Jimena Arnaiz Gonzalez <a href="jag1033@alu.ubu.es"> Jimena </a>
 * @author Ivan Estepar Rebollo <a href="ier1003@alu.ubu.es"> Ivan </a>
 * @version 2.0
 *
 */
public class MaquinaDelTiempoConPartidas extends MaquinaDelTiempoAbstracta {
	/**
	 * Historial que almacena las partidas realizasdas.
	 */
	protected List<Partida> historial;
	/**
	 * Partida actual.
	 */
	protected Partida partida;
	
	/**
	 * Constructor.
	 * 
	 * @param fecha Fecha
	 * @param filas Filas
	 * @param columnas Columnas
	 */
	public MaquinaDelTiempoConPartidas(Date fecha, int filas, int columnas) {
		super(fecha, filas, columnas);
		historial = new ArrayList<Partida>();
	
	}
	
	
	@Override
	public int consultarNumeroJugadasEnHistorico() {
		return historial.size();
	}
	

	@Override
	public Partida consultarPartidaActual() {
		partida = new Partida(new Tablero(), new Caja(Color.BLANCO), new Caja(Color.NEGRO));
		
		if(!historial.isEmpty()) {
			partida = historial.get(historial.size()-1);
		}
		return partida.clonar();
	}
	

	@Override
	public void deshacerJugada(){
		if(!historial.isEmpty()) { //solo deshace si hay jugadas hechas 
			historial.remove(historial.size() - 1); //eliminas la última jugada hecha
		}
	}

	
	@Override
	public void hacerJugada(int fila, int columna, Figura figura, Color color) throws CoordenadasIncorrectasException{
			partida = consultarPartidaActual();
			if(partida.consultarTablero().estaEnTablero(fila, columna) == true) {
				partida.colocarPiezaEnTurnoActual(fila, columna, figura);
				partida.cambiarTurno();
				historial.add(partida);
			}else {
				throw new CoordenadasIncorrectasException();
			}
		}
	
	
	
}
