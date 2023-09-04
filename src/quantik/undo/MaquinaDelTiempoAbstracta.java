package quantik.undo;

import java.util.Date;


/**
 * Maquina del tiempo abstracta.
 * 
 * @author Jimena Arnaiz Gonzalez <a href="jag1033@alu.ubu.es"> Jimena </a>
 * @author Ivan Estepar Rebollo <a href="ier1003@alu.ubu.es"> Ivan </a>
 * @version 2.0
 *
 */
public abstract class MaquinaDelTiempoAbstracta implements MecanismoDeDeshacer {
	/**
	 * Fecha actual.
	 */
    protected Date fechaActual;
    /**
     * Filas del tablero.
     */
    protected int filas;
    /**
     * Columnas del tablero.
     */
    protected int columnas;

    /**
     * Constructor.
     * 
     * @param fecha Fecha
     * @param filas Filas
     * @param columnas Columnas
     */
    public MaquinaDelTiempoAbstracta(Date fecha, int filas, int columnas) {
        this.fechaActual = fecha;
        this.filas = filas;
        this.columnas = columnas;
    }



    @Override
    public Date obtenerFechaInicio() {
        return fechaActual;
    }

}