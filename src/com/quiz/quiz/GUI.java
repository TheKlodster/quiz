package com.quiz.quiz;

import com.sun.org.apache.regexp.internal.RECompiler;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.util.Optional;

public class GUI extends Application
{

	private final BooleanProperty dragModeActiveProperty = new SimpleBooleanProperty(this, "dragModeActive", true);
	private Stage primaryStage;
	private Quiz quizGame;
	private int nxt;
	private int correct;
	private boolean inp = true;
	private Button currBtn;

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
	 * Creates a baseGUI function
	 *
	 * @param root
	 * @param nodes
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
		}
		else if (root instanceof BorderPane)
		{
			maximized = true;
		}
		Scene scene = new Scene(root, 250, 250);
		scene.getStylesheets().add("css/GUI.css");
		primaryStage.setScene(scene);
		primaryStage.setMaximized(maximized);
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
		CheckMenuItem moveNodes = new CheckMenuItem("Move Nodes");
		dragModeActiveProperty.bind(moveNodes.selectedProperty());
		root.setOnKeyReleased(event ->
		{
			if (event.getCode() == KeyCode.E)
			{
				moveNodes.setSelected(!moveNodes.isSelected());
				System.out.println("E key pressed");
			}
		});
		edit.getItems().addAll(copy, cut, paste, s3, undo, redo, moveNodes);
		//menu items for help
		MenuItem gRepo = new MenuItem("Github Repository");
		gRepo.setOnAction(event -> getHostServices().showDocument("https://github.com/rodude123/Quiz"));
		MenuItem about = new MenuItem("About");
		help.getItems().addAll(gRepo, about);

		VBox questions = new VBox();
		questions.getChildren().add(createQustionType("long answer"));
		questions.setStyle("-fx-border-color: blue;");
		root.setCenter(questions);
		questions.setOnDragOver(event ->
		{
			event.acceptTransferModes(TransferMode.MOVE);
		});
		questions.setOnDragDropped(event ->
		{
			event.setDropCompleted(true);
			questions.getChildren().add(createQustionType("long answer"));
			event.consume();
		});

		questions.setOnDragDone(event -> {});

		VBox sidePanel = new VBox();
		root.setLeft(sidePanel);
		sidePanel.setMinWidth(100);
		//sidePanel.setStyle("-fx-background-color: red");
		sidePanel.setStyle("-fx-border-color: red; -fx-min-width: 100px;");
		sidePanel.setSpacing(10);

		String[] types = new String[]{"multiple choice", "long answer", "short answer"};
		for (String type : types)
		{
			Button btn = new Button(type);
			btn.getStyleClass().add("qBtn");
			btn.setStyle("-fx-border-color: black;");
			btn.setOnDragDetected(event ->
			{
				currBtn = (Button) event.getSource();
				event.consume();
			});
			sidePanel.getChildren().add(btn);
		}
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

	private BorderPane createQustionType(String text)
	{
		BorderPane bp = new BorderPane();
		Button r = new Button("", new ImageView(new Image(getClass().getResourceAsStream("/images/upArrow.png"))));
		r.getStyleClass().add("btn");
		Button l = new Button("", new ImageView(new Image(getClass().getResourceAsStream("/images/downArrow.png"))));
		l.getStyleClass().add("btn");
		Button info = new Button(text);
		info.getStyleClass().add("btn");
		bp.setStyle("-fx-border-color: black;");
		bp.setCenter(info);
		bp.setRight(r);
		bp.setLeft(l);
		return bp;
	}

	private Node makeDraggable(final Node node)
	{
		final DragContext dragContext = new DragContext();
		final Group wrapGroup = new Group(node);

		wrapGroup.addEventFilter(
				MouseEvent.ANY,
				mouseEvent ->
				{
					if (dragModeActiveProperty.get())
					{
						// disable mouse events for all children
						mouseEvent.consume();
					}
				});

		wrapGroup.addEventFilter(
				MouseEvent.MOUSE_PRESSED,
				mouseEvent ->
				{
					if (dragModeActiveProperty.get())
					{
						// remember initial mouse cursor coordinates
						// and node position
						dragContext.setMouseAnchorX(mouseEvent.getX());
						dragContext.setMouseAnchorY(mouseEvent.getY());
						dragContext.setInitialTranslateX(node.getTranslateX());
						dragContext.setInitialTranslateY(node.getTranslateY());
					}
				});

		wrapGroup.addEventFilter(
				MouseEvent.MOUSE_DRAGGED,
				mouseEvent ->
				{
					if (dragModeActiveProperty.get())
					{
						// shift node from its initial position by delta
						// calculated from mouse cursor movement
						node.setTranslateX(dragContext.getInitialTranslateX() + mouseEvent.getX() - dragContext.getMouseAnchorX());
						node.setTranslateY(dragContext.getInitialTranslateY() + mouseEvent.getY() - dragContext.getMouseAnchorY());
					}
				});

		return wrapGroup;
	}
}
