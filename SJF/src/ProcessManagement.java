import java.util.ArrayList;

public class ProcessManagement {
    
    private static ArrayList<Process> processArray = new ArrayList<>();
    private static ArrayList<Process> ArrivedProcess = new ArrayList<>(); 


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


    //Serve
    public static void serve(){
        System.out.println("serving");
        ArrayList<Process> processes = getAllProcesses();
        
        double ArrivingtimeCounter;
        double LoopCondition=0;
        // to get the highest arrival time 
        for(int i = 0 ; i < processes.size(); i++){
            
            if(processes.get(i).getArrivalTime() >= LoopCondition)
                LoopCondition=processes.get(i).getArrivalTime();
        }

        for(ArrivingtimeCounter = 0 ; ArrivingtimeCounter<=LoopCondition ; ArrivingtimeCounter++ ){
            for(Process process : processes){
                if(process.getArrivalTime() <= ArrivingtimeCounter){
                    ArrivedProcess.add(process);
                    ArrivedProcess.remove(process);
                }
            }
            
        double MinBurstTime=0;
            for(Process arrivedprocess : ArrivedProcess){
                if(arrivedprocess.getBurstTime()<=MinBurstTime)
                    MinBurstTime=arrivedprocess.getBurstTime();
            }
            for(Process arrivedprocess : ArrivedProcess){
                if(arrivedprocess.getBurstTime()==MinBurstTime);
                    arrivedprocess.setBurstTime(arrivedprocess.getBurstTime()-1);
                    System.out.println("Process served " + arrivedprocess.getID() + "\n Remaining time: " + arrivedprocess.getBurstTime());
            }

    }
}
    // Waiting time function


    // Turnaround time function


    // Response Time function

//=================================================================
    //Rania Task 

    // Average waiting Time Function


    // Average Turnaround Time Function


    // Average Response Time Function
//================================================================



}
