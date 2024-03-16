package com.example.perfectmazeclient.views;

import com.example.perfectmazeclient.dm.LeaderboardEntry;
import com.example.perfectmazeclient.constants.FXMLPaths;
import com.example.perfectmazeclient.util.PageLoader;
import javafx.collections.FXCollections;
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
    private TableView<LeaderboardEntry> leaderboardsTable;
    @FXML
    private TableColumn<LeaderboardEntry, String> usernameColumn;
    @FXML
    private TableColumn<LeaderboardEntry, Integer> mazesCompletedColumn;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // TODO: Fetch from server
        ObservableList<LeaderboardEntry> data = initializeFakeLeaderboardData();

        initializeLeaderboardsTable(data);
    }

    public void onBack(ActionEvent actionEvent) {
        PageLoader.loadPage(FXMLPaths.GAME_OPTIONS, actionEvent, getClass());
    }

    private ObservableList<LeaderboardEntry> initializeFakeLeaderboardData()
    {
        ObservableList<LeaderboardEntry> data = FXCollections.observableArrayList();
        for (int i = 0; i < 25; i++) {
            data.add(new LeaderboardEntry("User" + i, (int) (Math.random() * 100)));
        }

        return data;
    }
    private void initializeLeaderboardsTable(List<LeaderboardEntry> leaderboards)
    {
        leaderboardsTable.setItems((ObservableList<LeaderboardEntry>) leaderboards);
        mazesCompletedColumn.setSortType(TableColumn.SortType.DESCENDING);
        leaderboardsTable.getSortOrder().add(mazesCompletedColumn);
        leaderboardsTable.sort();
    }

}
