package com.example.perfectmazeclient.requests.handlers;

import com.example.perfectmazeclient.constants.MazeDifficulty;
import com.example.perfectmazeclient.dm.Game;
import com.example.perfectmazeclient.dm.PerfectMazeBoard;
import com.example.perfectmazeclient.exceptions.RequestFailed;
import com.example.perfectmazeclient.requests.communication.Response;
import com.example.perfectmazeclient.requests.sender.Sender;
import com.example.perfectmazeclient.util.CurrentGame;
import com.example.perfectmazeclient.util.LoggedUser;
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

        String status = body.get("status").getAsString();
        String message = body.get("message").getAsString();

        if(!status.equals("success")) throw new RequestFailed(message);

        maze = gson.fromJson(body.get("mazeBoard"), PerfectMazeBoard.class);

        Game game = new Game();
        game.setMazeBoard(maze);

        CurrentGame.setCurrentGame(game);
    }
    public static void handleMazeSolvedRequest(PerfectMazeBoard maze, int time) throws RequestFailed {

        Game game = new Game(maze, time);

        Response response = Sender.sendRequest(SAVE_GAME, game);

        JsonObject body = response.getBody().getAsJsonObject();

        String status = body.get("status").getAsString();
        String message = body.get("message").getAsString();

        if(!status.equals("success")) throw new RequestFailed(message);

        String gameId = body.get("gameId").getAsString();

        game.setGameId(gameId);
        CurrentGame.setCurrentGame(game);
    }
    public static void handleMazeImprovementRequest(String gameId, int improvedTime) throws RequestFailed {

        Game game = new Game();
        game.setGameId(gameId);
        game.setTimeToSolve(improvedTime);
        game.setUserId(LoggedUser.getLoggedUser().getUserId());

        Response response = Sender.sendRequest(UPDATE_GAME_TIME, game);

        JsonObject body = response.getBody().getAsJsonObject();

        String status = body.get("status").getAsString();
        String message = body.get("message").getAsString();

        if(!status.equals("success")) throw new RequestFailed(message);

        game.setTimeToSolve(improvedTime);
        CurrentGame.setCurrentGame(game);
    }
}
