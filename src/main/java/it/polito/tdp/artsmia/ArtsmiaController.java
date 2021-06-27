package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model ;
	private boolean grafoCreato = false;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	txtResult.clear();
    	if(!this.grafoCreato) {
    		this.txtResult.appendText("Necessario creare il grafo prima di procedere\n");
    		return;
    	}
    	this.txtResult.appendText(this.model.getArtistiConnessi());
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	if(!this.grafoCreato) {
    		this.txtResult.appendText("Necessario creare il grafo prima di procedere\n");
    		return;
    	}
    	if(this.txtArtista.getText().equals("")) {
    		this.txtResult.appendText("Selezionare l'ID\n");
    		return;
    	}
    	int artist_id = 0;
    	try {
    		artist_id = Integer.parseInt(this.txtArtista.getText());
    	}catch (NumberFormatException e) {
    		this.txtResult.appendText("L'identificativo deve essere un intero positivo\n");
    		return;
    	}
    	this.txtResult.appendText(this.model.calcolaPercorso(artist_id));
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	String ruolo = this.boxRuolo.getValue();
    	if(ruolo == null) {
    		this.txtResult.appendText("Selezionare un ruolo\n");
    		return;
    	}
    	this.model.creaGrafo(ruolo);
    	this.grafoCreato = true;
    	this.txtResult.appendText("Grafo creato!\nVertici: "+this.model.getNumVertici()+"\nArchi: "+this.model.getNumEdges()+"\n");
    	
    }

    public void setModel(Model model) {
    	this.model = model;
    	this.boxRuolo.getItems().addAll(this.model.getAllRoles());
    }

    
    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }
}
