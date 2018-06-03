package view;

import java.sql.SQLException;
import java.util.ArrayList;

import application.DipendenteController;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DAO;
import model.Dipendente;
import utility.Crittografia;

public class AggiungiDipendenteViewController 
{
	private Stage dialogStage;
	
	private ObservableList<Dipendente> listaDipendenti;
	
	@FXML
	private TextField nomeTF;
	@FXML
	private TextField cognomeTF;	
	@FXML
	private TextField telefonoTF;	
	@FXML
	private TextField usernameTF;
	@FXML
	private ChoiceBox<String> agenziaCB;
	@FXML
	private PasswordField passTF;
	@FXML 
	private PasswordField repPassTF;
	@FXML
	private Button aggiungiButton;
	@FXML
	private Button annullaButton;
	
	private ObservableList<String> agenzie = FXCollections.observableArrayList();
	
	@FXML
	private void premutoAggiungi()
	{
		if (formRiempito())
		{
			if (passUguali())
			{
				//Ottengo i dati inseriti
				String nome = nomeTF.getText();
				String cognome = cognomeTF.getText();
				String telefono = telefonoTF.getText();
				String username = usernameTF.getText();
				String password = Crittografia.encrypt(passTF.getText());
				String agenziaNome = agenziaCB.getSelectionModel().getSelectedItem();
				String agenzia = "";
				 try { agenzia = DAO.cercaS("SELECT PartitaIVA FROM agenzia WHERE Nome = '" + agenziaNome + "'");
				 } catch (SQLException e) {e.printStackTrace();}
				//Creo l'user da aggiungere
				Dipendente dipendente = new Dipendente();
				dipendente.setNome(nome);
				dipendente.setCognome(cognome);
				dipendente.setTelefono(telefono);
				dipendente.setUsername(username);
				dipendente.setPassword(password);
				dipendente.setAgenzia(agenzia);
				//Se i dati sono corretti
				if (DipendenteController.verificaDipendente(dipendente).equals(""))
				{
					 if (DipendenteController.aggiungiDipendente(dipendente))
					 {
						 Main.lanciaInfo("Nuovo Dipendente", "Dipendente aggiunto!");
						 listaDipendenti.add(dipendente);
						 dialogStage.close();
					 }
				}else
				{
					Main.lanciaWarning("Aggiungi Dipendente", DipendenteController.verificaDipendente(dipendente));
				}
			}else
			{
				Main.lanciaWarning("Aggiungi Dipendente","Le due password devono essere uguali");
			}
		}else
		{
			Main.lanciaWarning("Aggiungi Dipendente", "Riempi tutti i campi del form");
		}
	}
	
	private boolean passUguali()
	{
		return (passTF.getText().equals(repPassTF.getText()));
	}
	
	private boolean formRiempito()
	{
		boolean risposta = !((nomeTF.getText().equals("")) && (cognomeTF.getText().equals("")));
		risposta = risposta && !(usernameTF.getText().equals(""));
		risposta = risposta && !(passTF.getText().equals("") && repPassTF.getText().equals(""));
		return risposta;
	}
	
	@FXML
	private void premutoAnnulla()
	{
		dialogStage.close();
	}
	
	@FXML
	public void initialize()
	{
		configuraPicker();
	}
	
	private void configuraPicker()
	{
		ArrayList<String> listaAgenziePresenti = DAO.getListaString("agenzia", "Nome");
		for (String agenzia: listaAgenziePresenti)
		{
			agenzie.add(agenzia);
		}
		agenziaCB.setItems(agenzie);
		agenziaCB.getSelectionModel().selectFirst();
	}
	
    public void setDialogStage(Stage dialogStage) 
    {
        this.dialogStage = dialogStage;
    }

	public ObservableList<Dipendente> getListaAuto() {
		return listaDipendenti;
	}

	public void setListaDipendenti(ObservableList<Dipendente> listaDipendenti) {
		this.listaDipendenti = listaDipendenti;
	}
}
