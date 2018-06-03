package view;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class DialogVerificaViewController 
{
	public static boolean isAuth = false;
	
	private static String creaAdminPassword = "godmode";
	
	@FXML
	private PasswordField pswdTF;
	
	@FXML
	private Button okButton;
	
	@FXML
	private Button cancelButton;
	
	private Stage dialogStage;
	
	@FXML
	private void initialize() 
	{
		
    }
	
    public void setDialogStage(Stage dialogStage) 
    {
        this.dialogStage = dialogStage;
    }
    
    public boolean isAuth() 
    {
        return isAuth;
    }
	
	@FXML
	private void premutoOk()
	{
		if (pswdTF.getText().equals(creaAdminPassword))
		{
			isAuth = true;
			dialogStage.close();
		}else
		{
			Main.lanciaWarning("Password non corretta","Contattare l'amministratore per ricevere la password di amministrazione");
			isAuth = false;
		}
	}
	
	@FXML
	private void premutoCancel()
	{
		isAuth = false;
		dialogStage.close();
	}

}
