public class Switch {
    
    private final int id;
    private static int SwitchCounter = 0;
    double switchtime;
    Process previousProcess, nextProcess;

    // Contructor
    Switch(double time , Process previouProcess, Process nextProcess ){
        this.switchtime = time;
        this.previousProcess = previouProcess;
        this.nextProcess = nextProcess;
        this.id = ++SwitchCounter;
    }

    //getters
    public int getSwingId(){
        return this.id;
    }
    public double getswingtime(){
        return this.switchtime;
    }
    public Process getPrevProcess(){
        return this.previousProcess;
    }

    public Process getNextProcess(){
        return this.nextProcess;
    }

    // Setters 
    public static void setSwitchCounter(int n)
    {
        SwitchCounter = n;
    }

}
