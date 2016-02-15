//AssesmentImp.java

package ct414;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class AssesmentImp implements Assessment{
	private int stuID;
	private String Info;
	private Date CDate;
	private List<Question> Questions;
	private ArrayList<Integer> Answers;
	private Integer QuestionHolder;
	
	public AssesmentImp(String info, Date ClosingDate, List<Question> QuestionsIn, int studentID){
		super();
		stuID = studentID;
		Info = info;
		CDate = ClosingDate;
		Questions = QuestionsIn;
		Answers = AnswersInit(Questions.size());
	}
	
	private ArrayList<Integer> AnswersInit(int size){
		int i;
		ArrayList<Integer> Answers = new ArrayList<Integer>(size);
		for (i = 0; i < size; i++){
			Answers.add(i,-1);
		}
		return(Answers);
	}
	
	public String getInformation(){
		return(Info);
	}

	 
	public Date getClosingDate(){
		return(CDate);
	}

 
	public List<Question> getQuestions(){
		return(Questions);
	}

 
	public Question getQuestion(int questionNumber) throws 
		InvalidQuestionNumber{
		return(Questions.get(questionNumber));
		}
   

	public void selectAnswer(int questionNumber, int optionNumber) throws
		InvalidQuestionNumber, InvalidOptionNumber{
		QuestionHolder = questionNumber;
		Answers.set(questionNumber, optionNumber);
		}


	public int getSelectedAnswer(int questionNumber){
	return(Answers.get(QuestionHolder));
	}


	public int getAssociatedID(){
	return(stuID);
	}


}
