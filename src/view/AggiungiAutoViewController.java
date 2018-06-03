package view;

import java.sql.SQLException;
import java.util.ArrayList;

import application.AgenziaController;
import application.AutoController;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Auto;
import model.DAO;

public class AggiungiAutoViewController {
	
	private Stage dialogStage;
	
	@FXML
	private TextField targaTF;
	@FXML
	private TextField modelloTF;
	@FXML
	private TextField chilometraggioTF;
	@FXML
	private ChoiceBox<Integer> fasciaCB;
	@FXML
	private ChoiceBox<String> agenziaCB;
	@FXML
	private ChoiceBox<String> statoCB;
	@FXML
	private Button aggiungiAutoButton;
	@FXML
	private Button annullaButton;
	private ObservableList<Integer> fasce = FXCollections.observableArrayList();
	private ObservableList<String> agenzie = FXCollections.observableArrayList();
	private ObservableList<String> stati = FXCollections.observableArrayList();
	
	private ObservableList<Auto> listaAuto;
	
	@FXML
	private void aggiungiAuto()
	{
		if (formRiempito())
		{
			//Ottengo i dati inseriti
			 String targa = targaTF.getText();
			 int fascia = fasce.get(fasciaCB.getSelectionModel().getSelectedIndex());
			 String modello = modelloTF.getText();
			 String agenziaNome = agenzie.get(agenziaCB.getSelectionModel().getSelectedIndex());
			 String agenzia = "";
			 try { agenzia = DAO.cercaS("SELECT PartitaIVA FROM agenzia WHERE Nome = '" + agenziaNome + "'");
			 } catch (SQLException e) {e.printStackTrace();}
			 int stato = statoStringToInt(stati.get(statoCB.getSelectionModel().getSelectedIndex()));
			 int km = Integer.parseInt(chilometraggioTF.getText());
			 
			 //Creo l'auto da aggiungere
			 Auto tempAuto = new Auto();
			 tempAuto.setTarga(targa);
			 tempAuto.setFascia(fascia);
			 tempAuto.setModello(modello);
			 tempAuto.setAgenzia(agenzia);
			 tempAuto.setStato(stato);
			 tempAuto.setChilometraggio(km);
			 
			 //Se i dati sono corretti
			if (AutoController.verificaAuto(tempAuto).equals(""))
			{
				 if (AutoController.aggiungiAuto(tempAuto))
				 {
					 Main.lanciaInfo("Nuova Auto", "Auto aggiunta!");
					 listaAuto.add(tempAuto);
					 dialogStage.close();
				 }else
				 {
					 Main.lanciaWarning("Nuova Auto", "Auto NON aggiunta!");
				 }
			}else
			{
				Main.lanciaWarning("Impossibile aggiungere auto", AutoController.verificaAuto(tempAuto));
			}
		}else
		{
			Main.lanciaWarning("Impossibile aggiungere auto", "Compilare correttamente tutti i campi del form");
		}
	}
	
	@FXML
	private void premutAnnulla()
	{
		 dialogStage.close();
	}
	
	@FXML
	private void initialize() 
	{
		configuraPicker();
    }
	
	private boolean formRiempito()
	{
		boolean risposta = false;
		
		try {
			Integer.parseInt(chilometraggioTF.getText());
			risposta = true;
		} catch (NumberFormatException e) 
		{
			risposta = false;
		}
		
		return risposta;
	}
	
	private void configuraPicker()
	{
		
		ArrayList<Integer> listaFascePresenti = DAO.getListaInteri("fascia", "idFascia");
		for (Integer fascia: listaFascePresenti)
		{
			fasce.add(fascia);
		}
		fasciaCB.setItems(fasce);	
		fasciaCB.getSelectionModel().selectFirst();
		
		
		ArrayList<String> listaAgenziePresenti = AgenziaController.getNomiAgenzie();
		for (String agenzia: listaAgenziePresenti)
		{
			agenzie.add(agenzia);
		}
		agenziaCB.setItems(agenzie);
		agenziaCB.getSelectionModel().selectFirst();
		
		
		stati.add("Libera");
		stati.add("Ordinaria");
		stati.add("Straordinaria");
		statoCB.setItems(stati);
		statoCB.getSelectionModel().selectFirst();
	}
	
	private int statoStringToInt(String stato)
	{
		int risultato = 99;
		
		switch (stato) {
		case "Libera":
			risultato = 1;
			break;
		case "In uso":
			risultato = 2;
			break;
		case "Ordinaria":
			risultato = 3;
			break;
		case "Straordinaria":
			risultato = 4;
			break;

		default:
			break;
		}
		
		return risultato;
	}
	
    public void setDialogStage(Stage dialogStage) 
    {
        this.dialogStage = dialogStage;
    }

	public ObservableList<Auto> getListaAuto() {
		return listaAuto;
	}

	public void setListaAuto(ObservableList<Auto> listaAuto) {
		this.listaAuto = listaAuto;
	}

}
