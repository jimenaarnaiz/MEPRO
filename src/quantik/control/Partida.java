package quantik.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import quantik.excepcion.CoordenadasIncorrectasException;
import quantik.modelo.Caja;
import quantik.modelo.Celda;
import quantik.modelo.GestorGrupos;
import quantik.modelo.Pieza;
import quantik.modelo.Tablero;
import quantik.util.Color;
import quantik.util.Figura;

/**
 * Partida.
 * 
 * @author Jimena Arnaiz Gonzalez <a href="jag1033@alu.ubu.es"> Jimena </a>
 * @author Ivan Estepar Rebollo <a href="ier1003@alu.ubu.es"> Ivan </a>
 * @version 2.0
 *
 */

public class Partida {
	/**
	 * Tablero.
	 */
	private Tablero tablero;
	/**
	 * Caja del jugador blanco.
	 */
	private Caja cajaBlancas;
	/**
	 * Caja del jugador negro.
	 */
	private Caja cajaNegras;
	/**
	 * Turno actual.
	 */
	private Color turnoActual;
	/**
	 * Gestor de los grupos del tablero.
	 */
	private GestorGrupos gestor;
	/**
	 * Número de jugadas hechas.
	 */
	private int jugadas;

	/**
	 * Constructor de partida.
	 * 
	 * @param tablero     Tablero
	 * @param cajaBlancas Caja de piezas blancas
	 * @param cajaNegras  Caja de piezas negras
	 */
	public Partida(Tablero tablero, Caja cajaBlancas, Caja cajaNegras) {
		this.tablero = tablero;
		this.cajaBlancas = cajaBlancas;
		this.cajaNegras = cajaNegras;

		gestor = new GestorGrupos(tablero);
		turnoActual = Color.BLANCO;
	}

	/**
	 * Cambia el turno al otro jugador.
	 */
	public void cambiarTurno() {
		if (turnoActual == Color.BLANCO) {
			turnoActual = Color.NEGRO;
			estaBloqueadoTurnoActual();
		} else {
			turnoActual = Color.BLANCO;
			estaBloqueadoTurnoActual();
		}
	}

	/**
	 * Devuelve el número de celdas que quedan libres(sin pieza) en el tablero.
	 * 
	 * @return int contador
	 */
	private int celdasLibres() {
		int cont = 0;
		// celdas libres
		for (int i = 0; i < tablero.consultarNumeroFilas(); i++) {
			for (int j = 0; j < tablero.consultarNumeroColumnas(); j++) {
				try {
					if (tablero.consultarCelda(i, j).estaVacia()) {
						cont++;
					}
				} catch (CoordenadasIncorrectasException e) {
					throw new RuntimeException("Coordenadas incorrectas", e);
				}
			}
		}
		return cont;
	}

