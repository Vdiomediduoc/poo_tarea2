package dev.diomedi.poo_tarea2.repository;

import dev.diomedi.poo_tarea2.model.Curso;
import dev.diomedi.poo_tarea2.model.CursoCertificado;
import dev.diomedi.poo_tarea2.model.CursoLibre;

import java.util.ArrayList;
import java.util.List;

public class CursoRepository {
    public static final String ARCHIVO_LIBRE = "Libre.json";
    public static final String ARCHIVO_CERTIFICADO = "Certificado.json";
    private final CursoDao<CursoCertificado> certificadoDao;
    private final CursoDao<CursoLibre> libreDao;
    private final List<CursoCertificado> certificados = new ArrayList<>();
    private final List<CursoLibre> libres = new ArrayList<>();

    public CursoRepository() {
        this(new GsonCursoDao<>(ARCHIVO_LIBRE, CursoLibre.class), new GsonCursoDao<>(ARCHIVO_CERTIFICADO, CursoCertificado.class));

    }

    public CursoRepository(GsonCursoDao<CursoLibre> libreDao, GsonCursoDao<CursoCertificado> certificadoDao) {
        this.libreDao = libreDao;
        this.certificadoDao = certificadoDao;
    }

    public void cargar() {
        List<CursoCertificado> leidasCertificados = this.certificadoDao.leer();
        List<CursoLibre> leidasLibres = this.libreDao.leer();
        this.certificados.clear();
        this.certificados.addAll(leidasCertificados);
        this.libres.clear();
        this.libres.addAll(leidasLibres);

    }
    public List<Curso> listar() {
        List<Curso> todos = new ArrayList<>(this.certificados);
        todos.addAll(this.libres);
        return todos;
    }

    public void registrar(Curso curso) {
        if (curso instanceof CursoCertificado certificado) {
            this.certificados.add(certificado);
            try {
                certificadoDao.guardar(this.certificados);
            } catch (PercistenciaExeption e) {
                this.certificados.remove(certificados.size() - 1);
                throw e;
            }
        } else if (curso instanceof CursoLibre cursoLibre) {
            this.libres.add(cursoLibre);
            try {
                libreDao.guardar(this.libres);
            } catch (PercistenciaExeption e) {
                this.libres.remove(libres.size() - 1);
                throw e;
            }
        } else {
            throw new UnsupportedOperationException("Curso no Soportado");
        }
    }
    private static int indiceDe(List<? extends Curso> lista, Curso curso){
        for( int i = 0; i < lista.size(); i++){
            if (lista.get(i) == curso){
                return i;
        }}
            return -1;}

    private < T extends Curso> boolean eliminarde(List<T> lista, CursoDao<T> dao, Curso curso){
        int posicion = indiceDe(lista,curso);
        if (posicion < 0){
            return false;
        }
        T eliminado = lista.remove(posicion);
        try{
            dao.guardar(lista);
        }catch (PercistenciaExeption e){
            lista.add(posicion,eliminado);
            throw e;
        }
        return true;
    };
    public boolean eliminar(Curso curso) {
        if (curso instanceof CursoCertificado certificado) {
        return (this.eliminarde(certificados, certificadoDao, curso));
        } else if (curso instanceof CursoLibre cursoLibre) {
            return (this.eliminarde(libres, libreDao, cursoLibre));
        }
        return false;
}
}