package kolokviumski.midterm1;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class AtkDef{
    List<Integer> attacker;
    List<Integer> defender;

    public AtkDef(List<Integer> attacker, List<Integer> defender) {
        this.attacker = attacker;
        this.defender = defender;
    }

    public boolean successfulAttack(){
        attacker.sort(Comparator.naturalOrder());
        defender.sort(Comparator.naturalOrder());
        int counter = 0;
        for(int i=0;i<3;i++){
            if(attacker.get(i) > defender.get(i)){
                counter++;
            }
        }
        return counter == 3;
    }
}

/*
input:
5 3 4; 2 4 1
1 1 1; 3 1 1
6 7 4; 6 7 4
9 4 9; 1 5 1 <- not working
2 3 4; 1 2 3
output:
2
 */

class Risk{
    List<AtkDef> atkDefList;

    public Risk() {
        atkDefList = new ArrayList<AtkDef>();
    }

    public int processAttacksData(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        br.lines()
                .forEach(line ->{
                    String[] parts = line.split("; ");
                    String attackers = parts[0];
                    String defenders = parts[1];
                    String[] attackerParts = attackers.split("\\s+");
                    String[] defenderParts = defenders.split("\\s+");
                    List<Integer> attackerList = new ArrayList<Integer>();
                    List<Integer> defenderList = new ArrayList<Integer>();
                    for(String attacker : attackerParts){
                        attackerList.add(Integer.parseInt(attacker));
                    }
                    for(String defender : defenderParts){
                        defenderList.add(Integer.parseInt(defender));
                    }
                    AtkDef atkDef = new AtkDef(attackerList,defenderList);
                    atkDefList.add(atkDef);
                });
        return (int) atkDefList.stream()
                .filter(AtkDef::successfulAttack)
                .count();
    }
}

public class RiskTester {
    public static void main(String[] args) {

        Risk risk = new Risk();

        System.out.println(risk.processAttacksData(System.in));

    }
}