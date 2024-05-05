public class Process {
    
    private final int id;
    private static int ProcessCounter = 0;
    double arrival_time;
    double burst_time;
    double waiting_time;
    double turnaround_time;
    double response_time;
    double completion_time;
    double start_time = -1;
    double originalBurstTime;
    double startClock = -1 ;
    double endClock = -1;
    
    Process(double arrival_time, double burst_time)
    {
        this.arrival_time = arrival_time;
        this.burst_time = burst_time;
        this.id = ++ProcessCounter;
        this.originalBurstTime = burst_time;
    }

    // overload constructor 
    Process(int id , double start , double end){
        this.id = id;
        this.startClock=start;
        this.endClock=end;
    }

    // Setters
    public static void setProcessCounter(int cnt)
    {
        ProcessCounter = cnt;
    }

    public void setWaitingTime(double waitingtime)
    {
        this.waiting_time = waitingtime;
    }
    public void setStartClock (double startClock){
        this.startClock = startClock ;
    }
    public void setEndClock (double endClock){
        this.endClock = endClock ;
    }
    

    public void setTurnaroundTime(double turnaroundtime)
    {
        this.turnaround_time = turnaroundtime;
    }

    public void setResponseTime(double responsetime)
    {
        this.response_time = responsetime;
    }    

    public void setArrivalTime(double arrivaltime){
        this.arrival_time = arrivaltime;
    }

    public void setBurstTime(double bursttime){
        this.burst_time=bursttime;
    }

    public void setCompletionTime(double completiontime){
        this.completion_time = completiontime;
    }

    public void setStartTime(double starttime){
        this.start_time = starttime;
    }

    // Getters
    public int getID()
    {
        return this.id;
    }

    public double getWaitingTime()
    {
        return this.waiting_time;
    }

    public double getTurnaroundTime()
    {
        return this.turnaround_time;
    }

    public double getResponseTime()
    {
        return this.response_time;
    }

    public double getBurstTime(){
        return this.burst_time;
    }

    public double getArrivalTime(){
        return this.arrival_time;
    }

    public double getCompletionTime()
    {
        return this.completion_time;
    }

    public double getStartTime()
    {
        return this.start_time;
    }

    public double getOriginalBurstTime()
    {
        return this.originalBurstTime;
    }

    public int getProcessCounter()
    { 
        return this.getProcessCounter();
    }

    public double getStartClock (){
        return this.startClock ;
    }
    
    public double getEndClock (){
        return this.endClock ;
    }

}
