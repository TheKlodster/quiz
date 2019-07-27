package main;

import java.util.Random;

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
		int answer = getAnswer(question);
		return inputAnswer == answer;
	}

	public int getAnswer(String question)
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
		return answer;
	}
}
