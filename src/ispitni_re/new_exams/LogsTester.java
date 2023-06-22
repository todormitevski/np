package ispitni_re.new_exams;

import java.util.*;

enum SeriousnessType{
    INFO, //0
    WARN, //1
    ERROR //3
}

class Log{
    SeriousnessType type;
    String message;
    int timestamp;

    public Log(SeriousnessType type, String message, int timestamp) {
        this.type = type;
        this.message = message;
        this.timestamp = timestamp;
    }
}

class MicroService{
    String name;
    List<Log> logs;

    public MicroService(String name) {
        this.name = name;
        logs = new ArrayList<>();
    }

    public void addLog(SeriousnessType type, String message, int timestamp){
        logs.add(new Log(type,message,timestamp));
    }

    public int getNumLogs(){
        return logs.size();
    }
}

class Service{
    String name;
    Map<String,MicroService> microServiceMap;
    int severity;

    public Service(String name) {
        this.name = name;
        microServiceMap = new HashMap<>();
        severity = 0;
    }

    public void addMicroService(String name, SeriousnessType type, String message, int timestamp){
        microServiceMap.computeIfPresent(name, (k,v) -> {
            v.addLog(type,message,timestamp);
            return v;
        });
        microServiceMap.computeIfAbsent(name, microService -> {
            MicroService newMicroService = new MicroService(name);
            newMicroService.addLog(type,message,timestamp);
            return newMicroService;
        });
    }

    public int getSeverity() {
        return severity;
    }

    public int getNumLogs(){
        return microServiceMap.values().stream()
                .mapToInt(MicroService::getNumLogs)
                .sum();
    }

    public double getAvgSeverityForAllLogs(){
        int severity = 0;
        for(MicroService ms : microServiceMap.values()){
            for(Log log : ms.logs){
                severity += log.type.ordinal();
            }
        }
        return (double) severity / getNumLogs();
    }

    @Override
    public String toString() {
        return String.format(
                "Service name: %s " +
                        "Count of microservices: %d " +
                        "Total logs in service: %d " +
                        "Average severity for all logs: %.2f " +
                        "Average number of logs per microservice: %.2f",
                name,
                microServiceMap.keySet().size(),
                getNumLogs(),
                getAvgSeverityForAllLogs(),
                0.00
        );
    }
}

class LogCollector{
    Map<String,Service> serviceMap;

    public LogCollector() {
        serviceMap = new HashMap<>();
    }

    public void addLog(String log) {
        String[] parts = log.split("\\s+");
        String serviceName = parts[0];
        String microServiceName = parts[1];
        SeriousnessType type = SeriousnessType.valueOf(parts[2]);
        StringJoiner joinedMessage = new StringJoiner(" ");
        for(int i=3;i<parts.length-1;i++){
            joinedMessage.add(parts[i]);
        }
        int timestamp = Integer.parseInt(parts[parts.length-1]);
        serviceMap.computeIfPresent(serviceName, (k,v) -> {
            v.addMicroService(microServiceName,type,joinedMessage.toString(),timestamp);
            return v;
        });
        serviceMap.computeIfAbsent(serviceName, service -> {
            Service newService = new Service(serviceName);
            newService.addMicroService(microServiceName,type,joinedMessage.toString(),timestamp);
            return newService;
        });
    }

    public void printServicesBySeverity() {
        for(Service service : serviceMap.values()){
            int severity = 0;
            for(MicroService microService : service.microServiceMap.values()){
                for(Log log : microService.logs){
                    severity += log.type.ordinal();
                }
            }
            service.severity += severity;
        }
        serviceMap.values().stream()
                .sorted(Comparator.comparing(Service::getSeverity)
                        .reversed())
                .forEach(System.out::println);
    }

    public Map<Integer, Integer> getSeverityDistribution(String service, String microservice) {
        return null;
    }

    public void displayLogs(String service, String microservice, String order) {

    }
}

public class LogsTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LogCollector collector = new LogCollector();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.startsWith("addLog")) {
                collector.addLog(line.replace("addLog ", ""));
            } else if (line.startsWith("printServicesBySeverity")) {
                collector.printServicesBySeverity();
            } else if (line.startsWith("getSeverityDistribution")) {
                String[] parts = line.split("\\s+");
                String service = parts[1];
                String microservice = null;
                if (parts.length == 3) {
                    microservice = parts[2];
                }
                collector.getSeverityDistribution(service, microservice).forEach((k,v)-> System.out.printf("%d -> %d%n", k,v));
            } else if (line.startsWith("displayLogs")){
                String[] parts = line.split("\\s+");
                String service = parts[1];
                String microservice = null;
                String order = null;
                if (parts.length == 4) {
                    microservice = parts[2];
                    order = parts[3];
                } else {
                    order = parts[2];
                }
                collector.displayLogs(service, microservice, order);
            }
        }
    }
}
