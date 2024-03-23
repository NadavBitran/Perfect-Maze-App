package com.example.perfectmazeclient.views;

import com.example.perfectmazeclient.dm.Leaderboards;
import com.example.perfectmazeclient.dm.LeaderboardsEntity;
import com.example.perfectmazeclient.constants.FXMLPaths;
import com.example.perfectmazeclient.exceptions.RequestFailed;
import com.example.perfectmazeclient.requests.handlers.LeaderboardRequests;
import com.example.perfectmazeclient.util.AlertError;
import com.example.perfectmazeclient.util.PageLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

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

        List<LeaderboardsEntity> data = initializeLeaderboardData();

        initializeLeaderboardsTable(data);
    }

    public void onBack(ActionEvent actionEvent) {
        PageLoader.loadPage(FXMLPaths.GAME_OPTIONS, actionEvent, getClass());
    }

    private List<LeaderboardsEntity> initializeLeaderboardData()
    {
        try
        {
            Leaderboards leaderboards = LeaderboardRequests.handleGetLeaderboardsRequest();
            return leaderboards.getLeaderboards();
        }
        catch (RequestFailed e)
        {
            AlertError.showAlertError("Error", "Leaderboards", e.getMessage(), FXMLPaths.GAME_OPTIONS);
            return null;
        }
    }
    private void initializeLeaderboardsTable(List<LeaderboardsEntity> leaderboards)
    {
        if (leaderboards != null) {
            usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
            mazesCompletedColumn.setCellValueFactory(new PropertyValueFactory<>("gamesCount"));


            ObservableList<LeaderboardsEntity> observableList = FXCollections.observableArrayList(leaderboards);
            leaderboardsTable.setItems(observableList);
            mazesCompletedColumn.setSortType(TableColumn.SortType.DESCENDING);
            leaderboardsTable.getSortOrder().add(mazesCompletedColumn);
            leaderboardsTable.sort();
        }
    }
}
