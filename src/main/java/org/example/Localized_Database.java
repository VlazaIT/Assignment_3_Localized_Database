package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Localized_Database extends Application {

    private ResourceBundle bundle;
    private Label firstNameLabel;
    private Label lastNameLabel;
    private Label emailLabel;
    @FXML
    private Button saveButton;
    @FXML
    private TextField firstNameInput;
    @FXML
    private TextField lastNameInput;
    @FXML
    private TextField emailInput;

    @Override
    public void start(Stage primaryStage) {
        ComboBox<String> languageSelector = new ComboBox<>();
        languageSelector.getItems().addAll("English", "Farsi", "Japanese");
        languageSelector.setValue("English");

        languageSelector.setOnAction(event -> {
            String selectedLanguage = languageSelector.getValue();
            if (selectedLanguage.equals("Farsi")) {
                bundle = ResourceBundle.getBundle("messages", new Locale("fa", "IR"));
            } else if (selectedLanguage.equals("Japanese")) {
                bundle = ResourceBundle.getBundle("messages", Locale.JAPAN);
            } else {
                bundle = ResourceBundle.getBundle("messages", new Locale("en", "EN"));
            }
            updateUI(primaryStage);
        });


        primaryStage.setTitle("Database Localization");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.setVgap(10);
        grid.setHgap(10);

        firstNameLabel = new Label();
        firstNameInput = new TextField();

        lastNameLabel = new Label();
        lastNameInput = new TextField();

        emailLabel = new Label();
        emailInput = new TextField();

        saveButton = new Button();

        grid.add(new Label("Select Language: "), 0, 0);
        grid.add(languageSelector, 1, 0);
        grid.add(firstNameLabel, 0, 1);
        grid.add(firstNameInput, 1, 1);
        grid.add(lastNameLabel, 0, 2);
        grid.add(lastNameInput, 1, 2);
        grid.add(emailLabel, 0, 3);
        grid.add(emailInput, 1, 3);
        grid.add(saveButton, 1, 4);

        Scene scene = new Scene(grid, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        bundle = ResourceBundle.getBundle("messages", new Locale("en", "EN"));
        updateUI(primaryStage);

        saveButton.setOnAction(e -> saveData(firstNameInput.getText(), lastNameInput.getText(), emailInput.getText(), languageSelector.getValue()));
    }

    private void updateUI(Stage primaryStage) {
        primaryStage.setTitle(bundle.getString("app.title"));
        firstNameLabel.setText(bundle.getString("label.firstName"));
        lastNameLabel.setText(bundle.getString("label.lastName"));
        emailLabel.setText(bundle.getString("label.email"));
        saveButton.setText(bundle.getString("button.save"));

        firstNameInput.setPromptText(bundle.getString("label.firstName"));
        lastNameInput.setPromptText(bundle.getString("label.lastName"));
        emailInput.setPromptText(bundle.getString("label.email"));
    }

    private void saveData(String firstName, String lastName, String email, String selectedLanguage) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://localhost:3306/localization";
            Connection conn = DriverManager.getConnection(jdbcUrl, "root", "root");

            String tableName;
            switch (selectedLanguage) {
                case "Farsi":
                    tableName = "employee_fa";
                    break;
                case "Japanese":
                    tableName = "employee_ja";
                    break;
                default:
                    tableName = "employee_en";
            }

            String sql = "INSERT INTO " + tableName + " (first_name, last_name, email) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.executeUpdate();
            conn.close();

            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle(bundle.getString("alert.title"));
                alert.setHeaderText(null);
                alert.setContentText(bundle.getString("message.saved"));
                alert.showAndWait();
            });

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
