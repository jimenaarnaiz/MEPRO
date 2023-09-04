package quantik.textui;

import java.util.Date;
import java.util.Scanner;

import quantik.control.Partida;
import quantik.excepcion.CoordenadasIncorrectasException;
import quantik.modelo.Caja;
import quantik.modelo.Pieza;
import quantik.modelo.Tablero;
import quantik.undo.MaquinaDelTiempoConJugadas;
import quantik.undo.MaquinaDelTiempoConPartidas;
import quantik.undo.MecanismoDeDeshacer;
import quantik.util.Color;
import quantik.util.Figura;

/**
 * Quantik en modo texto.
 * 
 * Se abusa del uso de static tanto en atributos como en métodos para comprobar
 * su similitud a variables globales y funciones globales de otros lenguajes.
 *
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @author Jimena Arnaiz Gonzalez <a href="jag1033@alu.ubu.es"> Jimena </a>
*  @author Ivan Estepar Rebollo <a href="ier1003@alu.ubu.es"> Ivan </a>
 * @since 1.0
 * @version 2.1
 */
public class Quantik {

	/** Tamaño en caracteres de una jugada. */
	private static final int TAMAÑO_JUGADA = 4;

	/**
	 * Arbitro.
	 */
	private static Partida partida;

	/**
	 * Caja blancas.
	 */
	private static Caja cajaBlancas;

	/**
	 * Caja negras.
	 */
	private static Caja cajaNegras;

	/**
	 * Lector por teclado.
	 */
	private static Scanner scanner;

	/**
	 * Mecanismo para deshacer partidas.
	 */
	private static MecanismoDeDeshacer deshacer;

	/**
	 * Tipo de mecanismo de deshacer a instanciar.
	 */
	private static String configuracion;

	/**
	 * Método raíz.
	 * 
	 * @param args argumentos de entrada
	 */
	public static void main(String[] args) {
		String jugada = null;
		boolean salgo = false;
		configuracion = "";
		
		try {
			mostrarMensajeBienvenida();
			try {
				configuracion = extraerModoDeshacer(args);
				inicializarPartida(configuracion);
			} catch (RuntimeException ex){
				mostrarErrorSeleccionandoModo();
			}
			mostrarTableroEnFormatoTexto();
			while (salgo != true) {
				jugada = recogerJugada();
		        if (jugada.equals("salir")) {
		        	salgo = true;
		        	mostrarInterrupcionPartida();
		        }
		        else if (jugada.equals("deshacer")) {
		        	if (configuracion.equals("jugadas")) {
		        		deshacer.deshacerJugada();
		        		partida = deshacer.consultarPartidaActual();
		        		mostrarTableroEnFormatoTexto();
		        	}
		        	else if (configuracion.equals("partidas")) {
		        		deshacer.deshacerJugada();
		        		partida = deshacer.consultarPartidaActual();
		        		mostrarTableroEnFormatoTexto();
		        	}
		        } 
		        else {
		        	 if (validarFormato(jugada)) {
		                if (validarLegalidad(jugada)) {
		                    realizarJugada(jugada);
		                    mostrarTableroEnFormatoTexto();
		                    avanzarTurno();
		                } else {
		                    mostrarErrorEnLegalidadJugada();
		                }
		            } else {
		                mostrarErrorEnFormatoDeEntrada();
		            }
		            if (comprobarSiFinalizaPartida() == true) {
		                salgo = true;
		            }
		        } 
			}
			avanzarTurno();
			finalizarPartida();
		}
		catch (RuntimeException ex){
			mostrarErrorInterno(ex);
		}
	}

	/**
	 * Muestra mensaje de error grave por error en el código del que no podemos
	 * recuperarnos.
	 * 
	 * @param ex excepción generada
	 */
	private static void mostrarErrorInterno(RuntimeException ex) {
		System.err.println("Error interno en código a corregir por el equipo informático.");
		System.err.println("Mensaje asociado de error: " + ex.getMessage());
		System.err.println("Traza detallada del error:");
		ex.printStackTrace();
		// sería mejor solución mandar dicha informacion de la traza a un fichero de log
		// en lugar de a la consola, pero esta solución se verá en otras asignaturas
	}

	/**
	 * Muestra mensaje de error grave si el modo de deshacer no es ninguno de los
	 * dos disponibles.
	 */
	private static void mostrarErrorSeleccionandoModo() {
		System.err.println("El modo seleccionado no se corresponde con ninguna de las dos opciones válidas.");
		System.err.println("Debe introducir \"jugadas\" o \"partidas\".");
	}

	/**
	 * Extrae de los argumentos de ejecución el tipo de mecanismo de deshacer con el
	 * que jugamos. No comprueba la corrección del texto introducido.
	 * 
	 * @param args argumentos
	 * @return texto con el tipo de mecanismo de deshacer, por defecto es jugadas
	 */
	private static String extraerModoDeshacer(String[] args) {
		if (args.length >= 1) {
			String modo = args[0].toLowerCase();
			if (!modo.equals("jugadas") && !modo.equals("partidas")) {
				throw new IllegalArgumentException("Error en argumento de entrada, modo " + modo + " no disponible");
			}
			return modo;
		}
		return "jugadas"; // por defecto si no hay argumento

	}

