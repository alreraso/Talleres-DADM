package co.edu.unal.androidtic_tac_toe;

public class Game {
    public String playerroom;
    public String playercome = "";
    public String state = "new";
    public String winner = "";
    public String board = "";

    public Game(String playerroom) {
        this.playerroom = playerroom;
    }
}
