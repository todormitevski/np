package ispitni_re.new_exams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Risk1{
    List<Integer> attacks;
    List<Integer> defenses;
    int numSuccessfulAttacks;
    boolean flag;

    public Risk1() {
        attacks = new ArrayList<>();
        defenses = new ArrayList<>();
        numSuccessfulAttacks = 0;
        flag = true;
    }


    public int processAttacksData(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        numSuccessfulAttacks = 0;
        br.lines()
                .forEach(line ->{
                    attacks.clear();
                    defenses.clear();
                    flag = true;
                    String[] parts = line.split(";");
                    for(String part : parts[0].split("\\s+"))
                        attacks.add(Integer.parseInt(part));
                    for(String part : parts[1].split("\\s+"))
                        defenses.add(Integer.parseInt(part));
                    Collections.sort(attacks);
                    Collections.sort(defenses);
                    for(int i=0;i<attacks.size();i++){
                        if (attacks.get(i) <= defenses.get(i)) {
                            flag = false;
                            break;
                        }
                    }
                    if(flag)
                        numSuccessfulAttacks++;
                });
        return numSuccessfulAttacks;
    }
}

public class RiskTester1 {
    public static void main(String[] args) {

        Risk1 risk = new Risk1();

        System.out.println(risk.processAttacksData(System.in));

    }
}