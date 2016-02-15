//Conor Hickey & Ronan Loftus
//Client.java

package ct414;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Client{
	public static void main(String args[]){
	//Sets up RMI security manager
		if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
        //Basic server setup
			String name = "ExamServer";
            Registry registry = LocateRegistry.getRegistry(args[0]);
            ExamServer assessServ = (ExamServer) registry.lookup(name);
            //Setup text scanner
            Scanner scan = new Scanner(System.in);
            String password = args[2];
            String select = "";
            int studID = Integer.parseInt(args[1]), token = assessServ.login(studID, password);
            System.out.print("Welcome to Assignment Completer.\n");

            while (true){
            	//shows users possible assessments and then asks them which to go to
            	System.out.print("Available Assessments: "+ assessServ.getAvailableSummary(token, studID));
				System.out.println("Enter 'exit' To End Program and Submit.");
				
            	System.out.println("Enter Assessment Number: ");
            	
            	select = scan.next();
            	//used to exit and submit
           	 	if (new String("exit").equals(select)){
            		break;
           	 	}
           	 	//while blank keep asking
            	while (new String("").equals(select)){
            		System.out.format("%s :Invalid. Enter Assessment Number: ", select);
            		select = scan.next();
            	}

            	Assessment assignment = assessServ.getAssessment(token, studID, select);
				assignment  = Examination(assignment );
            	assessServ.submitAssessment(token, studID, assignment );
            }
        }
        catch (Exception e) {
		System.err.println("Warning: Exception.");
		e.printStackTrace();

        }
    }
    
    private static Assessment Examination(Assessment exam){
    	//creates list of Questions
    	List<Question> Questions = exam.getQuestions(); 
    	
    	//creates a list for unanswered questions
		List<Integer> UnAnsweredQuestions = new ArrayList<Integer>(Questions.size());
    	Scanner scan = new Scanner(System.in);
    	int select = -1;
		int answer; 
		int index;
		Question temp;
		
		System.out.println("Enter 0 to exit assessment.");
		//adds question numbers to the unanswered list 
    	for (int i = 1; i <= Questions.size(); i++){
    		UnAnsweredQuestions.add(i-1,i);
    	}  
    	while (true){
    	
    		//prints out all unanswered questions and its number
    		for( int j = 0; j< UnAnsweredQuestions.size(); j++){
    		System.out.format("Question %d\n", UnAnsweredQuestions.get(j));
    		temp = Questions.get(UnAnsweredQuestions.get(j));
			System.out.format(temp.getQuestionDetail());
    		}
    		//allows user to select next question to answer
    		System.out.println("Enter Question Number:");
    		select = Integer.parseInt(scan.next());
    		while (select < 0 || select > Questions.size()){
            		System.out.format("%d is not a valid input. Try again: \n", select);
            		select = Integer.parseInt(scan.next());
            }
            //enter 0 to get out of assesment
            if (select == 0){
            	break;
            }
			try{
				//gets selected question and shows all possible answers
				Question current = exam.getQuestion(select-1);
				System.out.println(current.getQuestionDetail());
				String[] AllAns = current.getAnswerOptions();
			    for (int j = 0; j < AllAns.length; j++){
					System.out.format("%d %s \n", j+1, AllAns[j]);
				}
				//user inputs answer
				System.out.println("Enter answer number: ");
				answer = Integer.parseInt(scan.next());
				while (answer < 0 || answer > AllAns.length){
					System.out.println("Invalid Number, Try Again: ");
					answer = Integer.parseInt(scan.next());
				}
				if (answer == 0){
					continue;
				}
				//removed the question from unanswered if it has not been already removed 
				else{
					exam.selectAnswer(select-1, answer-1);
					index = UnAnsweredQuestions.indexOf(select);
					if (index != -1){
						UnAnsweredQuestions.remove(index);
					}
				}
			}
			catch (Exception e) {
			
            System.err.println("Warning: Exception.");
            e.printStackTrace();
			}
        }
        return(exam);
    }
}
            	
