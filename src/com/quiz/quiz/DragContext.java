package com.quiz.quiz;

public class DragContext
{
	private double mouseAnchorX;
	private double mouseAnchorY;
	private double initialTranslateX;
	private double initialTranslateY;

	public double getMouseAnchorX()
	{
		return mouseAnchorX;
	}

	public void setMouseAnchorX(double mouseAnchorX)
	{
		this.mouseAnchorX = mouseAnchorX;
	}

	public double getMouseAnchorY()
	{
		return mouseAnchorY;
	}

	public void setMouseAnchorY(double mouseAnchorY)
	{
		this.mouseAnchorY = mouseAnchorY;
	}

	public double getInitialTranslateX()
	{
		return initialTranslateX;
	}

	public void setInitialTranslateX(double initialTranslateX)
	{
		this.initialTranslateX = initialTranslateX;
	}

	public double getInitialTranslateY()
	{
		return initialTranslateY;
	}

	public void setInitialTranslateY(double initialTranslateY)
	{
		this.initialTranslateY = initialTranslateY;
	}
}