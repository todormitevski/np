package ispitni_re.old_exams;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//14

class Song{
    int id;
    static int idSeed = 0;
    String title;
    String artist;
    boolean isPaused;

    public Song(String title, String artist) {
        this.id = idSeed++;
        this.title = title;
        this.artist = artist;
        this.isPaused = false;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public boolean isPaused() {
        return isPaused;
    }

    @Override
    public String toString() {
        return "Song{" +
                "title=" + title +
                ", artist=" + artist +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Song song = (Song) o;
        return id == song.id && isPaused == song.isPaused && Objects.equals(title, song.title) && Objects.equals(artist, song.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, artist, isPaused);
    }
}

class MP3Player{
    List<Song> songList;
    Song currentSong;
    int index;

    public MP3Player(List<Song> songs) {
        this.songList = songs;
        currentSong = null;
        this.index = 0;
    }

    public void changeIndex(int dir){ //minus for left, plus for right, cycles
        index += dir;
        if(index < 0)
            index = songList.size()-1;
        if(index > songList.size()-1)
            index = 0;
    }

    public void printCurrentSong(){
        System.out.println(currentSong);
    }

    public void pressPlay(){
        if(currentSong != null && currentSong.equals(songList.get(index)) && !currentSong.isPaused)
            System.out.println("Song is already playing");
        else {
            currentSong = songList.get(index);
            System.out.printf("Song %d is playing\n", index);
        }
    }

    public void pressStop(){
        if(songList.get(index).isPaused()){
            index = 0;
            System.out.println("Songs are stopped");
        } else{
            songList.get(index).isPaused = true;
            System.out.printf("Song %d is paused\n", index);
        }
    }

    public void pressFWD(){
        currentSong.isPaused = true;
        changeIndex(1);
        currentSong = songList.get(index);
        System.out.println("Forward...");
    }

    public void pressREW(){
        currentSong.isPaused = true;
        changeIndex(-1);
        currentSong = songList.get(index);
        System.out.println("Reward...");
    }

    @Override
    public String toString() {
        return "MP3Player{" +
                "currentSong = " + Objects.requireNonNullElse(currentSong, 0) +
                ", songList = " + songList +
                '}';
    }
}

public class PatternTest {
    public static void main(String args[]) {
        List<Song> listSongs = new ArrayList<Song>();
        listSongs.add(new Song("first-title", "first-artist"));
        listSongs.add(new Song("second-title", "second-artist"));
        listSongs.add(new Song("third-title", "third-artist"));
        listSongs.add(new Song("fourth-title", "fourth-artist"));
        listSongs.add(new Song("fifth-title", "fifth-artist"));
        MP3Player player = new MP3Player(listSongs);


        System.out.println(player.toString());
        System.out.println("First test");


        player.pressPlay();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Second test");


        player.pressStop();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Third test");


        player.pressFWD();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
    }
}

//Vasiot kod ovde