import com.hit.dm.Game;
import com.hit.service.GameService;
import com.hit.dm.PerfectMazeBoard;

import java.util.List;

public class Program {

    // TEST PLAYGROUND FOR OUR SERVICES
    // DELETE AFTER!!!
    public static void main(String[] args) {
        gameServiceGeneralFlow();
    }

    public static void gameServiceGeneralFlow()
    {
        // STEP 0. Client logs in
        String userId = "user123";

        // STEP 1. Client communicating with server to generate a maze (10x10 maze example)
        GameService gameService = new GameService("games.txt");
        int usersChosenMazeSize = 10;
        PerfectMazeBoard generatedMaze = gameService.generateMaze(usersChosenMazeSize);
        // generated maze sends back to client and starts playing...

        // STEP 2. Client finished playing and his game stats being auto saved
        Game gameplayed = new Game(generatedMaze, 105, userId);

        gameService.saveGame(gameplayed);

        String generatedGameId = gameplayed.getGameId();
        // gameId is auto generated and goes back to client


        // results after first play session:
        System.out.println(gameService.getGame(userId, generatedGameId).toString());

        // STEP 3. Client chooses to re-play current maze and gets new personal best time
        String gameId = generatedGameId;
        int newPersonalBest = 95;

        gameService.updateGameTimeImprovement(userId, gameId, newPersonalBest);

        // results after second play session:
        System.out.println(gameService.getGame(userId, generatedGameId).toString());


        // client chooses to play a new maze... (process starting again at STEP 1)


        // EXTRA STEP. Game details gets deleted
        gameService.deleteGame(userId, generatedGameId);

        // results after deletion:
        System.out.println(gameService.getGame(userId, generatedGameId));


        // EXTRA STEP. Get all games
        Game gameplayed1 = new Game(gameService.generateMaze(usersChosenMazeSize), 105, "user123");
        Game gameplayed2 = new Game(gameService.generateMaze(usersChosenMazeSize), 80, "user456");
        Game gameplayed3 = new Game(gameService.generateMaze(usersChosenMazeSize), 30, "user123");
        Game gameplayed4 = new Game(gameService.generateMaze(usersChosenMazeSize), 10, "user456");
        Game gameplayed5 = new Game(gameService.generateMaze(usersChosenMazeSize), 40, "user123");

        gameService.saveGame(gameplayed1);
        gameService.saveGame(gameplayed2);
        gameService.saveGame(gameplayed3);
        gameService.saveGame(gameplayed4);
        gameService.saveGame(gameplayed5);


        List<Game> games = gameService.getAllGamesOfUser(userId);

        for (Game game : games) {
            System.out.println(game.toString());
        }
    }
}
