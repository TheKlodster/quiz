package main;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

import java.awt.*;
import java.util.Optional;


public class GUI extends Application
{

	private Stage primaryStage;
	private Quiz quizGame;
	private int nxt;
	private int correct;
	private boolean inp = true;
	private ToggleButton currBtn;
	private VBox questions = new VBox();

	public static void main(String[] args)
	{
		launch(args);
	}

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
		btnQuiz.getStyleClass().addAll("btn", "btn-default");
		btnQuiz.setOnAction(event -> quiz());
		btnQuiz.setMaxWidth(75);
		Button btnCreate = new Button("Create");
		btnCreate.setOnAction(event -> create());
		btnCreate.getStyleClass().addAll("btn", "btn-default");
		btnCreate.setMaxWidth(75);
		Button btnQuit = new Button("Quit");
		btnQuit.setOnAction(event -> System.exit(0));
		btnQuit.setMaxWidth(75);
		btnQuit.getStyleClass().addAll("btn", "btn-danger");
		baseGUI(root, title, btnQuiz, btnCreate, btnQuit);
		title.requestFocus();
	}

	/**
	 * baseGUI function
	 * Creates a baseGUI function
	 *
	 * @param root main panel
	 * @param nodes nodes to be added if they can be added this way
	 */
	private void baseGUI(Pane root, Node... nodes)
	{
		boolean maximized = false;
		if (root instanceof VBox)
		{
			((VBox) root).setAlignment(Pos.CENTER);
			((VBox) root).setSpacing(10);
			root.getChildren().addAll(nodes);
			root.setPadding(new Insets(10));
			maximized = false;
		}
		else if (root instanceof BorderPane)
		{
			maximized = true;
		}
		Scene scene = new Scene(root, 250, 250);
		scene.getStylesheets().add("css/GUI.css");
		scene.getStylesheets().add("css/bootstrapfx.css");
		primaryStage.setScene(scene);
		primaryStage.setMaximized(maximized);
		primaryStage.show();
	}

	/**
	 * Quiz function
	 * GUI for the quiz game
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
					checkAnswer(answerField.getText(), qLabel.getText());
				}
			}
		});
		answerField.textProperty().addListener((observable -> inp = true));
		answerField.setAlignment(Pos.CENTER);
		answerField.setMaxWidth(100);
		Button submitBtn = new Button("Submit");
		submitBtn.getStyleClass().addAll("btn", "btn-default");
		submitBtn.setOnAction(event -> checkAnswer(answerField.getText(), qLabel.getText()));
		submitBtn.setMaxWidth(100);
		baseGUI(root, title, qLabel, answerField, submitBtn);
	}

	/**
	 * Create function
	 */
	private void create()
	{
		//root pane
		BorderPane root = new BorderPane();
		baseGUI(root);
		//menu bar
		MenuBar mb = new MenuBar();
		root.setTop(mb);
		//Menu lists
		Menu file = new Menu("File");
		Menu edit = new Menu("Edit");
		Menu help = new Menu("Help");
		mb.getMenus().addAll(file, edit, help);
		//menu items for file
		MenuItem newFile = new MenuItem("New");
		newFile.setOnAction(event -> questions.getChildren().clear());
		MenuItem openFile = new MenuItem("Open");
		SeparatorMenuItem s1 = new SeparatorMenuItem();
		MenuItem saveFile = new MenuItem("Save");
		MenuItem saveAsFile = new MenuItem("Save As");
		SeparatorMenuItem s2 = new SeparatorMenuItem();
		MenuItem export = new MenuItem("Export");
		MenuItem mainMenu = new MenuItem("Main Menu");
		mainMenu.setOnAction(event -> menu());
		MenuItem close = new MenuItem("Close");
		close.setOnAction(event -> System.exit(0));
		file.getItems().addAll(newFile, openFile, s1, saveFile, saveAsFile, s2, export, mainMenu, close);
		//menu items for edit
		MenuItem copy = new MenuItem("Copy");
		MenuItem cut = new MenuItem("Cut");
		MenuItem paste = new MenuItem("Paste");
		SeparatorMenuItem s3 = new SeparatorMenuItem();
		MenuItem undo = new MenuItem("Undo");
		MenuItem redo = new MenuItem("Redo");
		primaryStage.getScene().setOnKeyReleased(event ->
		{
			if (event.isControlDown() && event.getCode() == KeyCode.N)
			{
				questions.getChildren().clear();
			}
			if (event.isControlDown() && event.getCode() == KeyCode.M)
			{
				menu();
			}
			if (event.isControlDown() && event.getCode() == KeyCode.W)
			{
				System.exit(0);
			}
		});
		edit.getItems().addAll(copy, cut, paste, s3, undo, redo);
		//menu items for help
		MenuItem gRepo = new MenuItem("Github Repository");
		gRepo.setOnAction(event -> getHostServices().showDocument("https://github.com/rodude123/Quiz"));
		MenuItem about = new MenuItem("About");
		help.getItems().addAll(gRepo, about);

		//The VBox to hold the questions
		root.setCenter(questions);
		questions.setSpacing(20);
		questions.setPadding(new Insets(0, 250, 0, 250));
		questions.setAlignment(Pos.TOP_CENTER);

		questions.setOnDragOver(event ->
		{
			if (event.getGestureSource() == currBtn && event.getDragboard().hasString())
			{
				event.acceptTransferModes(TransferMode.MOVE);
			}
			event.consume();
		});
		questions.setOnDragDropped(event ->
		{
			System.out.println("Drag dropped");
			Dragboard db = event.getDragboard();
			boolean success = false;
			if (db.hasString())
			{
				questions.getChildren().add(createQustionType(db.getString()));
				currBtn.setSelected(false);
				success = true;
			}
			event.setDropCompleted(success);
			event.consume();
		});

		primaryStage.widthProperty().addListener((observable, oldValue, newValue) ->
		{
			System.out.println("width changing to " + primaryStage.getWidth());
			//if statement to act as media query
			if (primaryStage.getWidth() < 1024.0 && primaryStage.getWidth() > 769.0)
			{
				questions.setPadding(new Insets(0, 100, 0, 100));
			}
			else if (primaryStage.getWidth() <= 768.0)
			{
				questions.setPadding(new Insets(0, 50, 0, 50));
			}
			else
			{
				questions.setPadding(new Insets(0, 250, 0, 250));
			}
		});

		//The VBox of draggable questions
		Panel quest = new Panel("Questions");
		root.setLeft(quest);
		quest.getStyleClass().add("panel-default");
		VBox questElem = new VBox();
		quest.setBody(questElem);
		questElem.setMinWidth(100);
		questElem.setStyle("-fx-min-width: 100px;");
		questElem.setSpacing(10);

		String[] types = new String[]{"multiple choice", "long answer", "short answer"};
		for (String type : types)
		{
			ToggleButton btn = new ToggleButton(type);
			btn.getStyleClass().addAll("btn", "qBtn");
			btn.setStyle("-fx-border-color: black;");
			btn.setOnDragDetected(event ->
			{
				currBtn = (ToggleButton) event.getSource();
				System.out.println("Dragging node");
				Dragboard db = btn.startDragAndDrop(TransferMode.ANY);
				ClipboardContent content = new ClipboardContent();
				content.putString(btn.getText());
				db.setContent(content);
				currBtn.setSelected(true);
				event.consume();
			});
			questElem.getChildren().add(btn);
		}

		Panel propertiesPanel = new Panel("Properties");
		VBox properties = new VBox();
		root.setRight(propertiesPanel);
		propertiesPanel.setBody(properties);
		propertiesPanel.getStyleClass().add("panel-default");
		properties.setMinWidth(200);
		properties.setSpacing(10);

		root.requestFocus();
	}

	/**
	 * checkAnswer function
	 * Checks the answer from a given answer and question
	 *
	 * @param answer user answer
	 * @param question question given to the user
	 */
	private void checkAnswer(String answer, String question)
	{
		if (!answer.equals(""))
		{
			nxt++;
			if (quizGame.checkAnswer(Integer.parseInt(answer), question))
			{
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
				Alert wrong = new Alert(Alert.AlertType.ERROR, "The answer should be: " + quizGame.getAnswer(question), ButtonType.NEXT);
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
			inp = false;
		}
	}

	private BorderPane createQustionType(String text)
	{
		BorderPane bp = new BorderPane();
		//right button
		Button r = new Button("", new ImageView(new Image(getClass().getResourceAsStream("/images/upArrow.png"))));
		r.getStyleClass().add("btns");
		r.setOnAction(event ->
		{
			int index = questions.getChildren().indexOf(bp);
			if (index != 0)
			{
				Node toSwap = questions.getChildren().get(index);
				Node swapFrom = questions.getChildren().get(index - 1);
				questions.getChildren().set(index, new Pane());
				questions.getChildren().set(index - 1, toSwap);
				questions.getChildren().set(index, swapFrom);
			}
		});
		//left button
		Button l = new Button("", new ImageView(new Image(getClass().getResourceAsStream("/images/downArrow.png"))));
		l.getStyleClass().add("btns");
		l.setOnAction(event ->
		{
			int index = questions.getChildren().indexOf(bp);
			if (index != questions.getChildren().size() - 1)
			{
				Node toSwap = questions.getChildren().get(index);
				Node swapFrom = questions.getChildren().get(index + 1);
				questions.getChildren().set(index, new Pane());
				questions.getChildren().set(index + 1, toSwap);
				questions.getChildren().set(index, swapFrom);
			}
		});
		//info
		Button info = new Button(text);
		info.setOnDragDetected(event ->
		{
			Dragboard db = info.startDragAndDrop(TransferMode.ANY);
			ClipboardContent content = new ClipboardContent();
			SnapshotParameters snapParams = new SnapshotParameters();
			snapParams.setFill(Color.TRANSPARENT);
			snapParams.setTransform(Transform.scale(5, 5));
			Image dragImage = bp.snapshot(snapParams, null);
			content.putImage(dragImage);
			db.setContent(content);
			db.setDragView(dragImage);
			event.consume();
		});
		info.setOnDragOver(event ->
		{
			if (event.getGestureSource() != info && event.getDragboard().hasImage())
			{
				event.acceptTransferModes(TransferMode.MOVE);
			}
			event.consume();
		});
		info.setOnDragDropped(event ->
		{
			System.out.println("Dragged node");
			Button target = (Button) event.getGestureTarget();
			Button source = (Button) event.getGestureSource();
			int targetIndex = questions.getChildren().indexOf(target.getParent());
			int sourceIndex = questions.getChildren().indexOf(source.getParent());

			questions.getChildren().set(sourceIndex, new Pane());
			questions.getChildren().set(targetIndex, source.getParent());
			questions.getChildren().set(sourceIndex, target.getParent());

			System.out.println(targetIndex);
			System.out.println(sourceIndex);
		});
		info.getStyleClass().add("btns");
		bp.setStyle("-fx-border-color: black;");
		bp.setCenter(info);
		bp.setRight(r);
		bp.setLeft(l);
		return bp;
	}

}
