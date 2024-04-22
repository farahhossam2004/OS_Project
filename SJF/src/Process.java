public class Process {
    
    private final int id;
    private static int ProcessCounter = 0;
    private boolean isRunning = false;
    double arrival_time;
    double burst_time;
    double waiting_time;
    double turnaround_time;
    double response_time;
    
    Process(double arrival_time, double burst_time)
    {
        this.arrival_time = arrival_time;
        this.burst_time = burst_time;
        this.id = ++ProcessCounter;
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

    public void setTurnaroundTime(double turnaroundtime)
    {
        this.turnaround_time = turnaroundtime;
    }

    public void setResponseTime(double responsetime)
    {
        this.response_time = responsetime;
    }    

    public void setIsRunning(boolean isRunning)
    {
        this.isRunning = isRunning;
    }
    
    public void setArrivalTime(double arrivaltime){
        this.arrival_time = arrivaltime;
    }

    public void setBurstTime(double bursttime){
        this.burst_time=bursttime;
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

    public boolean getIsRunning()
    {
        return this.isRunning;
    }

    public double getBurstTime(){
        return this.burst_time;
    }

    public double getArrivalTime(){
        return this.arrival_time;
    }
}
