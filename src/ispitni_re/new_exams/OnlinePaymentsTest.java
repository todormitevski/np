package ispitni_re.new_exams;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

class Payment{
    String name;
    int price;

    public Payment(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }
}

class OnlinePayments{
    Map<String,List<Payment>> studentMap;

    public OnlinePayments() {
        studentMap = new HashMap<>();
    }

    public void readItems(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        br.lines()
                .forEach(line ->{
                    String[] parts = line.split(";");
                    List<Payment> payments = new ArrayList<>();
                    payments.add(new Payment(parts[1],Integer.parseInt(parts[2])));
                    studentMap.computeIfPresent(parts[0], (k,v) ->{
                        v.add(new Payment(parts[1],Integer.parseInt(parts[2])));
                        return v;
                    });
                    studentMap.putIfAbsent(parts[0],payments);
                });
    }

    public void printStudentReport(String index, OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        StringBuilder sb = new StringBuilder();
        if(!studentMap.containsKey(index)){
            pw.printf("Student %s not found!\n",index);
        } else{
            AtomicInteger i = new AtomicInteger(1);
            int total = studentMap.get(index).stream()
                    .mapToInt(Payment::getPrice).sum();
            long provision = Math.round(total * 1.14 / 100);
            if(provision < 3)
                provision = 3;
            if(provision > 300)
                provision = 300;
            sb.append("Student: ").append(index).append(" Net: ")
                    .append(total).append(" Fee: ")
                    .append(provision).append(" Total: ")
                    .append(total + provision)
                    .append("\nItems:\n");
            List<Payment> sortingList = new ArrayList<>(studentMap.get(index));
            sortingList.stream()
                    .sorted(Comparator.comparing(Payment::getPrice)
                            .reversed())
                    .forEach(payment ->
                            sb.append(String.format("%d. %s %d\n",
                            i.getAndIncrement(),
                            payment.getName(),
                            payment.getPrice())));
            pw.println(sb.substring(0,sb.length()-1));
            //System.out.println(sb.substring(0,sb.length()-1));
        }
        pw.flush();
    }
}

public class OnlinePaymentsTest {
    public static void main(String[] args) {
        OnlinePayments onlinePayments = new OnlinePayments();

        onlinePayments.readItems(System.in);

        IntStream.range(151020, 151025).mapToObj(String::valueOf).forEach(id -> onlinePayments.printStudentReport(id, System.out));
    }
}