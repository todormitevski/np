package ispitni_re.newExams;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Risk{
    List<Integer> attacks;
    List<Integer> defenses;
    int attackersAlive;
    int defendersAlive;

    public Risk() {
        attacks = new ArrayList<>();
        defenses = new ArrayList<>();
        attackersAlive = 0;
        defendersAlive = 0;
    }

    public void processAttacksData(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        br.lines()
                .forEach(line ->{
                    attacks.clear();
                    defenses.clear();
                    attackersAlive = 0;
                    defendersAlive = 0;
                    String[] parts = line.split(";");
                    for(String part : parts[0].split("\\s+"))
                        attacks.add(Integer.parseInt(part));
                    for(String part : parts[1].split("\\s+"))
                        defenses.add(Integer.parseInt(part));
                    Collections.sort(attacks);
                    Collections.sort(defenses);
                    for(int i=0;i<attacks.size();i++){
                        if(attacks.get(i) > defenses.get(i))
                            attackersAlive++;
                        else defendersAlive++;
                    }
                    System.out.printf("%d %d\n",attackersAlive,defendersAlive);
                });
    }
}

public class RiskTester {
    public static void main(String[] args) {
        Risk risk = new Risk();
        risk.processAttacksData(System.in);
    }
}
