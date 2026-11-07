package dev.diomedi.poo_tarea2.model;

public class CursoLibre extends Curso {
    protected int cantidadIncritos;

    public CursoLibre() {
        super();
    }

    public CursoLibre(String codigoCurso, int duracion, int cupoMaximo, int cantidadIncritos) {
        super(codigoCurso, duracion, cupoMaximo);
        this.setCantidadIncritos(cantidadIncritos);
    }

    public int getCantidadIncritos() {
        return cantidadIncritos;
    }

    public void setCantidadIncritos(int cantidadIncritos) throws IllegalArgumentException {
        if (cantidadIncritos <= 0 || cantidadIncritos > cupoMaximo) {
            throw new IllegalArgumentException("La cantidad de inscritos no puede ser menor o igual a cero ni superar el cupo máximo.");
        }
        this.cantidadIncritos = cantidadIncritos;
    }

    @Override
    public double calcularCoste() {
        double costeBase = 45000.0;
        if (this.cantidadIncritos > 20) {
            return costeBase + (costeBase * 0.1);
        }
        return costeBase;
    }

    @Override
    public String detalleCurso() {
        return "Tipo: Curso Libre | " + super.detalleCurso() +
                " | Inscritos: " + this.cantidadIncritos +
                " | Coste: $" + calcularCoste();
    }

    @Override
    public String getTipoCurso() {
        return "Clase libre";
    }
}
