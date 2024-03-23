package com.example.perfectmazeclient.requests.handlers;

import com.example.perfectmazeclient.dm.Game;
import com.example.perfectmazeclient.dm.PerfectMazeBoard;
import com.example.perfectmazeclient.exceptions.RequestFailed;
import com.example.perfectmazeclient.requests.communication.Response;
import com.example.perfectmazeclient.requests.communication.util.ResponseProperties;
import com.example.perfectmazeclient.requests.sender.Sender;
import com.example.perfectmazeclient.containers.CurrentGameContainer;
import com.example.perfectmazeclient.containers.CurrentLoggedUserContainer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class GameRequests {
    private static final Gson gson = new Gson();
    private static final String GENERATE_MAZE = "Game/generateMaze";
    private static final String SAVE_GAME = "Game/save";
    private static final String UPDATE_GAME_TIME = "Game/updateTime";
    public static void handleMazeGenerationRequest(int mazeSize, String algorithm) throws RequestFailed {

        PerfectMazeBoard maze = new PerfectMazeBoard(mazeSize, algorithm);

        Response response = Sender.sendRequest(GENERATE_MAZE, maze);

        JsonObject body = response.getBody().getAsJsonObject();

        String status = body.get(ResponseProperties.STATUS).getAsString();
        String message = body.get(ResponseProperties.MESSAGE).getAsString();

        if(!status.equals(ResponseProperties.STATUS_SUCCESS)) throw new RequestFailed(message);

        maze = gson.fromJson(body.get(ResponseProperties.MAZE_BOARD), PerfectMazeBoard.class);

        CurrentGameContainer.setCurrentGame(new Game(maze));
    }
    public static void handleMazeSolvedRequest(PerfectMazeBoard maze, int time) throws RequestFailed {

        Game game = new Game(maze, time);
        game.setUserId(CurrentLoggedUserContainer.getLoggedUser().getUserId());
        game.setUserEmail(CurrentLoggedUserContainer.getLoggedUser().getEmail());

        Response response = Sender.sendRequest(SAVE_GAME, game);

        JsonObject body = response.getBody().getAsJsonObject();

        String status = body.get(ResponseProperties.STATUS).getAsString();
        String message = body.get(ResponseProperties.MESSAGE).getAsString();

        if(!status.equals(ResponseProperties.STATUS_SUCCESS)) throw new RequestFailed(message);

        String gameId = body.get(ResponseProperties.GAME_ID).getAsString();

        game.setGameId(gameId);
        CurrentGameContainer.setCurrentGame(game);
    }
    public static void handleMazeImprovementRequest(String gameId, int improvedTime) throws RequestFailed {

        Game game = new Game();
        game.setGameId(gameId);
        game.setTimeToSolve(improvedTime);
        game.setUserId(CurrentLoggedUserContainer.getLoggedUser().getUserId());
        game.setUserEmail(CurrentLoggedUserContainer.getLoggedUser().getEmail());

        Response response = Sender.sendRequest(UPDATE_GAME_TIME, game);

        JsonObject body = response.getBody().getAsJsonObject();

        String status = body.get(ResponseProperties.STATUS).getAsString();
        String message = body.get(ResponseProperties.MESSAGE).getAsString();

        if(!status.equals(ResponseProperties.STATUS_SUCCESS)) throw new RequestFailed(message);

        Game currentGame = CurrentGameContainer.getCurrentGame();
        currentGame.setTimeToSolve(improvedTime);
        CurrentGameContainer.setCurrentGame(currentGame);
    }
}
