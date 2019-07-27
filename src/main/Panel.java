package main;

import javafx.beans.DefaultProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

enum Orientation
{
	top, bottom, right, left
}

@DefaultProperty("body")
public class Panel extends BorderPane
{
	private ObjectProperty<Node> heading;
	private ObjectProperty<Node> body;
	private ObjectProperty<Node> footer;
	private Orientation orientation;

	public Panel()
	{
		getStyleClass().setAll("panel");
		this.orientation = Orientation.top;
	}

	public Panel(String title)
	{
		this();
		setText(title);
	}

	public Panel(Orientation orientation)
	{
		getStyleClass().setAll("panel");
		this.orientation = orientation;
	}

	public Panel(String title, Orientation orientation)
	{
		this(orientation);
		setText(title);
	}

	public final ObjectProperty<Node> headingProperty()
	{
		if (this.heading == null)
		{
			this.heading = new SimpleObjectProperty<>(this, "heading");
			this.heading.addListener((v, o, n) ->
			{
				if (null != n)
				{
					GridPane box = new GridPane();
					box.getStyleClass().setAll("panel-heading");
					GridPane.setColumnIndex(n, 0);
					GridPane.setRowIndex(n, 0);
					GridPane.setHgrow(n, Priority.ALWAYS);
					GridPane.setVgrow(n, Priority.ALWAYS);
					box.getChildren().add(n);

					switch (orientation)
					{
						case top:
							setTop(box);
							break;
						case right:
							setRight(box);
							break;
						case bottom:
							setBottom(box);
							break;
						case left:
							setLeft(box);
							break;
						default:
							break;
					}
				}
			});
		}
		return this.heading;
	}

	public final Node getHeading()
	{
		return this.heading == null ? null : this.heading.get();
	}

	public final void setHeading(Node content)
	{
		this.headingProperty().set(content);
	}

	public final ObjectProperty<Node> bodyProperty()
	{
		if (this.body == null)
		{
			this.body = new SimpleObjectProperty<>(this, "body");
			this.body.addListener((v, o, n) ->
			{
				if (null != n)
				{
					GridPane box = new GridPane();
					box.getStyleClass().setAll("panel-body");
					GridPane.setColumnIndex(n, 0);
					GridPane.setRowIndex(n, 0);
					GridPane.setHgrow(n, Priority.ALWAYS);
					GridPane.setVgrow(n, Priority.ALWAYS);
					box.getChildren().add(n);
					setCenter(box);
				}
			});
		}
		return this.body;
	}

	public final Node getBody()
	{
		return this.body == null ? null : this.body.get();
	}

	public final void setBody(Node body)
	{
		this.bodyProperty().set(body);
	}

	public final ObjectProperty<Node> footerProperty()
	{
		if (this.footer == null)
		{
			this.footer = new SimpleObjectProperty<>(this, "footer");
			this.footer.addListener((v, o, n) ->
			{
				if (null != n)
				{
					GridPane box = new GridPane();
					box.getStyleClass().setAll("panel-footer");
					GridPane.setColumnIndex(n, 0);
					GridPane.setRowIndex(n, 0);
					GridPane.setHgrow(n, Priority.ALWAYS);
					GridPane.setVgrow(n, Priority.ALWAYS);
					box.getChildren().add(n);

					switch (orientation)
					{
						case top:
							setBottom(box);
							break;
						case right:
							setLeft(box);
							break;
						case bottom:
							setTop(box);
							break;
						case left:
							setRight(box);
							break;
						default:
							break;
					}
				}
			});
		}
		return this.footer;
	}

	public final Node getFooter()
	{
		return this.footer == null ? null : this.footer.get();
	}

	public final void setFooter(Node content)
	{
		this.footerProperty().set(content);
	}

	public String getText()
	{
		Node node = headingProperty().get();
		if (node instanceof Labeled)
		{
			return ((Labeled) node).getText();
		}
		return null;
	}

	public void setText(String text)
	{
		Label label = new Label(text);
		label.getStyleClass().add("panel-title");
		headingProperty().set(label);
	}

	public Orientation getOrientation()
	{
		return orientation;
	}

	public void setOrientation(Orientation orientation)
	{
		this.orientation = orientation;
	}
}
