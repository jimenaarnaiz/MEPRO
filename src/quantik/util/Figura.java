package quantik.util;

/**
 * Enumeración figura.
 * 
 * @author Jimena Arnaiz Gonzalez <a href="jag1033@alu.ubu.es"> Jimena </a>
 * @author Ivan Estepar Rebollo <a href="ier1003@alu.ubu.es"> Ivan </a>
 * @version 1.0
 *
 */

public enum Figura {
	/**
	 * Figura cilindro.
	 */
	CILINDRO("CL"),
	/**
	 * Figura cono.
	 */
	CONO("CN"),
	/**
	 * Figura cubo.
	 */
	CUBO("CB"),
	/**
	 * Figura esfera.
	 */
	ESFERA("ES");
	
	/**
	 * Texto asociado a la figura.
	 */
	private String texto;
	
	/**
	 * Constructor.
	 * 
	 * @param texto Texto de la figura
	 */
	private Figura(String texto) {
		this.texto = texto;
	}
	
	/**
	 * Obtiene el texto de la figura.
	 * 
	 * @return texto de 1 figura
	 */
	public String aTexto() {
		return texto;
	}
	

}
