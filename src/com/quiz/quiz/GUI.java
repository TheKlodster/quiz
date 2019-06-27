package com.quiz.quiz;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class GUI extends Application
{

	private Stage primaryStage;
	private Quiz quizGame;
	private int nxt;
	private int correct;
	private boolean inp = true;

	@Override
	public void start(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
		this.quizGame = new Quiz();
		nxt = 1;
		correct = 0;
		menu();
	}

	/**
	 * Create menu for application
	 */
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
		baseGUI(root, title, btnQuiz, btnCreate, btnQuit);
	}

	/**
	 * baseGUI function
	 *
	 * @param root
	 * @param nodes
	 */
	private void baseGUI(VBox root, Node... nodes)
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

	/**
	 * Quiz
	 */
	private void quiz()
	{
		VBox root = new VBox();
		Label title = new Label("Question " + nxt);
		title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
		title.setPadding(new Insets(10, 0, 10, 0));
		Label qLabel = new Label(quizGame.getNextQuestion());
		qLabel.setPadding(new Insets(10, 0, 10, 0));
		TextField answerField = new TextField();
		answerField.setOnKeyReleased(event ->
		{
			if (event.getCode() == KeyCode.ENTER)
			{
				if (inp)
				{
					checkAnswer(answerField, qLabel);
				}
			}
		});
		answerField.textProperty().addListener((observable -> inp = true));
		answerField.setAlignment(Pos.CENTER);
		answerField.setMaxWidth(100);
		Button submitBtn = new Button("Submit");
		submitBtn.setOnAction(event -> checkAnswer(answerField, qLabel));
		submitBtn.setMaxWidth(100);
		baseGUI(root, title, qLabel, answerField, submitBtn);
	}

	private void create()
	{

	}

	private void checkAnswer(TextField answerField, Label qLabel)
	{
		if (!answerField.getText().equals(""))
		{
			nxt++;
			if (quizGame.checkAnswer(Integer.parseInt(answerField.getText()), qLabel.getText()))
			{
				answerField.setStyle(null);
				if (nxt <= 10)
				{
					quiz();
					correct++;
				}
				else
				{
					String msg;
					if (correct == 10)
					{
						msg = "all of the questions correctly";
					}
					else
					{
						msg = correct + "/10 questions correctly";
					}

					Alert fin = new Alert(Alert.AlertType.INFORMATION, "The quiz has finished You answered " + msg, ButtonType.FINISH);
					fin.setHeaderText("Finished");
					Optional<ButtonType> result = fin.showAndWait();
					if (result.isPresent())
					{
						if (result.get() == ButtonType.FINISH)
						{
							nxt = 1;
							menu();
						}
					}
				}
			}
			else
			{
				Alert wrong = new Alert(Alert.AlertType.ERROR, "The answer should be: " + quizGame.getAnswer(qLabel.getText()), ButtonType.NEXT);
				wrong.setHeaderText("Wrong answer");
				Optional<ButtonType> result = wrong.showAndWait();
				if (result.isPresent())
				{
					if (result.get() == ButtonType.NEXT)
					{
						quiz();
					}
				}
				nxt--;
			}
		}
		else
		{
			Alert empty = new Alert(Alert.AlertType.WARNING, "Answer box is empty, type something in");
			empty.setHeaderText("Empty answer box");
			empty.showAndWait();
			answerField.setStyle(null);
			inp = false;
		}
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}
