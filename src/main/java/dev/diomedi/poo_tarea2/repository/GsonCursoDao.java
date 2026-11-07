package dev.diomedi.poo_tarea2.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import dev.diomedi.poo_tarea2.model.Curso;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GsonCursoDao<T extends Curso> implements CursoDao<T> {
    private final Path archivo;
    private final Type tipoLista;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public GsonCursoDao(String archivo, Class<T> clase) {
        this.archivo = Paths.get(archivo);
        this.tipoLista = TypeToken.getParameterized(List.class, clase).getType();
    }
    @Override
    public List<T> leer() {
        if(Files.notExists(this.archivo)) {
            return new ArrayList<>();
        }
        try (Reader lector = Files.newBufferedReader(this.archivo, StandardCharsets.UTF_8)) {
            List<T> cursos = gson.fromJson(lector, this.tipoLista);
            if(cursos == null) {
            return new ArrayList<>();}
            List<T> validas = new ArrayList<>();
            for (T curso : cursos) {
                if ( curso != null && curso.getCodigoCurso()!= null){
                    validas.add(curso);
                }
            }
            return validas;
        } catch (IOException e){
        throw  new PercistenciaExeption(PercistenciaExeption.ALERTA_LECTURA, e);
    }

    }
    @Override
    public void guardar(List<T> Cursos) {
        try(Writer escritor = Files.newBufferedWriter(this.archivo, StandardCharsets.UTF_8)){
            this.gson.toJson(Cursos, this.tipoLista, escritor);
        }catch (IOException | JsonIOException e){
            throw new PercistenciaExeption(PercistenciaExeption.ALERTA_ESCRITURA, e);
        }

    }
}
