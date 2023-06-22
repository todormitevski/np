package ispitni_re.old_exams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

class Team{
    String name;
    int numMatches = 0;
    int numWins = 0;
    int numTies = 0;
    int numLosses = 0;
    int points = 0;
    int i = 0;
    int givenGoals = 0;
    int receivedGoals = 0;

    public Team(String name) {
        this.name = name;
    }

    public void increaseNumWins() {
        this.numWins += 1;
        setPoints();
    }

    public void increaseNumTies() {
        this.numTies += 1;
        setPoints();
    }

    public void increaseNumLosses(){
        this.numLosses += 1;
        setPoints();
    }

    public void setPoints() {
        this.points = numWins * 3 + numTies;
        this.numMatches += 1;
    }

    public int getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    public void incrementI(int increment){
        i += increment;
    }

    public void setGivenGoals(int givenGoals) {
        this.givenGoals += givenGoals;
    }

    public void setReceivedGoals(int receivedGoals) {
        this.receivedGoals += receivedGoals;
    }

    public int calcGoalDifference(){
        return givenGoals - receivedGoals;
    }

    @Override
    public String toString() {
        return String.format("%2d. %-15s%5d%5d%5d%5d%5d",
                i,name,numMatches,numWins,numTies,numLosses,getPoints());
    }
}

class FootballTable{
    Map<String,Team> teamsMapByPts;

    public FootballTable() {
        teamsMapByPts = new TreeMap<String,Team>();
    }

    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        teamFactory(homeTeam, homeGoals, awayGoals);
        teamFactory(awayTeam, awayGoals, homeGoals);
    }

    private void teamFactory(String team, int givenGoals, int receivedGoals) {
        teamsMapByPts.putIfAbsent(team, new Team(team));
        teamsMapByPts.computeIfPresent(team, (k,v) ->{
            if(givenGoals > receivedGoals)
                v.increaseNumWins();
            else if(receivedGoals > givenGoals)
                v.increaseNumLosses();
            else v.increaseNumTies();
            v.setGivenGoals(givenGoals);
            v.setReceivedGoals(receivedGoals);
            return v;
        });
    }

    public void printTable() {
        PrintWriter pw = new PrintWriter(System.out);
        AtomicInteger incrementer = new AtomicInteger(1);
        teamsMapByPts.values().stream()
                .sorted(Comparator.comparing(Team::getPoints)
                        .thenComparing(Team::calcGoalDifference).reversed()
                        .thenComparing(Team::getName))
                .forEach(value ->{
            value.incrementI(incrementer.get());
            incrementer.getAndIncrement();
            pw.println(value);
        });
        pw.close();
    }
}

/**
 * Partial exam II 2016/2017
 */
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
