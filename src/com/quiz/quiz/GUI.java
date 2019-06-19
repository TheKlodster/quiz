package com.quiz.quiz;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.omg.PortableServer.POA;

public class GUI extends Application
{

	private Stage primaryStage;
	private Quiz quizGame;
	private int nxt;

	@Override
	public void start(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
		this.quizGame = new Quiz();
		nxt = 1;
		menu();
	}

	private void menu()
	{
		VBox root = new VBox();
		Label title = new Label("Quiz");
		title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
		Button btnQuiz = new Button("Quiz");
		btnQuiz.setOnAction(event -> quiz());
		btnQuiz.setMaxWidth(75);
		Button btnCreate = new Button("Create");
		btnCreate.setOnAction(event -> create());
		btnCreate.setMaxWidth(75);
		Button btnQuit = new Button("Quit");
		btnQuit.setOnAction(event -> System.exit(0));
		btnQuit.setMaxWidth(75);
		baseGUI(root, new Node[] {title, btnQuiz, btnCreate, btnQuit});
	}

	private void baseGUI(VBox root, Node[] nodes)
	{
		for (Node node : nodes)
		{
			root.getChildren().add(node);
		}
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
		Label title = new Label("Question " + nxt);
		title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
		title.setPadding(new Insets(10,0,10,0));
		Label qLabel = new Label(quizGame.getNextQuestion(nxt));
		qLabel.setPadding(new Insets(10,0,10,0));
		TextField answerField = new TextField();
		answerField.setAlignment(Pos.CENTER);
		answerField.setMaxWidth(100);
		Button submitBtn = new Button("Submit");
		submitBtn.setMaxWidth(100);
		baseGUI(root, new Node[] { title, qLabel, answerField, submitBtn});
	}

	private void create()
	{

	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
