//AssessmentImp.java

package ct414;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class AssessmentImp implements Assessment{
	private int stuID;
	private String Info;
	private Date CDate;
	private List<Question> Questions;
	private ArrayList<Integer> Answers;
	private Integer QuestionHolder;
	
	public AssessmentImp(String info, Date ClosingDate, List<Question> QuestionsIn, int studentID){
		super();
		stuID = studentID;
		Info = info;
		CDate = ClosingDate;
		Questions = QuestionsIn;
		Answers = AnswersInit(Questions.size());
	}
	//adds answers to an array 
	private ArrayList<Integer> AnswersInit(int size){
		int i;
		ArrayList<Integer> Answers = new ArrayList<Integer>(size);
		for (i = 0; i < size; i++){
			Answers.add(i,-1);
		}
		return(Answers);
	}
	// Return information about the assessment
	public String getInformation(){
		return(Info);
	}

		// Return the final date / time for submission of completed assessment 
	public Date getClosingDate(){
		return(CDate);
	}

 // Return a list of all questions and anser options
	public List<Question> getQuestions(){
		return(Questions);
	}

 	// Return one question only with answer options
	public Question getQuestion(int questionNumber) throws 
		InvalidQuestionNumber{
		return(Questions.get(questionNumber));
		}
   
	// Answer a particular question
	public void selectAnswer(int questionNumber, int optionNumber) throws
		InvalidQuestionNumber, InvalidOptionNumber{
		QuestionHolder = questionNumber;
		Answers.set(questionNumber, optionNumber);
		}


	// Return selected answer or zero if none selected yet
	public int getSelectedAnswer(int questionNumber){
	return(Answers.get(QuestionHolder));
	}

	// Return studentid associated with this assessment object
	// This will be preset on the server before object is downloade
	public int getAssociatedID(){
	return(stuID);
	}


}