	/**
	 * Avanza el turno al siguiente jugador.
	 */
	private static void avanzarTurno() {
		partida.cambiarTurno();
	}

	/**
	 * Muestra en pantalla el mensaje de interrupción de partida.
	 */
	private static void mostrarInterrupcionPartida() {
		System.out.println("\nInterrumpida la partida, se concluye el juego.");
	}

	/**
	 * Muestra en pantalla el estado actual del tablero en formato texto.
	 */
	private static void mostrarTableroEnFormatoTexto() {
		System.out.println();
		System.out.println(partida.consultarTablero().aTexto());
	}

	/**
	 * Realiza la jugada introducida por teclado. Se supone que la jugada en cuanto
	 * al formato ya ha sido validada previamente y también que se ha comprobado la
	 * legalidad de la misma.
	 * 
	 * @param jugada jugada
	 */
	private static void realizarJugada(String jugada) {
		try {
			int fila = leerFila(jugada);
			int columna = leerColumna(jugada);
			Figura figura = leerFigura(jugada);
			deshacer.hacerJugada(fila, columna, figura, partida.consultarTurno());
			partida.colocarPiezaEnTurnoActual(fila, columna, figura);
		} catch (CoordenadasIncorrectasException ex) {
			// si la jugada estaba validada, no deberíamos llegar aquí
			// salvo que haya errores de programación en dichas comprobaciones
			// que habría que corregir
			throw new RuntimeException("Errores graves en comprobación previa de formato de jugada.", ex);
		}
	}

	/**
	 * Muestra en pantalla el mensaje de error si la jugada es ilegal.
	 */
	private static void mostrarErrorEnLegalidadJugada() {
		System.out.println(
				"Movimiento ILEGAL, compruebe piezas del jugador contrario y disponibilidad de sus propias piezas.");
		mostrarPiezasDisponibles(partida.consultarTurno());
		mostrarTableroEnFormatoTexto();
	}

	/**
	 * Muestra las piezas disponibles.
	 * 
	 * @param color color de la caja a mostrar
	 */
	private static void mostrarPiezasDisponibles(Color color) {
		Caja caja = color == Color.BLANCO ? cajaBlancas : cajaNegras;
		System.out.print("Piezas disponibles para caja de color " + color + ": ");
		StringBuilder sb = new StringBuilder();
		for (Pieza pieza : caja.consultarPiezasDisponibles()) {
			sb.append(pieza.consultarFigura());
			sb.append("-");
		}
		String text = sb.substring(0, sb.lastIndexOf("-"));
		System.out.println(text);
	}

	/**
	 * Comprueba la legalidade de la jugada asumiendo que el formato es correcto.
	 * 
	 * @param jugada jugada en formato texto correcta
	 * @return true si puede realizarse la jugada en base a las reglas, false en
	 *         caso contrario
	 */
	private static boolean validarLegalidad(String jugada) {
		int fila = leerFila(jugada);
		int columna = leerColumna(jugada);
		Figura figura = leerFigura(jugada);
		return partida.esJugadaLegalEnTurnoActual(fila, columna, figura);
	}

	/**
	 * Muestra el mensaje de bienvenida con instrucciones para finalizar la partida.
	 */
	private static void mostrarMensajeBienvenida() {
		System.out.println("Bienvenido al juego del Quantik 2.0 - Máquina del tiempo con " + configuracion);
		System.out.println("Para interrumpir partida introduzca \"salir\".");
		System.out.println("Para deshacer la última jugada introduzca \"deshacer\".");
		System.out.println("Disfrute de la partida...");
	}

	/**
	 * Mostrar al usuario información de error en el formato de entrada, mostrando
	 * ejemplos.
	 */
	private static void mostrarErrorEnFormatoDeEntrada() {
		System.out.println();
		System.out.println("Error en el formato de entrada.");
		System.out.println(
				"El formato debe ser numeronumeroletraletra, por ejemplo 12ES para colocar una ESfera en la fila 1, columna2");
		System.out.println(
				"Los números deben estar en el rango [0,3] y las dos letras coinciden con los tipos de figuras: CL (cilindro), CN (cono), CB (cubo) y ES (esfera).\n");
	}

	/**
	 * Comprueba si la partida está finalizada.
	 * 
	 * @return true se ha finalizado la partida, false en caso contrario
	 */
	private static boolean comprobarSiFinalizaPartida() {
		return partida.estaAcabadaPartida();
	}

	/**
	 * Finaliza la partida informando al usuario y cerrando recursos abiertos.
	 * 
	 * @version 1.1
	 */
	private static void finalizarPartida() {
		if (partida.hayAlgunGrupoCompleto() && !partida.estaBloqueadoTurnoActual()) {
			avanzarTurno();
		}
		System.out.printf("Ganada la partida por el jugador con turno %s.%n", partida.consultarGanador());
		scanner.close();
	}

