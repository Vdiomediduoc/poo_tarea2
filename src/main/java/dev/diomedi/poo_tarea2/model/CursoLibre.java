package dev.diomedi.poo_tarea2.model;

import java.io.IOException;

public class CursoLibre extends Curso {
    protected int cantidadIncritos;
    public CursoLibre(String codigoCurso, int duracion, int cupoMaximo, int cantidadIncritos) {
        super(codigoCurso, duracion, cupoMaximo);
        this.setCantidadIncritos(cantidadIncritos);
    }
    public int getCantidadIncritos() {
        return cantidadIncritos;
    }
    public void setCantidadIncritos(int cantidadIncritos) throws IllegalArgumentException {
        if (cantidadIncritos <= 0 || cantidadIncritos > cupoMaximo) {
            throw new IllegalArgumentException("La Cantidad de inscrito no puede ser negativo o cero y no puede superar el cupo maximo");
        }
        this.cantidadIncritos = cantidadIncritos;
    }
    @Override
    public double calcularCoste(){
        double costeBase = 45000.0;
        if(this.cantidadIncritos > 20){
            return (costeBase + (costeBase * 0.1));
        }
        return costeBase;
    };
    @Override
    public String detalleCurso(){
        String detalleCurso = "Tipo: Curso Libre";
        detalleCurso += " | " + super.detalleCurso();
        detalleCurso += " | Costo Curso: " + calcularCoste();
        return detalleCurso;

    }
    @Override
    public String getTipoCurso(){
        String tipoCurso = "Tipo: Curso Libre";
        return tipoCurso;
    }
}
