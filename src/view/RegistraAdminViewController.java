package view;

import java.sql.SQLException;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.DAO;
import utility.Crittografia;

public class RegistraAdminViewController 
{
	
	private Main  mainApp;
	
	@FXML
	private TextField usernameTF;
	
	@FXML
	private PasswordField pswdTF, repPswsTF;
	
	@FXML
	private Button registratiButton, annullaButton;
	
	@FXML
	private void premutoRegistrati()
	{
		if (verificaForm())
		{
			if (isNuovoUtente())
			{
				String username = usernameTF.getText();
				String pswd = pswdTF.getText();
				String enPswd = Crittografia.encrypt(pswd);
				//Registro l'utente nel DB
				String comando = String.format("INSERT INTO `admin` (Username,PASSWORD) VALUES ('%s','%s')", username,enPswd);
				boolean registrato = DAO.esegui(comando);
				
				if (registrato)
				{
					Main.lanciaInfo("Registrazione avvenuta", "L'Admin " + username + " è stato registrato!");
					
					//Reimposto l'interfaccia di Login su MainApp
					mainApp.lanciaLogin();
				}
				
			}
		}
		
	}
	
	@FXML
	private void premutoAnnulla()
	{
		//Reimposto l'interfaccia di Login su MainApp
		mainApp.lanciaLogin();
	}
	
	private boolean verificaForm()
	{
		boolean risposta = false;
		
		String username = usernameTF.getText();
		String pswd = pswdTF.getText();
		String rePswd = repPswsTF.getText();
		
		//Se i tre campi sono stati valorizzati
		if (username.length() > 0 && pswd.length() > 0 && rePswd.length() > 0)
		{
			if (username.length() > 6)
			{
				if (pswd.equals(rePswd))
				{
					risposta = true;
				}else
				{
					Main.lanciaWarning("Form non corretto","Le password devono essere uguali");
				}
			}else
			{
				Main.lanciaWarning("Form non corretto","Username deve essere maggiore di 6 caratteri");
			}
		}else
		{
			Main.lanciaWarning("Form non corretto","Non sono stati riempiti tutti i campi!");
		}
			
		return risposta;
		
	}
	
	private boolean isNuovoUtente()
	{
		boolean risposta = false;
		
			//Cerco se nel DB è presente già un utente con questo nome
			String comando = String.format("SELECT Username FROM admin WHERE Username = '%s'", usernameTF.getText());
			try {
				//DAO.connetti();
				boolean utenteTrovato = DAO.cerca(comando);
				//Se non ho trovato nessuno (quindi utenteTrovat == false) allora isNuovoUtente.risposta deve essere true
				risposta = !utenteTrovato;
			} catch (SQLException e) {
				System.out.println("Errore in RegistraAdminViewController.isNuovoUtente()");
				e.printStackTrace();
			}
			
			if (!risposta)
			{
				Main.lanciaWarning("Utente già esistente","Inserisci un nuovo Username");
			}
		
		return risposta;
	}
	

	public Main getMainApp() {
		return mainApp;
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}
}
