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

    public CursoRepository(CursoDao<CursoLibre> libreDao, CursoDao<CursoCertificado> certificadoDao) {
        this.libreDao = libreDao;
        this.certificadoDao = certificadoDao;
    }

    public void cargar() throws PercistenciaExeption {
        List<CursoCertificado> leidasCertificados = this.certificadoDao.leer();
        List<CursoLibre> leidasLibres = this.libreDao.leer();

        this.certificados.clear();
        if (leidasCertificados != null) {
            this.certificados.addAll(leidasCertificados);
        }

        this.libres.clear();
        if (leidasLibres != null) {
            this.libres.addAll(leidasLibres);
        }
    }

    public List<Curso> listar() {
        List<Curso> todos = new ArrayList<>();
        todos.addAll(this.certificados);
        todos.addAll(this.libres);
        return todos;
    }

    public void registrar(Curso curso) throws PercistenciaExeption {
        if (curso instanceof CursoCertificado certificado) {
            this.certificados.add(certificado);
            try {
                this.certificadoDao.guardar(this.certificados);
            } catch (PercistenciaExeption e) {
                this.certificados.remove(certificado);
                throw e;
            }
        } else if (curso instanceof CursoLibre cursoLibre) {
            this.libres.add(cursoLibre);
            try {
                this.libreDao.guardar(this.libres);
            } catch (PercistenciaExeption e) {
                this.libres.remove(cursoLibre);
                throw e;
            }
        } else {
            throw new UnsupportedOperationException("Tipo de curso no soportado");
        }
    }

    private static int indiceDe(List<? extends Curso> lista, Curso curso) {
        if (curso == null || curso.getCodigoCurso() == null) return -1;

        for (int i = 0; i < lista.size(); i++) {
            Curso item = lista.get(i);
            if (item != null && curso.getCodigoCurso().equalsIgnoreCase(item.getCodigoCurso())) {
                return i;
            }
        }
        return -1;
    }

    private <T extends Curso> boolean eliminarde(List<T> lista, CursoDao<T> dao, Curso curso) throws PercistenciaExeption {
        int posicion = indiceDe(lista, curso);
        if (posicion < 0) {
            return false;
        }
        T eliminado = lista.remove(posicion);
        try {
            dao.guardar(lista);
        } catch (PercistenciaExeption e) {
            lista.add(posicion, eliminado);
            throw e;
        }
        return true;
    }

    public boolean eliminar(Curso curso) throws PercistenciaExeption {
        if (curso instanceof CursoCertificado) {
            return this.eliminarde(certificados, certificadoDao, curso);
        } else if (curso instanceof CursoLibre) {
            return this.eliminarde(libres, libreDao, curso);
        }
        return false;
    }
}