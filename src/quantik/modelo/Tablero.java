package quantik.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import quantik.excepcion.CoordenadasIncorrectasException;

/**
 * Tablero.
 * 
 * @author Jimena Arnaiz Gonzalez <a href="jag1033@alu.ubu.es"> Jimena </a>
 * @author Ivan Estepar Rebollo <a href="ier1003@alu.ubu.es"> Ivan </a>
 * @version 2.0
 */

public class Tablero {
	/**
	 * Inicializa el tablero.
	 */
	private List<List<Celda>> matriz;
	/**
	 * FILAS = 4.
	 */
	private static final int FILAS = 4;
	
	/**
	 * COLUMNAS = 4.
	 */
	private static final int COLUMNAS = 4;
	

	/**
	 * Constructor. Asigna al tablero las filas y columnas.
	 */
	public Tablero() {
		
		matriz = new ArrayList<>();
		
		List<Celda> f1 = new ArrayList<>(4);
		List<Celda> f2 = new ArrayList<>(4);
		List<Celda> f3 = new ArrayList<>(4);
		List<Celda> f4 = new ArrayList<>(4);
		
		for (int i = 0; i < FILAS ; i++) {
        	for (int j = 0 ; j < COLUMNAS; j++) {
	    		if(i==0) {
	    			f1.add(new Celda(i, j));
	    		}
	    		if(i==1) {
	    		f2.add(new Celda(i, j));
	    		}
	    		if(i==2) {
	    			f3.add(new Celda(i, j));
	    		}
	    		if(i==3) {
	    			f4.add(new Celda(i, j));
	        	}
        	}
        }
        
        matriz.add(0, f1);
        matriz.add(1, f2);
        matriz.add(2, f3);
        matriz.add(3, f4);
	}
	

	/**
	 * Devuelve un clon en profundidad del tablero actual.
	 * 
	 * @return tablero clon
	 */
	public Tablero clonar() {
		Tablero matrizClon = new Tablero();
		
		for (int i = 0 ; i < matriz.size() ; i++) {
			for (int j = 0 ; j < matriz.size() ; j++) {
				
			matrizClon.matriz.get(i).set(j,matriz.get(i).get(j).clonar()) ;	  
			}
		}
		return matrizClon;
	}

	
	/**
	 * Coloca en la posición indicada la pieza pasada como argumento.
	 * Si las coordenadas no están en el tablero, no se hace nada.
	 * Si la celda estaba ya ocupada tampoco se realiza acción alguna.
	 * 
	 * @param fila Fila
	 * @param columna Columna
	 * @param pieza Pieza
	 * @throws CoordenadasIncorrectasException excepción
	 */
	public void colocar(int fila, int columna, Pieza pieza) throws CoordenadasIncorrectasException{
		if (estaEnTablero(fila,columna) == true) {
			if (obtenerCelda(fila,columna).estaVacia()) {
				matriz.get(fila).get(columna).colocar(pieza);
			}
		}else {
			throw new CoordenadasIncorrectasException();
		}
			
	}
	
	
	/**
	 * Función que evalúa si la pieza está dentro de los límites del tablero.
	 * 
	 * @param fila Fila
	 * @param columna Columna
	 * @return true si es dentro de los límites y false si no
	 */
	public boolean estaEnTablero(int fila, int columna) {
		if (fila >= 0 && fila < FILAS && columna >= 0 && columna < COLUMNAS) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * Devuelve un clon en profundidad de la celda con las coordenadas indicadas.
	 * 
	 * @param fila Fila
	 * @param columna Columna
	 * @return Si las coordenadas están en el tablero devuelve el clon, si no, null.
	 * @throws CoordenadasIncorrectasException excepción
	 */
	public Celda consultarCelda(int fila, int columna) throws CoordenadasIncorrectasException {
		if (estaEnTablero(fila,columna) == false) {
			throw new CoordenadasIncorrectasException();
		}
		return matriz.get(fila).get(columna).clonar();
	}
	
	
	/**
	 * Obtiene el numero de columnas.
	 * 
	 * @return numero columnas
	 */
	public int consultarNumeroColumnas() {
		return matriz.size();
	}
	
	
	/**
	 * Obtiene el numero de filas.
	 * 
	 * @return numero filas
	 */
	public int consultarNumeroFilas() {
		return matriz.size();
	}
	
	
	/**
	 * Devuelve la referencia a la celda con las coordenadas indicadas.
	 * 
	 * @param fila Fila
	 * @param columna Columna
	 * @return Si las coordenadas están en el tablero devuelve la referencia, si no, null.
	 * @throws CoordenadasIncorrectasException excepción
	 */
	Celda obtenerCelda(int fila, int columna) throws CoordenadasIncorrectasException {
		if (estaEnTablero(fila,columna) == false) {
			throw new CoordenadasIncorrectasException();
		}
		return matriz.get(fila).get(columna);
	}
	
	
	/**
	 * Devuelve la cadena en la matriz.
	 * 
	 * @param celda Celda
	 * @return cadena Cadena
	 */
	private String textoCelda(Celda celda){
		String cadena = "";
		
		if (celda.estaVacia()) {
			cadena = "---";
		} else {
			cadena = celda.consultarPieza().aTexto();
		}
		return " -" + cadena + "-";
	}
	
	
	/**
	 * Devuelve el estado del tablero con las piezas actualmente colocadas.
	 *
	 * @return cadena Cadena
	 */
	public String aTexto() {
		String cadena = " ";
		
		for (int j = 0 ; j < consultarNumeroColumnas() ; j++) {
			cadena += "   " + j + "  ";
		}
		cadena += "\n";
		for (int i = 0 ; i < consultarNumeroFilas() ; i++) {
			cadena += i;
			for (int j = 0 ; j < consultarNumeroColumnas() ; j++) {
				try {
					cadena += textoCelda(consultarCelda(i,j));
				} catch (CoordenadasIncorrectasException e) {
					throw new RuntimeException("Coordenadas incorrectas" , e);
				}
				
			}
			if (i < matriz.size() - 1) {
				cadena += "\n";
			}
		}
		return cadena;
	}



	@Override
	public int hashCode() {
		return Objects.hash(COLUMNAS, FILAS, matriz);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tablero other = (Tablero) obj;
		return COLUMNAS == other.COLUMNAS && FILAS == other.FILAS && Objects.equals(matriz, other.matriz);
	}


	@Override
	public String toString() {
		return "Tablero [matriz=" + matriz + ", FILAS=" + FILAS + ", COLUMNAS=" + COLUMNAS + "]";
	}
	


	

	

	
	
	
	
}