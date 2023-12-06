package com.example.java_homework;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import com.example.java_homework.gamemodel.GameModel;

/**
 * The HelloController class is the controller for the Mancala game's UI.
 * It handles user interactions and updates the UI based on the game model.
 */
public class HelloController {
    @FXML
    private Label playerTurnLabel;

    @FXML
    private Button pitButton0;
    @FXML
    private Button pitButton1;
    @FXML
    private Button pitButton2;
    @FXML
    private Button pitButton3;
    @FXML
    private Button pitButton4;
    @FXML
    private Button pitButton5;
    @FXML
    private Button pitButton6;
    @FXML
    private Button pitButton7;
    @FXML
    private Button pitButton8;
    @FXML
    private Button pitButton9;
    @FXML
    private Button pitButton10;
    @FXML
    private Button pitButton11;
    @FXML
    private Button pitButton12;
    @FXML
    private Button pitButton13;

    private GameModel gameModel;
    private int[] marbles;
    private int numOfMarbles;

    /**
     * Constructs a new HelloController object, initializing the associated GameModel.
     */
    public HelloController() {
        this.gameModel = new GameModel();
    }


    /**
     * Handles the event when a pit button is clicked.
     *
     * @param event The ActionEvent triggered by the button click.
     */
    @FXML
    protected void onPitButtonClick(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        int pitIndex = Integer.parseInt(clickedButton.getId().substring(9));
        if(gameModel.getGameState() == GameModel.STATE.START){
            if(pitIndex == 13){
                numOfMarbles = 3;
                marbles = gameModel.setAllPits(numOfMarbles);
            } else if (pitIndex == 6) {
                numOfMarbles = 4;
                marbles = gameModel.setAllPits(numOfMarbles);
            }
            gameModel.setGameState(GameModel.STATE.INCOMPLETE);
            updateUI();
        }
        if(gameModel.getGameState() == GameModel.STATE.COMPLETE){
            if(pitIndex == 13 || pitIndex == 6) {
                gameModel.setGameState(GameModel.STATE.INCOMPLETE);
                marbles = gameModel.setAllPits(numOfMarbles);
                updateUI();
            }
        }
        if (gameModel.canPlay(pitIndex)) {
            gameModel.move(pitIndex);
            marbles = gameModel.getAllPits();
            updateUI();
        }
    }

    /**
     * Initializes the UI when the FXML file is loaded.
     */
    @FXML
    public void initialize() {
        playerTurnLabel.setText("How many marbles? 3/4");
    }

    /**
     * Updates the UI based on the current state of the game model.
     */
    private void updateUI() {
        playerTurnLabel.setText("Current Player: " + gameModel.getCurrentPlayer());
        pitButton0.setText(String.valueOf(marbles[0]));
        pitButton1.setText(String.valueOf(marbles[1]));
        pitButton2.setText(String.valueOf(marbles[2]));
        pitButton3.setText(String.valueOf(marbles[3]));
        pitButton4.setText(String.valueOf(marbles[4]));
        pitButton5.setText(String.valueOf(marbles[5]));
        pitButton6.setText(String.valueOf(marbles[6]));
        pitButton7.setText(String.valueOf(marbles[7]));
        pitButton8.setText(String.valueOf(marbles[8]));
        pitButton9.setText(String.valueOf(marbles[9]));
        pitButton10.setText(String.valueOf(marbles[10]));
        pitButton11.setText(String.valueOf(marbles[11]));
        pitButton12.setText(String.valueOf(marbles[12]));
        pitButton13.setText(String.valueOf(marbles[13]));
        if(gameModel.getGameState() == GameModel.STATE.COMPLETE) {
            playerTurnLabel.setText(gameModel.getWinnerPlayer() + " || Click one of the mancalas to play again!");
        }
    }

}
