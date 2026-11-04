package dev.diomedi.poo_tarea2.model;

import java.util.ArrayList;
import java.util.List;

public class GestorCapacitacion {
    ArrayList<Curso> cursos = new ArrayList<>();

    public ArrayList<Curso> getCursos() {
        return cursos;
    }
    public void setCursos(ArrayList<Curso> cursos) {
        this.cursos = cursos;
    }
    public void registrarCurso(Curso curso) {
        cursos.add(curso);
        if(curso instanceof CursoLibre){
            System.out.println(curso.getCodigoCurso() + " (Curso libre) registrado Correctamente");
        } else if (curso instanceof CursoCertificado) {
            System.out.println(curso.getCodigoCurso() + " (Curso certificado) registrado Correctamente");
        }
    }
    public List<Curso> buscarPorCodigo(String codigo) {
        ArrayList<Curso> busqueda = new ArrayList<>();
        for (Curso curso : cursos) {
            if (curso.getCodigoCurso().equals(codigo)) {
                busqueda.add(curso);
            }

        }
        return busqueda;
    }
}