	/**
	 * Inicializa el estado de los elementos de la partida.
	 * 
	 * @param configuracion modo de deshacer
	 */
	private static void inicializarPartida(String configuracion) {
		// Inicializaciones de objetos
		cajaBlancas = new Caja(Color.BLANCO);
		cajaNegras = new Caja(Color.NEGRO);
		Tablero tablero = new Tablero();
		partida = new Partida(tablero, cajaBlancas, cajaNegras);
		// inicializamos mecanismo de deshacer
		seleccionarMecanismoDeshacer(configuracion, tablero);
		// Abrimos la lectura desde teclado
		scanner = new Scanner(System.in);
	}

	/**
	 * Selecciona el mecanismo de deshacer.
	 * 
	 * Por polimorfimo se conecta una instancia concreta del descendiente a la
	 * interfaz Deshacer.
	 *
	 * @param configuracion texto con la selección actual de mecanismo de deshacer
	 * @param tablero       tablero
	 * @see quantik.undo.MecanismoDeDeshacer
	 */
	private static void seleccionarMecanismoDeshacer(String configuracion, Tablero tablero) {
		final int filas = tablero.consultarNumeroFilas();
		final int columnas = tablero.consultarNumeroColumnas();
		switch (configuracion) {
		case "jugadas":
			deshacer = new MaquinaDelTiempoConJugadas(new Date(), filas, columnas);
			break;
		case "partidas":
			deshacer = new MaquinaDelTiempoConPartidas(new Date(), filas, columnas);
			break;
		default:
			deshacer = new MaquinaDelTiempoConJugadas(new Date(), filas, columnas);
		}
	}

	/**
	 * Recoge jugada del teclado.
	 * 
	 * @return jugada jugada en formato texto
	 */
	private static String recogerJugada() {
		System.out.printf("Introduce jugada el jugador con turno %s (máscara nnll donde n es número y l es letra): ",
				partida.consultarTurno());
		return scanner.next();
	}

	/**
	 * Valida la corrección del formato de la jugada. Solo comprueba la corrección
	 * del formato de entrada en cuanto al tablero. La jugada tiene que tener cuatro
	 * caracteres y contener letras y números de acuerdo a las reglas de la notación
	 * algebraica.
	 * 
	 * Otra mejor solución alternativa es el uso de expresiones regulares (se verán
	 * en la asignatura de 3º Procesadores del Lenguaje).
	 * 
	 * @param jugada a validar
	 * @return true si el formato de la jugada es correcta según las coordenadas
	 *         disponibles del tablero
	 */
	private static boolean validarFormato(String jugada) {
		boolean estado = true;
		if (jugada.length() != TAMAÑO_JUGADA || esFiguraIncorrecta(jugada.substring(2))
				|| esNumeroInvalido(jugada.charAt(0)) || esNumeroInvalido(jugada.charAt(1))) {
			estado = false;
		}
		return estado;
	}

	/**
	 * Comprueba si las letras no se corresponden con una figura.
	 * 
	 * @param texto texto a comprobar
	 * @return true si la letra no está en el rango, false en caso contrario
	 */
	private static boolean esFiguraIncorrecta(String texto) {
		// evalúa si no es un cilindro, ni cubo, ni cono ni esfera retornando dicha
		// evaluación...
		return (!texto.equals(Figura.CILINDRO.aTexto()) && !texto.equals(Figura.CUBO.aTexto())
				&& !texto.equals(Figura.CONO.aTexto()) && !texto.equals(Figura.ESFERA.aTexto()));
	}

	/**
	 * Comprueba si el número está fuera del rango [0,3].
	 * 
	 * @param numero numero
	 * @return true si el número no está en el rango, false en caso contrario
	 */
	private static boolean esNumeroInvalido(char numero) {
		return numero < '0' || numero > '3';
	}

	/**
	 * Obtiene la fila de la jugada.
	 * 
	 * @param jugada jugada en formato nnll
	 * @return fila de la celda donde colocar la pieza
	 */
	private static int leerFila(String jugada) {
		return Integer.parseInt(jugada.substring(0, 1));
	}

	/**
	 * Obtiene la columna de la jugada.
	 * 
	 * @param jugada jugada en formato nnll
	 * @return columna de la celda donde colocar la pieza
	 */
	private static int leerColumna(String jugada) {
		return Integer.parseInt(jugada.substring(1, 2));
	}

	/**
	 * Lee la figura correspondiente de la jugada. Se asume que se ha comprobado
	 * previamente la longitud de la cadena.
	 * 
	 * @param jugada jugada en formato nnll donde ll es el texto de la figura a
	 *               extraer
	 * @return figura con la que obtener la pieza de la caja a colocar en el tablero
	 */
	private static Figura leerFigura(String jugada) {
		String figura = jugada.substring(2);

		if (figura.equals(Figura.CILINDRO.aTexto()))
			return Figura.CILINDRO;
		else if (figura.equals(Figura.CONO.aTexto()))
			return Figura.CONO;
		else if (figura.equals(Figura.CUBO.aTexto()))
			return Figura.CUBO;
		else if (figura.equals(Figura.ESFERA.aTexto()))
			return Figura.ESFERA;
		return null;
	}

}
