
package dev.diomedi.poo_tarea2.model;

public class CursoCertificado extends Curso implements ConDiploma {
    protected String entidadCertificadora;
    protected boolean evaluacionAlDia;
    protected boolean diplomaEmitido;

    public CursoCertificado() {
        super();
    }

    public CursoCertificado(String codigoCurso, int duracion, int cupoMaximo, String entidadCertificadora, boolean evaluacionAlDia, boolean diplomaEmitido) {
        super(codigoCurso, duracion, cupoMaximo);
        this.setEntidadCertificadora(entidadCertificadora);
        this.evaluacionAlDia = evaluacionAlDia;
        this.diplomaEmitido = diplomaEmitido;
    }

    public String getEntidadCertificadora() {
        return entidadCertificadora;
    }

    public void setEntidadCertificadora(String entidadCertificadora) throws IllegalArgumentException {
        if (entidadCertificadora == null || entidadCertificadora.trim().isEmpty()) {
            throw new IllegalArgumentException("La entidad certificadora no puede estar vacía ni ser nula.");
        }
        this.entidadCertificadora = entidadCertificadora.trim();
    }

    public boolean isEvaluacionAlDia() {
        return evaluacionAlDia;
    }

    public void setEvaluacionAlDia(boolean evaluacionAlDia) {
        this.evaluacionAlDia = evaluacionAlDia;
    }

    public boolean isDiplomaEmitido() {
        return diplomaEmitido;
    }

    public void setDiplomaEmitido(boolean diplomaEmitido) {
        this.diplomaEmitido = diplomaEmitido;
    }

    @Override
    public double calcularCoste() {
        double costeBase = 85000.0;
        if (!this.evaluacionAlDia) {
            return costeBase + (costeBase * 0.2);
        }
        return costeBase;
    }

    @Override
    public void emitirDiploma() {
        this.diplomaEmitido = true;
    }

    @Override
    public boolean estaEmitido() {
        return this.diplomaEmitido;
    }

    @Override
    public String detalleCurso() {
        return "Tipo: Curso Certificado | " + super.detalleCurso() +
                " | Entidad: " + this.entidadCertificadora +
                " | Evaluación al Día: " + (this.evaluacionAlDia ? "Sí" : "No") +
                " | Diploma Emitido: " + (this.diplomaEmitido ? "Sí" : "No") +
                " | Coste: $" + calcularCoste();
    }

    @Override
    public String getTipoCurso() {
        return "Clase Certificada";
    }
}
