package quantik.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import quantik.excepcion.CoordenadasIncorrectasException;
import quantik.util.Color;
import quantik.util.Figura;

/**
 * Gestor de grupos.
 * 
 * @author Jimena Arnaiz Gonzalez <a href="jag1033@alu.ubu.es"> Jimena </a>
 * @author Ivan Estepar Rebollo <a href="ier1003@alu.ubu.es"> Ivan </a>
 * @version 2.0
 *
 */

public class GestorGrupos {
	/**
	 * Lista de grupos.
	 */
	private List<Grupo> grupos;

	/**
	 * Crea los 12 grupos con las respectivas celdas contenidas en el tablero pasado
	 * como argumento.
	 * 
	 * @param tablero Tablero
	 */
	public GestorGrupos(Tablero tablero) {
		grupos = new ArrayList<Grupo>(12);

		try {
			// filas
			for (int i = 0; i < tablero.consultarNumeroFilas(); i++) {
				List<Celda> celdas = new ArrayList<Celda>(tablero.consultarNumeroColumnas());
				for (int j = 0; j < tablero.consultarNumeroColumnas(); j++) {
					celdas.add(j, tablero.obtenerCelda(i, j));
				}
				grupos.add(i, new Grupo(celdas));
			}

			// columnas
			int z = 4;
			for (int j = 0; j < tablero.consultarNumeroColumnas(); j++) {
				List<Celda> celdas0 = new ArrayList<Celda>(tablero.consultarNumeroFilas());
				for (int i = 0; i < tablero.consultarNumeroFilas(); i++) {
					celdas0.add(i, tablero.obtenerCelda(i, j));
				}
				grupos.add(z, new Grupo(celdas0));
				z++;
			}

			// 1 cuadrante
			List<Celda> celdas1 = new ArrayList<Celda>(4);
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					celdas1.add(tablero.obtenerCelda(i, j));
				}
			}
			grupos.add(8, new Grupo(celdas1));

			// 2 cuadrante
			List<Celda> celdas2 = new ArrayList<Celda>(4);
			for (int i = 0; i < 2; i++) {
				for (int j = 2; j < 4; j++) {
					celdas2.add(tablero.obtenerCelda(i, j));
				}
			}
			grupos.add(9, new Grupo(celdas2));

			// 3 cuadrante
			List<Celda> celdas3 = new ArrayList<Celda>(4);
			for (int i = 2; i < 4; i++) {
				for (int j = 0; j < 2; j++) {
					celdas3.add(tablero.obtenerCelda(i, j));
				}
			}
			grupos.add(10, new Grupo(celdas3));

			// 4 cuadrante
			List<Celda> celdas4 = new ArrayList<Celda>(4);
			for (int i = 2; i < 4; i++) {
				for (int j = 2; j < 4; j++) {
					celdas4.add(tablero.obtenerCelda(i, j));
				}
			}
			grupos.add(11, new Grupo(celdas4));
		} catch (CoordenadasIncorrectasException e) {
			throw new RuntimeException("Coordenadas incorrectas", e);
		}
	}

	/**
	 * Comprueba la legalidad de colocar una pieza en dicha celda, en relación al
	 * resto de piezas colocadas previamente.
	 * 
	 * @param celda  Celda
	 * @param figura FIgura
	 * @param turno  Turno
	 * @return true si hay conflicto y false si no
	 */
	public boolean hayConflictoEnGruposDeCelda(Celda celda, Figura figura, Color turno) {
		List<Grupo> contenedor = new ArrayList<Grupo>(3);

		contenedor.addAll(obtenerGruposConteniendoCelda(celda));
		if (celda.estaVacia() == false) {
			return true;
		}
		for (int i = 0; i < contenedor.size(); i++) {
			if (contenedor.get(i).existeMismaPiezaDelColorContrario(figura, turno)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Comprueba si en alguno de los 12 grupos hay un grupo completo con una
	 * combinación de cuatro piezas de figura diferente.
	 * 
	 * @return true si lo hay y false si no
	 */
	public boolean hayGrupoGanador() {
		for (int i = 0; i < grupos.size(); i++) {
			if (grupos.get(i).estaCompletoConFigurasDiferentes()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Obtiene los grupos a los que pertenece cada celda.
	 * 
	 * @param celda Celda
	 * @return gruposContenidos GruposContenidos
	 */
	public List<Grupo> obtenerGruposConteniendoCelda(Celda celda) {
		List<Grupo> gruposContenidos = new ArrayList<Grupo>(3);

		for (int i = 0; i < grupos.size(); i++) {
			if (grupos.get(i).contieneCelda(celda) == true) {
				gruposContenidos.add(grupos.get(i));
			}
		}
		return gruposContenidos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(grupos);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GestorGrupos other = (GestorGrupos) obj;
		return Objects.equals(grupos, other.grupos);
	}

	@Override
	public String toString() {
		return "GestorGrupos [grupos=" + grupos + "]";
	}

}