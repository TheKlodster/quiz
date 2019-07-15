package com.quiz.quiz;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.omg.PortableServer.POA;

public class GUI extends Application
{

	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
		menu();
	}

	private void menu()
	{
		VBox root = new VBox();
		Label title = new Label("QuizConsole");
		title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
		Button btnQuiz = new Button("QuizConsole");
		btnQuiz.setOnAction(event -> quiz());
		Button btnCreate = new Button("Create");
		Button btnQuit = new Button("Quit");
		btnQuit.setOnAction(event -> System.exit(0));
		root.getChildren().addAll(title, btnQuiz, btnCreate, btnQuit);
		root.setPadding(new Insets(10));
		root.setAlignment(Pos.CENTER);
		root.setSpacing(10.00);
		Scene scene = new Scene(root, 250, 250);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void quiz()
	{
		VBox root = new VBox();

		Scene scene = new Scene(root, 250, 250);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
