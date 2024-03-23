package com.example.perfectmazeclient.views;

import com.example.perfectmazeclient.dm.Leaderboards;
import com.example.perfectmazeclient.dm.LeaderboardsEntity;
import com.example.perfectmazeclient.constants.FXMLPaths;
import com.example.perfectmazeclient.exceptions.RequestFailed;
import com.example.perfectmazeclient.requests.handlers.LeaderboardRequests;
import com.example.perfectmazeclient.util.AlertError;
import com.example.perfectmazeclient.util.PageLoader;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LeaderboardsController implements Initializable {

    @FXML
    private TableView<LeaderboardsEntity> leaderboardsTable;
    @FXML
    private TableColumn<LeaderboardsEntity, String> usernameColumn;
    @FXML
    private TableColumn<LeaderboardsEntity, Integer> mazesCompletedColumn;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<LeaderboardsEntity> data = initializeLeaderboardData();

        initializeLeaderboardsTable(data);
    }

    public void onBack(ActionEvent actionEvent) {
        PageLoader.loadPage(FXMLPaths.GAME_OPTIONS, actionEvent, getClass());
    }

    private ObservableList<LeaderboardsEntity> initializeLeaderboardData()
    {
        try
        {
            Leaderboards leaderboards = LeaderboardRequests.handleGetLeaderboardsRequest();
            return (ObservableList<LeaderboardsEntity>) leaderboards.getLeaderboards();
        }
        catch (RequestFailed e)
        {
            AlertError.showAlertError("Error", "Leaderboards", e.getMessage(), FXMLPaths.GAME_OPTIONS);
            return null;
        }
    }
    private void initializeLeaderboardsTable(List<LeaderboardsEntity> leaderboards)
    {
        leaderboardsTable.setItems((ObservableList<LeaderboardsEntity>) leaderboards);
        mazesCompletedColumn.setSortType(TableColumn.SortType.DESCENDING);
        leaderboardsTable.getSortOrder().add(mazesCompletedColumn);
        leaderboardsTable.sort();
    }
}
