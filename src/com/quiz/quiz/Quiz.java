package com.quiz.quiz;

import java.util.Random;

/**
 * New Quiz class to be used in the GUI
 */
public class Quiz
{
	private String[] operator = {"+", "-", "x", "%"};
	private Random random = new Random();
	private int firstNo = 0;
	private int secondNo = 0;

	public String getNextQuestion()
	{
		int firstNo = random.nextInt(20);
		int secondNo = random.nextInt(20);
		int i = random.nextInt(operator.length);

		if (operator[i].equals("%"))
		{
			secondNo += 1;
		}

		this.firstNo = firstNo;
		this.secondNo = secondNo;

		return "What is " + firstNo + " " + operator[i] + " " + secondNo + " = ";
	}

	public boolean checkAnswer(int inputAnswer, String question)
	{
		int answer = 0;
		if (question.contains("+"))
		{
			answer = (firstNo + secondNo);
		}
		else if (question.contains("-"))
		{
			answer = (firstNo - secondNo);
		}
		else if (question.contains("x"))
		{
			answer = (firstNo * secondNo);
		}
		else if (question.contains("%"))
		{
			answer = (firstNo % secondNo);
		}
		return inputAnswer == answer;
	}

	public int getAnswer(int nxt)
	{
		int answer = 0;
		switch (operator[nxt-1])
		{
			case "+":
				answer = (firstNo + secondNo);
				break;
			case "-":
				answer = (firstNo - secondNo);
				break;
			case "x":
				answer = (firstNo * secondNo);
				break;
			case "%":
				answer = (firstNo % secondNo);
				break;
		}

		return answer;
	}
}
