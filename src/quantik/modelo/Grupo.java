package quantik.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import quantik.util.Color;
import quantik.util.Figura;


/**
* Grupo.
* 
* @author Jimena Arnaiz Gonzalez <a href="jag1033@alu.ubu.es"> Jimena </a>
* @author Ivan Estepar Rebollo <a href="ier1003@alu.ubu.es"> Ivan </a>
* @version 2.0
*
**/

public class Grupo {
	/**
	 * Celdas del grupo.
	 */
	private List<Celda> celdas;
	
	/**
	 * Constructor. Se inicializa con las referencias a las 
	 * cuatro celdas del tablero que conforman el grupo.
	 * 
	 * @param celdas Celdas
	 */
	public Grupo(List<Celda> celdas){
		this.celdas = new ArrayList<Celda>(celdas.size());
		this.celdas = celdas;
	}
	
	
	/**
	 * Devuelve un clon en profundidad del grupo actual.
	 * 
	 * @return nuevo grupo clon
	 */
	public Grupo clonar() {
		List<Celda> celdasClon = new ArrayList<Celda>(celdas.size());
		
		for(Celda celda: celdas)
			if (!celda.estaVacia()) {
				celdasClon.add(celda.clonar());
			}
		return new Grupo(celdasClon);
	}
	
	/**
	 * Devuelve el número de celdas que componen actualmente el grupo.
	 * 
	 * @return celdas Celdas
	 */
	public int consultarNumeroCeldas() {
		return celdas.size();
	}
	
	
	/**
	 * Devuelve el número total de piezas actualmente en celdas del grupo.
	 * 
	 * @return cont Cont
	 */
	public int consultarNumeroPiezas() {
		int cont = 0;
		for (int i = 0; i < celdas.size(); i++) {
			if (!celdas.get(i).estaVacia()) {
				cont++;
			}
		}
		return cont;
	}
	
	
	/**
	 * Comprueba si la celda que se pasa por argumento está en el grupo.
	 * 
	 * @param celdaABuscar Celda a buscar
	 * @return true si está y false si no está
	 */
	public boolean contieneCelda(Celda celdaABuscar) {
		for (int i = 0; i < celdas.size(); i++) {
			if (celdas.get(i).equals(celdaABuscar)) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Comprueba si hay cuatro piezas con cuatro figuras diferentes, 
	 * con independencia del color de las mismas.
	 * 
	 * @return true si está completo con figuras diferentes o false si no
	 */
	public boolean estaCompletoConFigurasDiferentes() {
		int c = 0, es = 0, cb = 0, cl = 0;
		
		if(consultarNumeroPiezas() == 4) {
			for (int i = 0 ; i < celdas.size() ; i++) {
				Figura figura = celdas.get(i).consultarPieza().consultarFigura();
					switch(figura) {
					case CONO:
						c++;
						break;
					case ESFERA:
						es++;
						break;
					case CUBO:
						cb++;
						break;
					case CILINDRO:
						cl++;
						break;
					default:
					}
			} return (c == 1 && es == 1 && cb == 1 && cl ==1);
		}
		return false;
	}

	
	/**
	 * Comprueba si en las celdas del grupo hay alguna pieza 
	 * que tiene la misma figura de color contrario al pasado como argumento.
	 * 
	 * @param figura Figura
	 * @param color Color
	 * @return true si existe y false si no existe
	 */
	public boolean existeMismaPiezaDelColorContrario(Figura figura, Color color) {
		for (int i = 0; i < celdas.size(); i++) {
			if (!celdas.get(i).estaVacia()) {
				if (celdas.get(i).consultarPieza().consultarFigura() == figura && celdas.get(i).consultarPieza().consultarColor() != color) {
					return true;
				}
			}
		}
		return false;
	}


	@Override
	public int hashCode() {
		return Objects.hash(celdas);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grupo other = (Grupo) obj;
		return Objects.equals(celdas, other.celdas);
	}


	@Override
	public String toString() {
		return "Grupo [celdas=" + celdas + "]";
	}



	
	
}