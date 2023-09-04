package quantik.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import quantik.util.Color;
import quantik.util.Figura;

/**
 * Caja.
 * 
 * @author Jimena Arnaiz Gonzalez <a href="jag1033@alu.ubu.es"> Jimena </a>
 * @author Ivan Estepar Rebollo <a href="ier1003@alu.ubu.es"> Ivan </a>
 * @version 2.0
 *
 */

public class Caja {
	/**
	 * Color.
	 */
	private Color color;
	/**
	 * Lista de piezas.
	 */
	private List<Pieza> piezas;

	/**
	 * Inicializa las piezas con sus figuras y colores.
	 * 
	 * @param color Color
	 */
	public Caja(Color color) {
		piezas = new ArrayList<Pieza>();
		this.color = color;
		int j = 0;

		for (Figura figuras : Figura.values()) {
			for (int i = 0; i < 2; i++, j++) { // de 8 figuras que hay, 2 de ellas se repiten
				piezas.add(j, new Pieza(figuras, color));
			}
		}
	}

	/**
	 * Inicializa las piezas con sus figuras y colores.
	 * 
	 * @param color  Color
	 * @param piezas Piezas
	 */
	private Caja(Color color, List<Pieza> piezas) {
		this.color = color;
		this.piezas = new ArrayList<Pieza>();

		for (Pieza i : piezas) {
			this.piezas.add(i);
		}
	}

	/**
	 * Función que clona la caja en profundidad.
	 * 
	 * @return nueva caja
	 */
	public Caja clonar() {
		return new Caja(color, consultarPiezasDisponibles());
	}

	/**
	 * Devuelve un array con clones en profundidad de la piezas disponibles en la caja.
	 * 
	 * @return clon de piezas
	 */
	public List<Pieza> consultarPiezasDisponibles() {
		List<Pieza> copia = new ArrayList<Pieza>();

		for (Pieza pieza : piezas) {
			if (pieza != null) {
				copia.add(pieza.clonar());
			}
		}
		return copia;
	}

	/**
	 * Cuenta las piezas actuales en la caja.
	 * 
	 * @return numero de piezas
	 */
	public int contarPiezasActuales() {
		int numeroPiezas = 0;
		for (int i = 0; i < piezas.size(); i++) {
			if (piezas.get(i) != null) {
				numeroPiezas++;
			}
		}
		return numeroPiezas;
	}

	/**
	 * Devuelve el color de la pieza.
	 * 
	 * @return color
	 */
	public Color consultarColor() {
		return color;
	}

	/**
	 * Comprueba si hay una pieza disponible.
	 * 
	 * @param figura Figura
	 * @return true si hay y false si no hay
	 */
	public boolean estaDisponible(Figura figura) {
		for (Pieza pieza : piezas) {
			if (pieza != null) {
				if (pieza.consultarFigura() == figura) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Extrae una pieza con dicha figura en la caja.
	 * 
	 * @param figura Figura
	 * @return pieza Pieza y si no hay una pieza disponible devuelve null
	 */

	public Pieza retirar(Figura figura) {
		Pieza retiraPieza = null;
		int i = 0;
		
		for (Pieza pieza : piezas) {
			if (pieza != null) {
				if (pieza.consultarFigura() == figura) {
					retiraPieza = pieza;
					piezas.set(i, null);
					break;
				}
			}
			i++;
		}
		return retiraPieza;
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, piezas);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Caja other = (Caja) obj;
		return color == other.color && Objects.equals(piezas, other.piezas);
	}

}