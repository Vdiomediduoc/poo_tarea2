package dev.diomedi.poo_tarea2.repository;

public class PercistenciaExeption extends RuntimeException {
    public static final String ALERTA_LECTURA = " No se pudo leer el archivo";
    public static final String ALERTA_ESCRITURA = "No se pudo Sobreescribir el archivo";
    public PercistenciaExeption(String message, Throwable cause) {
        super(message, cause);
    }
}
