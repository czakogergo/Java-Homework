package com.example.java_homework.gamemodel;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;

import java.util.ArrayList;
/**
 * The GameModel class represents the model of the Mancala game.
 * It includes methods and properties to manage the game state, players, pits, and marbles.
 */
public class GameModel {

    /**
     * Enum representing the possible states of the game.
     */
    public static enum STATE {
        START, INCOMPLETE, COMPLETE
    };

    /**
     * Enum representing players in the game.
     */
    public static enum Player {
        ONE, TWO
    }

    private Player currentPlayer;
    private STATE gameState;

    public static final int totalPits = 14;
    private int[] pits;

    public static final int mancalaP1 = 6;
    public static final int mancalaP2 = 13;

    private boolean lastMarbleInMancala;

    /**
     * Constructs a new GameModel object, initializing the game state, current player, pits, and other properties.
     */
    public GameModel() {
        gameState = STATE.START;
        currentPlayer = Player.ONE;
        pits = new int[totalPits];
        lastMarbleInMancala = false;
    }

    /**
     * Gets the array representing the state of all pits.
     *
     * @return An array of integers representing the state of all pits.
     */
    public int[] getAllPits() { return pits; }

    /**
     * Gets the current state of the game.
     *
     * @return The current state of the game.
     */
    public STATE getGameState() {
        return gameState;
    }

    /**
     * Sets the state of the game.
     *
     * @param state The new state of the game.
     */
    public void setGameState(STATE state) {
        this.gameState = state;
    }

    /**
     * Gets the current player in the game.
     *
     * @return The current player.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Gets the winner of the game based on the current state of pits.
     *
     * @return A string indicating the winner or "Equal" if it's a tie.
     */
    public String getWinnerPlayer() {
        if(pits[mancalaP2] > pits[mancalaP1]) return "The Winner is TWO";
        if(pits[mancalaP2] < pits[mancalaP1]) return "The Winner is ONE";
        return "Equal";
    }

    /**
     * Gets the player of the current pit.
     *
     * @return The player of the current pit.
     */
    private Player playerOfPit(int pit) {

        if (pit >= 0 && pit <= mancalaP1) {
            return Player.ONE;
        }
        return Player.TWO;
    }

    /**
     * Checks if the specified pit index corresponds to a mancala pit.
     *
     * @param pit The index of the pit to check.
     * @return True if the pit is a mancala pit, false otherwise.
     */
    private boolean isMancala(int pit) {
        return (pit == mancalaP1 || pit == mancalaP2);
    }

    /**
     * Calculates the index of the pit opposite to the given pit index.
     *
     * @param pit The index of the pit.
     * @return The index of the pit opposite to the given pit.
     */
    private int oppositePit(int pit) {
        if(pit <=12) {
            return 12 - pit;
        } else{
            return pit - 12;
        }
    }

    /**
     * Sets the initial number of marbles in all pits, except mancala pits.
     *
     * @param num The number of marbles to set in each non-mancala pit.
     * @return An array representing the state of all pits after setting the marbles.
     */
    public int[] setAllPits(int num) {
        for(int i = 0; i < 14; i++) {
            pits[i] = num;
        }
        pits[mancalaP1] = 0;
        pits[mancalaP2] = 0;
        return pits;
    }

    /**
     * Checks if a move is valid for the specified pit.
     *
     * @param pit The index of the pit being considered for a move.
     * @return True if the move is valid, false otherwise.
     */
    public boolean canPlay(int pit) {

        if (gameState != STATE.INCOMPLETE) {
            return false;
        }

        if (pits[pit] == 0){
            return false;
        }
        if (playerOfPit(pit) != currentPlayer){
            return false;
        }

        if (pit < 0 || pit > totalPits || pit == mancalaP1 || pit == mancalaP2)
            return false;

        return true;
    }

    /**
     * Changes the current player.
     */
    public void changePlayer() {

        if (currentPlayer == Player.ONE) {
            currentPlayer = Player.TWO;
        }
        else {
            currentPlayer = Player.ONE;
        }
    }

    /**
     * Moves the last marbles to the corresponding Mancala pit.
     */
    private void moveLastMarblesToMancala() {
        for (int i = 0; i < totalPits; ++i)

            if (!isMancala(i)) {
                if (playerOfPit(i) == Player.ONE) {

                    pits[mancalaP1] += pits[i];
                    pits[i] = 0;
                }
                else {

                    pits[mancalaP2] += pits[i];
                    pits[i] = 0;
                }
            }
    }

    /**
     * Checks and updates properties related to the last marble in a Mancala pit.
     *
     * @param currentPit The index of the current pit.
     */
    public void checkLastMarbleProperties(int currentPit) {
        if (playerOfPit(currentPit) == currentPlayer && isMancala(currentPit))
        {
            lastMarbleInMancala = true;
        }
        else if (playerOfPit(currentPit) == currentPlayer && pits[currentPit] == 1 && pits[oppositePit(currentPit)] > 0)
        {
            int stolenMarbles = pits[currentPit] + pits[oppositePit(currentPit)];
            pits[currentPit] = pits[oppositePit(currentPit)] = 0;
            if (currentPlayer == Player.ONE)
            {
                pits[mancalaP1] += stolenMarbles;
            }
            else {
                pits[mancalaP2] += stolenMarbles;
            }
            lastMarbleInMancala = false;
            changePlayer();
        }
        else {
            lastMarbleInMancala = false;
            changePlayer();
        }
    }

    /**
     * Checks if all non-Mancala pits are empty for either player.
     *
     * @return True if all non-Mancala pits are empty for either player, false otherwise.
     */
    private boolean arePitsEmpty() {
        int player1Pits = 0;
        int player2Pits = 0;
        for (int i = 0; i < totalPits; ++i)

            if (!isMancala(i)) {
                if (playerOfPit(i) == Player.ONE)
                {
                    player1Pits += pits[i];
                }
                else
                {
                    player2Pits += pits[i];
                }
            }

        return (player1Pits == 0 || player2Pits == 0);
    }

    /**
     * Moves marbles in the game based on the selected pit index.
     *
     * @param pitIndex The index of the selected pit for the move.
     */
    public void move(int pitIndex) {

        int marbles = pits[pitIndex];
        pits[pitIndex] = 0;

        int currentPit = pitIndex;
        while (marbles > 0)
        {
            currentPit++;
            if (currentPit >= totalPits) currentPit = 0;

            if (isMancala(currentPit) && playerOfPit(currentPit) != currentPlayer) {
                continue;
            }

            pits[currentPit]= pits[currentPit] + 1;
            marbles--;
        }

        checkLastMarbleProperties(currentPit);

        if (arePitsEmpty())
        {
            moveLastMarblesToMancala();
            gameState = STATE.COMPLETE;
        }
    }
}