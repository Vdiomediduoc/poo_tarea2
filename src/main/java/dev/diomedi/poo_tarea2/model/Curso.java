package dev.diomedi.poo_tarea2.model;

public abstract class Curso {
    protected String codigoCurso;
    protected int duracion;
    protected int cupoMaximo;

    public Curso(String codigoCurso, int duracion, int cupoMaximo) {
        this.setCodigoCurso(codigoCurso);
        this.setDuracion(duracion);
        this.setCupoMaximo(cupoMaximo);
    }
    public String getCodigoCurso() {
        return codigoCurso;
    }
    public void setCodigoCurso (String codigoCurso) throws IllegalArgumentException  {
        if(codigoCurso == null || codigoCurso.isEmpty()){
            throw new IllegalArgumentException("El codigo de curso no puede estar vacio ni ser nulo");
        }else{ this.codigoCurso = codigoCurso;}
    }
    public int getDuracion() {
        return duracion;
    }
    public void setDuracion(int duracion) throws IllegalArgumentException {
        if (duracion < 4 || duracion > 200) {
            throw new IllegalArgumentException("La duracion del curso tiene que estar en un rango de 4 a 200 horas");
        }
        this.duracion = duracion;
    }
    public int getCupoMaximo() {
        return cupoMaximo;
    }
    public void setCupoMaximo(int cupoMaximo) throws IllegalArgumentException {
        if (cupoMaximo <= 0) {
            throw new IllegalArgumentException("Cupo maximo debe ser un numero positivo");
        }
        this.cupoMaximo = cupoMaximo;
    }
    public abstract double calcularCoste();
    public String detalleCurso() {
        String detalleCurso = "";
        detalleCurso += "Codigo: " + this.codigoCurso + "| Duracion: " + this.duracion + "| Cupo: " + this.cupoMaximo + "\n";

        return detalleCurso;
    }

    public String toString() {
        String informacion = "";
        informacion += "Codigo: " + this.codigoCurso + "| Duracion: " + this.duracion;
        return informacion;
    }
    public abstract String getTipoCurso();
}
