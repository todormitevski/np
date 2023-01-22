package kolokviumski;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Partial exam II 2016/2017
 */

class Team{
    String name;
    int wins;
    int ties;
    int losses;
    int goalsGiven;
    int goalsTaken;

    public Team(String name) {
        this.name = name;
        wins = 0;
        ties = 0;
        losses = 0;
        goalsGiven = 0;
        goalsTaken = 0;
    }

    public void updateInfo(int goalsGiven,int goalsTaken){
        if(goalsGiven > goalsTaken) wins++;
        else if(goalsGiven < goalsTaken) losses++;
        else ties++;
        this.goalsGiven += goalsGiven;
        this.goalsTaken += goalsTaken;
    }

    int getPoints(){
        return wins * 3 + ties;
    }

    int getTotalGames(){
        return wins + losses + ties;
    }

    String getName(){
        return name;
    }

    int goalDiff(){
        return goalsGiven - goalsTaken;
    }

    @Override
    public String toString() {
        return String.format("%-15s%5d%5d%5d%5d%5d",
                name,getTotalGames(),wins,ties,losses,getPoints()); //za levo mesto 15 so minus
    }
}

class FootballTable{
    Map<String,Team> teamsByName;

    public FootballTable() {
        teamsByName = new HashMap<>();
    }

    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals){
        Team home = teamsByName.computeIfAbsent(homeTeam, key -> new Team(homeTeam));
        Team away = teamsByName.computeIfAbsent(awayTeam, key -> new Team(awayTeam));

        home.updateInfo(homeGoals,awayGoals);
        away.updateInfo(awayGoals,homeGoals);
    }

    public void printTable() {
        List<Team> result = teamsByName.values().stream()
                .sorted(Comparator.comparing(Team::getPoints)
                        .thenComparing(Team::goalDiff).reversed()
                        .thenComparing(Team::getName)).collect(Collectors.toList());

        IntStream.range(0,result.size()) //list starts with 0
                .forEach(i -> System.out.printf("%2d. %s\n",i+1,result.get(i)));
    }
}

public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}

// Your code here

