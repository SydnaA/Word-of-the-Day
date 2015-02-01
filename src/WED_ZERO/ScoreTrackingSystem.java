package WED_ZERO;

/**
 *
 * @author SydnaAgnehs
 */
public class ScoreTrackingSystem {
    private int numberCorrect;
    private int numberIncorrect;
    private int numberOfQuestions;

    public ScoreTrackingSystem(int numberOfQuestions) {
        this.numberOfQuestions=numberOfQuestions;
        numberCorrect=0;
        numberIncorrect=0;
    }
    public int incrementCorrect() {
        return ++numberCorrect;
    }
    
    public int incrementIncorrect() {
        return ++numberIncorrect;
    }
    
    public String getFraction() {
        return ""+numberCorrect+"/"+numberOfQuestions;
    }
    
    public int getPercentage() {
        return (int)(((float)numberCorrect/(float)numberOfQuestions)*100);
    }
    
    public int getRatio() {
        return ((int)(((float)numberCorrect/(float)numberIncorrect)*100))/100;
    }
    
    public String getResults() {
        String wordsOfEncouragement=null;
        if(((int)this.getPercentage())==100) {
            wordsOfEncouragement="PERFECTION!";
        } else if(((int)this.getPercentage())>89) {
            wordsOfEncouragement="AWESOME!";
        } else if(((int)this.getPercentage())>=80) {
            wordsOfEncouragement="Average...";
        } else if(((int)this.getPercentage())>=70) {
            wordsOfEncouragement="Need reviewing...";
        } else if(((int)this.getPercentage())<70) {
            wordsOfEncouragement=".........";
        } 
        
        return "You got "+this.getFraction()+"!\n"
                +this.getPercentage()+"\n"
                +"Your ratio is: "+this.getRatio()+"\n"
                +"You did "+wordsOfEncouragement;
    }
    
}
