//clientApp.java

package ct414;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Client{
	public static void main(String args[]){
		if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "ExamServer";
            Registry registry = LocateRegistry.getRegistry(args[0]);
            ExamServer assessServ = (ExamServer) registry.lookup(name);
            Scanner scan = new Scanner(System.in);
            String password = args[2], choice = "";
            int studID = Integer.parseInt(args[1]), token = assessServ.login(studID, password);
            System.out.print("Welcome to Assignment Completer.\n");

            while (true){
            	
            	System.out.print("Available Assessments: "+ assessServ.getAvailableSummary(token, studID));
				System.out.println("Enter 'exit' To End Program and Submit.");
				
            	System.out.println("Enter Assessment Number: ");
            	
            	choice = scan.next();
            	
           	 	if (new String("exit").equals(choice)){
            		break;
           	 	}
           	 	
            	while (new String("").equals(choice)){
            		System.out.format("%s :Invalid. Enter Assessment Number: ", choice);
            		choice = scan.next();
            	}

            	Assessment assignment = assessServ.getAssessment(token, studID, choice);
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
    	List<Question> Questions = exam.getQuestions(); 
		List<Integer> UnAnsweredQuestions = new ArrayList<Integer>(Questions.size());
    	Scanner scan = new Scanner(System.in);
    	int choice = -2;
		int answer; 
		int ind;
		Question temp;
		
		System.out.println("Enter 0 to exit assessment.");
    	for (int i = 1; i <= Questions.size(); i++){
    		UnAnsweredQuestions.add(i-1,i);
    	}
    	
    	while (true){
    		for( int j = 0; j< UnAnsweredQuestions.size(); j++){
    		System.out.format("Question %d\n", UnAnsweredQuestions.get(j));
    		temp = Questions.get(UnAnsweredQuestions.get(j));
			System.out.format(temp.getQuestionDetail());
    		}
    		
    		System.out.println("Enter Question Number:");
    		choice = Integer.parseInt(scan.next());
    		while (choice < 0 || choice > Questions.size()){
            		System.out.format("%d is not a valid input. Try again: \n", choice);
            		choice = Integer.parseInt(scan.next());
            }
            if (choice == 0){
            	break;
            }
			try{
				Question current = exam.getQuestion(choice-1);
				System.out.println(current.getQuestionDetail());
				String[] AllAns = current.getAnswerOptions();
			    for (int j = 0; j < AllAns.length; j++){
					System.out.format("%d %s \n", j+1, AllAns[j]);
				}
				System.out.println("Enter answer number: ");
				answer = Integer.parseInt(scan.next());
				while (answer < 0 || answer > AllAns.length){
					System.out.println("Invalid Number, Try Again: ");
					answer = Integer.parseInt(scan.next());
				}
				if (answer == 0){
					continue;
				}
				else{
					exam.selectAnswer(choice-1, answer-1);
					ind = UnAnsweredQuestions.indexOf(choice);
					if (ind != -1){
						UnAnsweredQuestions.remove(ind);
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
            	
