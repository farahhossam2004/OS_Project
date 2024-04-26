import java.util.ArrayList;

public class ProcessManagement {

    private static ArrayList<Process> processArray = new ArrayList<>();


    // -----------Delete Process-----------------
    public static int deleteProcess(int id)
    {
        for(int i = 0; i < processArray.size(); i++)
        {
            if(processArray.get(i).getID() == id)
            {
                processArray.remove(i);
                return 1; // Process deleted successfully
            }
        }
        return -1;   // Process not found
    }

    //-------------Get all the processes-------------
    public static ArrayList<Process> getAllProcesses()
    {
        return processArray;
    }

    //---------------Add Process---------------------

    public static void addProcess(double burst, double arrival)
    {
        ProcessManagement.getAllProcesses().add(new Process(arrival, burst));
    }

    //----------------Serve all the processes--------------------------

    public static void serve()
    {
        ArrayList<Process> processes = getAllProcesses();
        ArrayList<Process> readyProcesses = new ArrayList<>();

        int servedProcesses = 0;
        double currentSecond = 0;

        while(servedProcesses < processes.size())
        {   
            // Add all the processes that have arrived in readyProcesses Array
            for(Process process : processes)
            {
                if(process.getArrivalTime() <= currentSecond && !readyProcesses.contains(process) && process.getBurstTime() > 0)
                {
                    readyProcesses.add(process);
                }
            }

            if(!readyProcesses.isEmpty())
            {
                Process shortestProcess = readyProcesses.get(0);
                for(Process process : readyProcesses)
                {
                    if(process.getBurstTime() < shortestProcess.getBurstTime())
                    {
                        shortestProcess = process;
                    }
                }

                for(Process p : processArray)
                {
                    if(p.getID() == shortestProcess.getID())
                    {
                        if(p.getStartTime() == -1)
                        {
                            p.setStartTime(currentSecond);
                        }
                    }
                }
                shortestProcess.setBurstTime(shortestProcess.getBurstTime() - 1);

                if(shortestProcess.getBurstTime() <= 0)
                {
                    for(Process p : processArray)
                    {
                        if(p.getID() == shortestProcess.getID())
                        {
                            p.setCompletionTime(currentSecond + 1);
                        }
                    }
                    readyProcesses.remove(shortestProcess);
                    servedProcesses++;
                }
            }
            currentSecond++;
        }
    }

//================================================================
// fn for Calculation scenes
    // Waiting time function
    public static double waitingTime(int id)
    {
        for(Process p : processArray)
        {
            if(p.getID() == id)
                return (turnaroundtime(id) - p.getOriginalBurstTime());
        }
        return -1; // process not found
    }


    // Turnaround time function
    public static double turnaroundtime(int id)
    {
        for(Process p : processArray){
            if(p.getID() == id)
            {
                return p.getCompletionTime() - p.getArrivalTime();
            }
        }
        return -1; // process not found
    }


    // Response Time function
    public static double responseTime(int id)
    {
        for(Process p : processArray)
        {
            if(p.getID() == id)
            {
                return p.getStartTime() - p.getArrivalTime();
            }
        }
        return -1;
    }

//=================================================================
        //Rania Task 

        // Average waiting Time Function

        public static double calculateAverageWaiting(){
            ArrayList<Process> processes = getAllProcesses();
            double totalWaitingTime=0;

            for(Process process : processes){
                totalWaitingTime += process.getWaitingTime();
            }

            return totalWaitingTime / processes.size();
        }

        // Average Turnaround Time Function

        public static double calculateAverageTurnaroundTime(){
            ArrayList<Process> processes = getAllProcesses();
            double totalTurnaroundTime=0;
            for(Process process : processes){
                totalTurnaroundTime += process.getTurnaroundTime();
            }
            return  totalTurnaroundTime / processes.size();
        }

        // Average Response Time Function

        public static double calculateAverageResponseTime(){
            ArrayList<Process> processes = getAllProcesses();
            double totalResponseTime=0;
            for(Process process : processes){
                totalResponseTime += process.getResponseTime();
            }
            return  totalResponseTime / processes.size();
        }


//==========================================================

        public static void Calculation(){
            ArrayList<Process>  ProcessList = getAllProcesses();

            for(Process  p : ProcessList) {
                p.setResponseTime(responseTime(p.getID()));
                p.setTurnaroundTime(turnaroundtime(p.getID()));
                p.setWaitingTime(waitingTime(p.getID()));
            }
        }

//================================================================

}



