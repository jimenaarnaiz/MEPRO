package quantik.excepcion;


/**
 * CoordenadasIncorrectasException.
 * 
 * @author Jimena Arnaiz Gonzalez <a href="jag1033@alu.ubu.es"> Jimena </a>
 * @author Ivan Estepar Rebollo <a href="ier1003@alu.ubu.es"> Ivan </a>
 * @version 2.0
 *
 */

public class CoordenadasIncorrectasException extends Exception {

	/**
	 * Valor constante serialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor sin argumentos.
	 * 
	 */
	public CoordenadasIncorrectasException() {
		
	}

	/**
	 * Constructor con texto descriptivo.
	 * 
	 * @param message texto descriptivo
	 */
	public CoordenadasIncorrectasException(String message) {
		super(message);
	}
	
	
	/**
	 * Constructor con excepción encadenada.
	 * 
	 * @param cause excepión encadenada
	 */
	public CoordenadasIncorrectasException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor con texto descriptivo y excepción encadenada.
	 * 
	 * @param message texto descriptivo
	 * @param cause excepión encadenada
	 */
	public CoordenadasIncorrectasException(String message, Throwable cause) {
		super(message, cause);
	}
}
