import java.util.ArrayList;
import java.util.Comparator;
public class ProcessManagement {

    private static ArrayList<Process> processArray = new ArrayList<>();
    private static ArrayList<Switch> Switcharray = new ArrayList<>();

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

    //------------Get Switch Array-------------------
    public static ArrayList<Switch> getAllSwitch()
    {
        return Switcharray;
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
        ArrayList<Process> readyProcesses = new ArrayList<>(); // array of arrived processes

        int servedProcesses = 0;
        double currentSecond = 0;
        Process lastProcess = null;

        // loop untill serving all the processes
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

            // check if there any processes arrived 
            if(!readyProcesses.isEmpty())
            {
                // Comapre arrived processes and select the process that has shortest burst time 
                readyProcesses.sort(Comparator.comparing(Process::getBurstTime));
                Process shortestProcess = readyProcesses.get(0); // intialize shortest process


                // for the switch array -> intialize last process with shortest process at time = 0
                if(currentSecond == 0)
                    lastProcess = shortestProcess; 

                // loop to set the start time "in the original process array" of each process if it is not set before 
                for(Process p : processArray)
                {
                    if(p.getID() == shortestProcess.getID())
                    {
                        if(p.getStartTime() == -1)
                        {
                            p.setStartTime(currentSecond);
                            p.setStartClock(currentSecond);

                        }
                    }
                }

                // minus 1 second from the burst time of shortest process " process being served "
                shortestProcess.setBurstTime(shortestProcess.getBurstTime() - 1);
                
                // SWITCH ARRAY -> check if there is gap between two processes and the last process is null
                if(lastProcess == null && shortestProcess !=null)
                {
                    Switcharray.add(new Switch(currentSecond, null, shortestProcess));
                    lastProcess = shortestProcess;
                    shortestProcess.setStartClock(currentSecond);
                }
                // else if there is no gap between the two processes and there is switch happens between them 
                else if(lastProcess != null && shortestProcess != null && lastProcess.getID() != shortestProcess.getID() )
                {
                    Switcharray.add(new Switch(currentSecond, lastProcess, shortestProcess));
                    lastProcess.setEndClock(shortestProcess.getStartClock());
                    lastProcess = shortestProcess;
                    shortestProcess.setStartClock(currentSecond);

                }

                // if the process served completely -> burst time = 0
                if(shortestProcess.getBurstTime() <= 0)
                {
                    for(Process p : processArray)
                    {
                        if(p.getID() == shortestProcess.getID())
                        {
                            p.setCompletionTime(currentSecond + 1); // set when the process is totally completed
                            p.setEndClock(p.completion_time);
                        }
                    }

                    readyProcesses.remove(shortestProcess);
                    servedProcesses++;
                }
            }
            else
            {
                // if there is no ready procesess ad last process is not null 
                if(lastProcess != null)
                {
                    //lastProcess.setEndClock(lastProcess.getOriginalBurstTime() + lastProcess.getArrivalTime() ); // Set endClock for the last process
                    lastProcess = null;
                    Switcharray.add(new Switch(currentSecond, lastProcess, null));


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




