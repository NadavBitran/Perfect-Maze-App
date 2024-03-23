package com.hit.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.hit.algorithm.BFS;
import com.hit.algorithm.DFS;
import com.hit.algorithm.IShortestPaths;
import com.hit.dm.Game;
import com.hit.dm.PerfectMazeBoard;
import com.hit.dm.User;
import com.hit.exceptions.ControllerRoutingFailed;
import com.hit.exceptions.ServiceRequestFailed;
import com.hit.server.Request;
import com.hit.server.Response;
import com.hit.server.util.ResponseUtils;
import com.hit.service.GameService;
import com.hit.util.UndirectedGraphCreator;

import java.util.List;

public class GameController implements IController {

    private GameService gameService;
    private Gson gson = new Gson();

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }
    @Override
    public Response executeAction(Request request) throws ControllerRoutingFailed {

        String methodAction = request.getHeaders().getAction().split("/")[1];
        String currentController = request.getHeaders().getAction().split("/")[0];

        switch (methodAction) {
            case "generateMaze":
                return generateMaze(request.getBody());
            case "save":
                return saveGame(request.getBody());
            case "delete":
                return deleteGame(request.getBody());
            case "load":
                return loadGame(request.getBody());
            case "loadPerUser":
                return loadGamesOfUser(request.getBody());
            case "updateTime":
                return updateGameTime(request.getBody());
            default:
                throw new ControllerRoutingFailed("Failed to route to method action: " + methodAction + " in controller: " + currentController);
        }
    }
    public Response saveGame(JsonElement body) throws JsonSyntaxException {
        Game game = gson.fromJson(body, Game.class);

        Response response = null;

        try
        {
            Game savedGame = gameService.saveGame(game);
            response = ResponseUtils.buildResponse("Game saved successfully", "success", "gameId", savedGame.getGameId());
        }
        catch (ServiceRequestFailed e) {
            response = ResponseUtils.buildResponse(e.getMessage(), "failed");
        }

        return response;
    }

    public Response deleteGame(JsonElement body) throws JsonSyntaxException {
        Game game = gson.fromJson(body, Game.class);

        Response response = null;

        try
        {
            gameService.deleteGame(game.getUserId(), game.getGameId());
            response = ResponseUtils.buildResponse("Game deleted successfully", "success");
        }
        catch (ServiceRequestFailed e) {
            response = ResponseUtils.buildResponse(e.getMessage(), "failed");
        }


        return response;
    }

    public Response loadGame(JsonElement body) throws JsonSyntaxException {
        Game game = gson.fromJson(body, Game.class);

        Response response = null;

        try
        {
            Game loadedGame = gameService.getGame(game.getUserId(), game.getGameId());
            response = ResponseUtils.buildResponse("Game loaded successfully", "success", "game", loadedGame);
        }
        catch (ServiceRequestFailed e) {
            response = ResponseUtils.buildResponse(e.getMessage(), "failed");
        }

        return response;
    }

    public Response loadGamesOfUser(JsonElement body) throws JsonSyntaxException {
        User user = gson.fromJson(body, User.class);

        Response response = null;

        try
        {
            List<Game> games = gameService.getAllGamesOfUser(user.getUserId());
            response = ResponseUtils.buildResponse("Games loaded successfully", "success", "games", games);
        }
        catch (ServiceRequestFailed e) {
            response = ResponseUtils.buildResponse(e.getMessage(), "failed");
        }

        return response;
    }

    public Response updateGameTime(JsonElement body) throws JsonSyntaxException {
        Game game = gson.fromJson(body, Game.class);

        Response response = null;

        try
        {
            gameService.updateGameTimeImprovement(game.getUserId(), game.getGameId(), game.getTimeToSolve());
            response = ResponseUtils.buildResponse("Game time updated successfully", "success");
        }
        catch (ServiceRequestFailed e) {
            response = ResponseUtils.buildResponse(e.getMessage(), "failed");
        }

        return response;
    }

    public Response generateMaze(JsonElement body) throws JsonSyntaxException {

        PerfectMazeBoard dataAboutMaze = gson.fromJson(body, PerfectMazeBoard.class);
        IShortestPaths<Integer> algorithm = null;

        Response response = null;

        if(dataAboutMaze.getAlgorithm() == null)
            return ResponseUtils.buildResponse("Failed to generate maze: algorithm not specified", "failed");


        switch (dataAboutMaze.getAlgorithm())
        {
            case "BFS" :
                algorithm = new BFS<>(
                        UndirectedGraphCreator.createNxNGridGraph(dataAboutMaze.getRows()),
                        1);
                break;
            case "DFS":
                algorithm = new DFS<>(
                        UndirectedGraphCreator.createNxNGridGraph(dataAboutMaze.getRows()),
                        1);
                break;
        }

        try
        {
            PerfectMazeBoard maze = gameService.generateMaze(dataAboutMaze.getRows(), algorithm, dataAboutMaze.getAlgorithm());
            response = ResponseUtils.buildResponse("Maze generated successfully", "success", "mazeBoard", maze);
        }
        catch (ServiceRequestFailed e) {
            response = ResponseUtils.buildResponse(e.getMessage(), "failed");
        }

        return response;
    }

}
