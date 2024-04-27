public class Switch {
    
    private final int id;
    private static int SwingCounter = 0;
    int swingtime;
    Process previousProcess;

    
    Switch(int time , Process previouProcess){
        this.swingtime=time;
        this.previousProcess=previouProcess;
        this.id= ++SwingCounter;
    }

    //getters
    public int getSwingId(){
        return this.id;
    }
    public int getswingtime(){
        return this.swingtime;
    }
    public Process getswingProcess(){
        return this.previousProcess;
    }

}
