package com.example.perfectmazeclient.validation;

import com.example.perfectmazeclient.util.AlertError;

public class MazeSetupValidation {
    public static boolean validateMazeSetup(String difficulty, String generatiorType) {
        if (difficulty == null || difficulty.isEmpty()) {
            AlertError.showAlertError("Maze Setup Error", "Selection Setup", "Please select a maze size.");
            return false;
        }
        if (generatiorType == null || generatiorType.isEmpty()) {
            AlertError.showAlertError("Maze Setup Error", "Selection Setup", "Please select a generator algorithm.");
            return false;
        }
        return true;
    }

}
