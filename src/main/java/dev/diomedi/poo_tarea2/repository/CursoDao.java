package dev.diomedi.poo_tarea2.repository;

import dev.diomedi.poo_tarea2.model.Curso;

import java.util.List;

public interface CursoDao<T extends Curso> {
    List<T> leer();
    void guardar(List<T> cursos);
}
