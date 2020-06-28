package it.polito.tdp.ufo;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.model.AnnoAvvistamento;
import it.polito.tdp.ufo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<AnnoAvvistamento> boxAnno;

    @FXML
    private ComboBox<String> boxStato;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleAnalizza(ActionEvent event) {
    	txtResult.clear();
    	if(boxAnno==null)
    		txtResult.appendText("Inserisci un anno");
    	if(boxStato.getValue()==null)
    		txtResult.appendText("inserisci uno stato");
    	txtResult.appendText("Lista di precendenti\n");
    	for(int i=0; i<model.getPrecedenti(boxStato.getValue()).size();i++ )
    		txtResult.appendText(model.getPrecedenti(boxStato.getValue()).get(i)+"\n");
    	txtResult.appendText("\n\n\nLista di Successivi\n");
    	for(int i=0; i<model.getSuccessivi(boxStato.getValue()).size();i++ )
    		txtResult.appendText(model.getSuccessivi(boxStato.getValue()).get(i)+"\n");
    	txtResult.appendText("\n\n\nLista di Visita\n");
    	for(int i=0; i<model.getVisita(boxStato.getValue()).size();i++ )
    		txtResult.appendText(model.getVisita(boxStato.getValue()).get(i)+"\n");

    }

    @FXML
    void handleAvvistamenti(ActionEvent event) {
    	txtResult.clear();
    	if(boxAnno==null)
    		txtResult.appendText("Inserisci un anno");
    	model.creaGrafo(boxAnno.getValue());
    	txtResult.appendText("Grafo creato\nNumero vertici "+model.getVertex().size()+"\nNumero archi "+model.getEdge());
    	boxStato.getItems().addAll(model.getVertex());

    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	txtResult.clear();
    	if(boxAnno==null)
    		txtResult.appendText("Inserisci un anno");
    	if(boxStato.getValue()==null)
    		txtResult.appendText("inserisci uno stato");
    	txtResult.appendText("CAMMINO MASSIMO\n");
    	for(String s: model.trovaRicorsivo(boxStato.getValue())) {
    		txtResult.appendText(s+" ");
    	}
    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Ufo.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		txtResult.setEditable(false);
		boxAnno.getItems().addAll(model.getAnnoAvvistamento());
	}
}
