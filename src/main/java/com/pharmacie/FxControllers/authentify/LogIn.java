package com.pharmacie.FxControllers.authentify;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import com.pharmacie.models.User;
import com.pharmacie.models.Login;
import com.pharmacie.session.SessionUtil;


import java.io.IOException;
import java.util.regex.Pattern;

import com.pharmacie.controllers.UserController;
import com.pharmacie.FxControllers.messages.SimpleMessage;
import com.pharmacie.controllers.LoginController;

public class LogIn {

    private static final String EMAIL_REGEX = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$";
    
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private boolean succes = false;

    UserController userController = new UserController();
    LoginController loginController = new LoginController();


    @FXML
    private TextField emailSignInPF;

    @FXML
    private TextField passwordSignInPF;


    @FXML
    private void handleOpenNewWindow(ActionEvent event) throws IOException {
        // Charger le fichier FXML de la nouvelle fenêtre
        FXMLLoader loader = new FXMLLoader(getClass().getResource("com/pharmacie/fxml/boards/sideBoard.fxml"));
        Scene newScene = new Scene(loader.load());

        // Obtenir la stage actuelle à partir du bouton ou du nœud source
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close(); // Fermer la fenêtre actuelle

        // Créer et afficher la nouvelle fenêtre
        Stage newStage = new Stage();
        newStage.setScene(newScene);
        newStage.setTitle("Nouvelle Fenêtre");
        newStage.show();
    }


    @FXML
    void onForgotPassword(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pharmacie/fxml/pops/passwordForgot.fxml"));
        Parent rootForgot = loader.load();
        Scene scene = new Scene(rootForgot, 400, 200);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setTitle("");
        stage.show();

    }

    @FXML
    void onSignIn(ActionEvent event) throws IOException {
        if(!checkedAll())
            return;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/pharmacie/fxml/messages/simpleMessage.fxml"));
        Parent root = loader.load();
        SimpleMessage messageController = loader.getController();

        try {
            User user = userController.getUserByEmail(emailSignInPF.getText());
            if (!user.passwordBelongToMe(passwordSignInPF.getText())) {
                makeRed(this.passwordSignInPF);
                messageController.setMessage("mot de passe incorrect");
            }
            else {
                SessionUtil.setCurrentUser(user);

                Login login = new Login(user);
                loginController.addLogin(login);
                SessionUtil.setCurrentLogin(login);

                //simulation de deconnexion
                loginController.endLogin(login);
                SessionUtil.setCurrentUser(user);
                SessionUtil.setCurrentLogin(login);


                messageController.setMessage("connexion reussie");
                this.succes = true;
            }
        } catch (Exception e) {
            messageController.setMessage("connexion echouée");
            this.succes = false;
        }
        finally {
            if (this.succes) {
                Stage rootstage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                rootstage.close();
                this.openfen();
            }
            else{
                // Créer et afficher la scène
                Scene scene = new Scene(root, 300, 120);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.setScene(scene);
                stage.setTitle("");
                stage.show();
            }
        }

    }

    void makeRed(TextField field) {
        // Appliquer un style rouge au TextField
        field.setStyle("-fx-border-color: red; -fx-background-color: red;");

        // Démarrer un temporisateur pour réinitialiser le style après 2 secondes
        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
            // Réinitialiser le style après 2 secondes
            field.setStyle("-fx-border-color: green;");
        });
        pause.play();
    }

    boolean checkedAll() {
        if (this.emailSignInPF.getText().isEmpty() || !isValidEmail(this.emailSignInPF.getText())) {
            makeRed(this.emailSignInPF);
            return false;
        }
        else if (this.passwordSignInPF.getText().isEmpty()) {
            makeRed(this.passwordSignInPF);
            return false;
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    private void openfen() throws IOException {
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/com/pharmacie/fxml/boards/sideBoard.fxml"));
        Parent root = loader1.load();

        Scene scene = new Scene(root, 1200, 600);
        Stage stage = new Stage();

        stage.setScene(scene);
        stage.setTitle("");
        stage.show();
    }

}