	/**
	 * Comprueba si se produce un bloqueo en el turno actual.
	 * 
	 * @return true si hay bloqueo, false si no
	 */
	private boolean hayBloqueo() {
		List<Pieza> piezasRestantes;

		if (turnoActual == Color.BLANCO) {
			piezasRestantes = cajaBlancas.consultarPiezasDisponibles();
		} else {
			piezasRestantes = cajaNegras.consultarPiezasDisponibles();
		}

		List<Celda> celdasLibres = new ArrayList<Celda>(celdasLibres());
		int z = 0;
		// rellenamos la lista de celdasLibres
		for (int i = 0; i < tablero.consultarNumeroFilas(); i++) {
			for (int j = 0; j < tablero.consultarNumeroColumnas(); j++) {
				try {
					if (tablero.consultarCelda(i, j).estaVacia() && tablero.consultarCelda(i, j) != null) {
						celdasLibres.add(z, tablero.consultarCelda(i, j));
						z++;
					}
				} catch (CoordenadasIncorrectasException e) {
					throw new RuntimeException("Coordenadas incorrectas", e);
				}
			}
		}
		// comprobamos si las piezas que le quedan al turno actual pueden colocarse
		// en las celdas vacías restantes (no hay bloqueo)
		if (turnoActual == Color.BLANCO) {
			for (int i = 0; i < cajaBlancas.consultarPiezasDisponibles().size(); i++) {
				for (int j = 0; j < celdasLibres(); j++) {
					if (!gestor.hayConflictoEnGruposDeCelda(celdasLibres.get(j),
							piezasRestantes.get(i).consultarFigura(), turnoActual)) {
						return false;
					}
				}
			}
		} else {
			if (turnoActual == Color.NEGRO) {
				for (int i = 0; i < cajaNegras.consultarPiezasDisponibles().size(); i++) {
					for (int j = 0; j < celdasLibres(); j++) {
						if (!gestor.hayConflictoEnGruposDeCelda(celdasLibres.get(j),
								piezasRestantes.get(i).consultarFigura(), turnoActual)) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * Coloca una pieza del turno actual en las coordenadas indicadas.
	 * 
	 * @param fila    Fila
	 * @param columna Columna
	 * @param figura  Figura
	 * @throws CoordenadasIncorrectasException Excepción
	 */
	public void colocarPiezaEnTurnoActual(int fila, int columna, Figura figura) throws CoordenadasIncorrectasException {
		Pieza pieza = new Pieza(figura, turnoActual);

		if (turnoActual == cajaBlancas.consultarColor()) {
			if (esJugadaLegalEnTurnoActual(fila, columna, figura)) {
				tablero.colocar(fila, columna, pieza);
				cajaBlancas.retirar(figura);
				jugadas++;
			} else {
				throw new CoordenadasIncorrectasException();
			}
		}

		if (turnoActual == cajaNegras.consultarColor()) {
			if (esJugadaLegalEnTurnoActual(fila, columna, figura)) {
				tablero.colocar(fila, columna, pieza);
				cajaNegras.retirar(figura);
				jugadas++;
			} else {
				throw new CoordenadasIncorrectasException();
			}
		}
	}

	/**
	 * Consulta el turno concreto que ha ganado la partida, habiendo comprobado
	 * antes si esta ha terminado.
	 * 
	 * @return devuelve el turno que ha ganado, y null si no hay ganador
	 */
	public Color consultarGanador() {
		if (estaAcabadaPartida() == true) {
			if (estaLleno() == true) {
				cambiarTurno();
				return turnoActual;
			}
			if (hayAlgunGrupoCompleto()) {
				return consultarTurno();
			}
			if (sinPiezas() == true || estaBloqueadoTurnoActual() == true) {
				return turnoActual.obtenerContrario();
			}
		}
		return null;
	}

	/**
	 * Devuelve el número de jugadas hechas.
	 * 
	 * @return jugadas
	 */
	public int consultarNumeroJugada() {
		return jugadas;
	}

	/**
	 * Devuelve un clon en profundidad del tablero.
	 * 
	 * @return tablero clon
	 */
	public Tablero consultarTablero() {
		return tablero.clonar();
	}

	/**
	 * Devuelve el turno actual que puede realizar la siguiente jugada.
	 * 
	 * @return turno actual
	 */
	public Color consultarTurno() {
		return turnoActual;
	}

	/**
	 * Devuelve un clon en profundidad de la caja de piezas blancas.
	 * 
	 * @return caja blanca clon
	 */
	public Caja consultarCajaBlancas() {
		return cajaBlancas.clonar();
	}

	/**
	 * Devuelve un clon en profundidad de la caja de piezas negras.
	 * 
	 * @return caja negra clon
	 */
	public Caja consultarCajaNegras() {
		return cajaNegras.clonar();
	}

	/**
	 * Comprueba que la celda pasada por parámetro está en el tablero.
	 * 
	 * @param fila    Fila
	 * @param columna Columna
	 * @return true si está en tablero y false si no
	 */
	private boolean estaEnTablero(int fila, int columna) {
		if (fila >= 0 && fila <= 3 && columna >= 0 && columna <= 3) {
			return true;
		}
		return false;
	}

	/**
	 * Comprueba si es legal colocar una figura en las coordenadas indicadas, con el
	 * turno actual.
	 * 
	 * @param fila    Fila
	 * @param columna Columna
	 * @param figura  Figura
	 * @return true si es legal y false si no
	 */
	public boolean esJugadaLegalEnTurnoActual(int fila, int columna, Figura figura) {
		Celda celda = null;

		if (estaEnTablero(fila, columna)) {
			try {
				celda = tablero.consultarCelda(fila, columna);

				if (tablero.consultarCelda(fila, columna) != null) {
					if (turnoActual == Color.BLANCO && estaEnTablero(fila, columna) == true
							&& cajaBlancas.estaDisponible(figura)
							&& !gestor.hayConflictoEnGruposDeCelda(celda, figura, turnoActual)) {
						return true;
					}
					if (turnoActual == Color.NEGRO && estaEnTablero(fila, columna) == true
							&& cajaNegras.estaDisponible(figura)
							&& !gestor.hayConflictoEnGruposDeCelda(celda, figura, turnoActual)) {
						return true;
					}
				}
			} catch (CoordenadasIncorrectasException e) {
				throw new RuntimeException("Coordenadas incorrectas", e);
			}
		}
		return false;
	}

	/**
	 * Comprueba si el tablero se encuentra lleno.
	 * 
	 * @return true si lo está, false si no
	 */
	private boolean estaLleno() {
		if (cajaBlancas.contarPiezasActuales() == 0 && cajaNegras.contarPiezasActuales() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Comprueba si el jugador de turno actual no tiene piezas.
	 * 
	 * @return true si no tiene piezas y false en caso contrario
	 */
	private boolean sinPiezas() {
		if (turnoActual == Color.BLANCO && cajaBlancas.contarPiezasActuales() == 0) {
			return true;
		}
		if (turnoActual == Color.NEGRO && cajaNegras.contarPiezasActuales() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * Comprueba si la partida está acabada por alguna de la condiciones de
	 * finalización.
	 * 
	 * @return true si ha temrminado y false si no
	 */
	public boolean estaAcabadaPartida() {
		if (hayAlgunGrupoCompleto() == true) {
			return true;
		}
		if (estaLleno() == true) {
			return true;
		}
		if (sinPiezas() == true) {
			return true;
		}
		if (estaBloqueadoTurnoActual() == true) {
			return true;
		}
		return false;
	}

	/**
	 * Comprueba si el jugador con turno actual no puede colocar ninguna de sus
	 * piezas.
	 * 
	 * @return true si está bloqueado y false si no
	 */
	public boolean estaBloqueadoTurnoActual() {
		if (hayBloqueo() == true) {
			return true;
		}
		return false;
	}

	/**
	 * Comprueba si alguno de los grupos tiene cuatro piezas diferentes con
	 * independencia del color.
	 * 
	 * @return true si hay alguno completo, false si no
	 */
	public boolean hayAlgunGrupoCompleto() {
		if (gestor.hayGrupoGanador() == true) {
			return true;
		}
		return false;

	}

	/**
	 * Clona la partida actual.
	 * 
	 * @return partida clon
	 */
	public Partida clonar() {
		Partida partida = new Partida(tablero.clonar(), cajaBlancas.clonar(), cajaNegras.clonar());
		partida.turnoActual = turnoActual;
		partida.jugadas = jugadas;
		partida.gestor = gestor;
		return partida;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cajaBlancas, cajaNegras, gestor, jugadas, tablero, turnoActual);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Partida other = (Partida) obj;
		return Objects.equals(cajaBlancas, other.cajaBlancas) && Objects.equals(cajaNegras, other.cajaNegras)
				&& Objects.equals(gestor, other.gestor) && jugadas == other.jugadas
				&& Objects.equals(tablero, other.tablero) && turnoActual == other.turnoActual;
	}

	@Override
	public String toString() {
		return "Partida [tablero=" + tablero + ", cajaBlancas=" + cajaBlancas + ", cajaNegras=" + cajaNegras
				+ ", turnoActual=" + turnoActual + ", gestor=" + gestor + ", jugadas=" + jugadas + "]";
	}

}
