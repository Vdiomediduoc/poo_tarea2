package dev.diomedi.poo_tarea2.controller;

import dev.diomedi.poo_tarea2.model.Curso;
import dev.diomedi.poo_tarea2.model.CursoCertificado;
import dev.diomedi.poo_tarea2.model.CursoLibre;
import dev.diomedi.poo_tarea2.repository.CursoRepository;
import dev.diomedi.poo_tarea2.repository.PercistenciaExeption;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    public static final String CLASE_LIBRE = "Clase libre";
    public static final String CLASE_CERTIFICADA = "Clase Certificada";
    public static final String ALERTA_DEFAULT = "";
    public static final String ALERTA_EXITO = "Clase Guardada Con Exito";
    public static final String ALERTA_ELIMINAR = "Clase Eliminada Con Exito";
    public static final String ALERTA_NO_VALIDO = "Dato Ingresado No Valido";
    public static final String ALERTA_NO_SELECCIONADO = "Por favor seleccione un dato";
    public static final String ALERTA_LECTURA = "Error al leer el archivo";
    public static final String ALERTA_ESCRITURA = "Error al sobreescribir el archivo";

    private final CursoRepository repository = new CursoRepository();
    @FXML
    private ChoiceBox<String>  tipoBox;
    @FXML
    private Label alertaLabel;
    @FXML
    private TextField codigoText;
    @FXML
    private TextField duracionText;
    @FXML
    private TextField cuposText;
    @FXML
    private TextField cantidadInscritosText;
    @FXML
    private TextField entidadCertificadoraText;
    @FXML
    private CheckBox alDiaBox;
    @FXML
    private CheckBox emitidoBox;
    @FXML
    private TableView<Curso> cursosView;
    @FXML
    private TableColumn<Curso, String> tipoColumn;
    @FXML private TableColumn<Curso, String> codigoColumn;
    @FXML private TableColumn<Curso, Integer> duracionColumn;
    @FXML private TableColumn<Curso, Integer> cuposColumn;
    @FXML private TableColumn<Curso, Double> precioColumn;
    @FXML private final ObservableList<Curso> cursosObs = FXCollections.observableArrayList();

    @FXML public void onSeleccionarTipo() {
        String choice = tipoBox.getValue();
        switch (choice) {
            case CLASE_LIBRE:
                this.cantidadInscritosText.setDisable(false);
                this.entidadCertificadoraText.setDisable(true);
                this.alDiaBox.setDisable(true);
                this.emitidoBox.setDisable(true);
                break;
            case CLASE_CERTIFICADA:
                this.cantidadInscritosText.setDisable(true);
                this.entidadCertificadoraText.setDisable(false);
                this.alDiaBox.setDisable(false);
                this.emitidoBox.setDisable(false);
                break;
            default:
                this.cantidadInscritosText.setDisable(true);
                this.entidadCertificadoraText.setDisable(true);
                this.alDiaBox.setDisable(true);
                this.emitidoBox.setDisable(true);
                break;
        }
    }
    private void setAlerta(String tipo, String mensaje) {
        if(mensaje.trim().isBlank()) {
            mensaje = tipo;
        } else {
            mensaje = tipo + " : " + mensaje;
        }
        switch(tipo) {
            case ALERTA_EXITO, ALERTA_ELIMINAR:
                this.alertaLabel.setVisible(true);
                this.alertaLabel.setTextFill(Color.GREEN);
                break;
            case ALERTA_LECTURA, ALERTA_NO_SELECCIONADO,  ALERTA_NO_VALIDO, ALERTA_ESCRITURA:
                this.alertaLabel.setVisible(true);
                this.alertaLabel.setTextFill(Color.RED);
                break;
            default:
                this.alertaLabel.setVisible(false);
                this.alertaLabel.setTextFill(Color.BLACK);
                break;
        }
        this.alertaLabel.setText(mensaje);
    }
    private void configurarView(){
        this.tipoColumn.setCellValueFactory(celda -> new ReadOnlyStringWrapper(celda.getValue().getTipoCurso()));
        this.codigoColumn.setCellValueFactory(celda -> new ReadOnlyStringWrapper(celda.getValue().getCodigoCurso()));
        this.duracionColumn.setCellValueFactory(celda -> new ReadOnlyObjectWrapper<>(celda.getValue().getDuracion()));
        this.cuposColumn.setCellValueFactory(celda ->new ReadOnlyObjectWrapper<>(celda.getValue().getCupoMaximo()));
        this.precioColumn.setCellValueFactory(celda -> new ReadOnlyObjectWrapper<>(celda.getValue().calcularCoste()));
        this.cursosView.setItems(cursosObs);
    }
    private  void actualizarView(){
        cursosObs.setAll(repository.listar());
    }
    @FXML
    public void initialize() {
        this.tipoBox.getItems().addAll(CLASE_LIBRE, CLASE_CERTIFICADA);
        this.cantidadInscritosText.setDisable(true);
        this.entidadCertificadoraText.setDisable(true);
        this.alDiaBox.setDisable(true);
        this.emitidoBox.setDisable(true);
        this.configurarView();
        try {
            this.repository.cargar();
            this.actualizarView();
        }catch (PercistenciaExeption e){
            setAlerta(e.getMessage(), "Error al cargar datos");
        }
    }
    @FXML
    public void onGuardar(){
        Curso curso;
        try {
            String tipo = tipoBox.getValue();
            if (tipo == null) {
                this.setAlerta(ALERTA_NO_SELECCIONADO, "Seleccione un tipo de curso");
                return;
            }
            String codigo = codigoText.getText();
            int duracion = Integer.parseInt(duracionText.getText());
            int cupos = Integer.parseInt(this.cuposText.getText());
            switch(tipo){
                case CLASE_LIBRE:
                    int cantidadInscritos = Integer.parseInt(this.cantidadInscritosText.getText());
                    if(cantidadInscritos <= 0){
                        this.setAlerta(ALERTA_NO_VALIDO, "Cantidad de inscritos no puede ser menor o igual a 0");
                        return;
                    }
                    curso = new CursoLibre(codigo, duracion, cupos,cantidadInscritos);
                    break;
                case CLASE_CERTIFICADA:
                    String entidad = entidadCertificadoraText.getText();
                    boolean isAlDia = alDiaBox.isSelected();
                    boolean emitido = emitidoBox.isSelected();
                    if(entidad.trim().isBlank() || entidad.isEmpty()){
                        this.setAlerta(ALERTA_NO_VALIDO, "Campos entidad certificadora vacio");
                        return;
                    }
                    curso = new CursoCertificado(codigo, duracion, cupos,entidad, isAlDia, emitido);
                    break;
                default:
                    this.setAlerta(ALERTA_NO_SELECCIONADO, "Seleccione un tipo de curso");
                    return;

            }
            try {
                this.repository.registrar(curso);
                this.actualizarView();
            }catch (PercistenciaExeption e){
                this.setAlerta(e.getMessage(), "Error al sobreescribir datos");
                return;
            }
            this.setAlerta(ALERTA_EXITO, "Curso" + curso.getCodigoCurso());
        }catch (IllegalArgumentException e){
            setAlerta(ALERTA_NO_VALIDO,e.getMessage());
        }


    }
    @FXML
    public void onLimpiar(){
        this.tipoBox.setValue("");
        this.codigoText.setText("");
        this.duracionText.setText("");
        this.cuposText.setText("");
        this.cantidadInscritosText.setText("");
        this.entidadCertificadoraText.setText("");
        this.alDiaBox.setSelected(false);
        this.emitidoBox.setSelected(false);
        setAlerta(ALERTA_DEFAULT, "");
    }

    @FXML
    public void onEliminar(){
        Curso seleccion = this.cursosView.getSelectionModel().getSelectedItem();
        if(seleccion == null){
            this.setAlerta(ALERTA_NO_SELECCIONADO, "Seleccione un curso para eliminar");
            return;
        } try {
            if(!this.repository.eliminar(seleccion)){
                this.setAlerta(ALERTA_NO_VALIDO, "Dato no se encuentra en el registro");
                this.actualizarView();
                return;
            }
        }catch (PercistenciaExeption e){
            setAlerta(ALERTA_ESCRITURA, "Error al eliminar curso");
        }
        this.actualizarView();
        this.setAlerta(ALERTA_EXITO, "Curso Eliminado Exitosamente");
    }
}
