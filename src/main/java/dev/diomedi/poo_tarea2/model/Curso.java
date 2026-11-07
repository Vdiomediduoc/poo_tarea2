package dev.diomedi.poo_tarea2.model;

public abstract class Curso {
    protected String codigoCurso;
    protected int duracion;
    protected int cupoMaximo;

    protected Curso() {
    }

    public Curso(String codigoCurso, int duracion, int cupoMaximo) {
        this.setCodigoCurso(codigoCurso);
        this.setDuracion(duracion);
        this.setCupoMaximo(cupoMaximo);
    }

    public String getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(String codigoCurso) throws IllegalArgumentException {
        if (codigoCurso == null || codigoCurso.trim().isEmpty()) {
            throw new IllegalArgumentException("El código del curso no puede estar vacío ni ser nulo.");
        }
        this.codigoCurso = codigoCurso.trim();
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) throws IllegalArgumentException {
        if (duracion < 4 || duracion > 200) {
            throw new IllegalArgumentException("La duración del curso debe estar entre 4 y 200 horas.");
        }
        this.duracion = duracion;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public void setCupoMaximo(int cupoMaximo) throws IllegalArgumentException {
        if (cupoMaximo <= 0) {
            throw new IllegalArgumentException("El cupo máximo debe ser un número positivo.");
        }
        this.cupoMaximo = cupoMaximo;
    }

    public abstract double calcularCoste();

    public abstract String getTipoCurso();

    public String detalleCurso() {
        return "Código: " + this.codigoCurso + " | Duración: " + this.duracion + " hrs | Cupo: " + this.cupoMaximo + "\n";
    }

    @Override
    public String toString() {
        return "Código: " + this.codigoCurso + " | Duración: " + this.duracion;
    }
}