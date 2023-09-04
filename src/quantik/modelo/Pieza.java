package quantik.modelo;

import java.util.Objects;

import quantik.util.Figura;
import quantik.util.Color;

/**
 * Pieza.
 * 
 * @author Jimena Arnaiz Gonzalez <a href="jag1033@alu.ubu.es"> Jimena </a>
 * @author Ivan Estepar Rebollo <a href="ier1003@alu.ubu.es"> Ivan </a>
 * @version 2.0
 *
 */

public class Pieza {
	/**
	 * Figura.
	 */
	private Figura figura;
	
	/**
	 * Color.
	 */
	private Color color;
	
	/**
	 * Constructor.
	 * 
	 * @param figura que tiene la pieza
	 * @param color de la pieza
	 */
	public Pieza(Figura figura, Color color) {
		this.figura = figura;
		this.color = color;
	}
	
	/**
	 * Obtiene el texto de la pieza.
	 * 
	 * @return figura con su color
	 */
	public String aTexto() {
		return figura.aTexto() + color.toChar();
	}
	
	/**
	 * Clona una pieza.
	 * 
	 * @return Clon de la pieza actual
	 */
	public Pieza clonar() {
		return new Pieza(figura, color);
	}
	
	/**
	 * Deuelve la figura.
	 * 
	 * @return figura Figura
	 */
	public Figura consultarFigura() { //es como un get
		return figura;
	}
	
	/**
	 * Devuelve el color de la pieza.
	 * 
	 * @return color Color.
	 */
	public Color consultarColor() {
		return color;
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, figura);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pieza other = (Pieza) obj;
		return color == other.color && figura == other.figura;
	}

	@Override
	public String toString() {
		return "Pieza [figura=" + figura + ", color=" + color + "]";
	}



	
}
