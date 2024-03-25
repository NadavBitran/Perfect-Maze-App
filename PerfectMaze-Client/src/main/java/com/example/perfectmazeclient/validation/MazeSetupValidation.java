package com.example.perfectmazeclient.validation;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class MazeSetupValidation {
    private static final String FIELD_ERROR_CLASS = "field-error";
    private Label unselectedFieldsError;
    private ChoiceBox<String> mazeSizeChoiceBox;
    private ChoiceBox<String> generatorAlgorithmChoiceBox;

    public MazeSetupValidation(ChoiceBox<String> mazeSizeChoiceBox, ChoiceBox<String> generatorAlgorithmChoiceBox, Label unselectedFieldsError) {
        this.mazeSizeChoiceBox = mazeSizeChoiceBox;
        this.generatorAlgorithmChoiceBox = generatorAlgorithmChoiceBox;
        this.unselectedFieldsError = unselectedFieldsError;
    }
    public boolean validateMazeSetup() {

        boolean isValid = true;

        resetErrorFields();

        String difficulty = mazeSizeChoiceBox.getValue();
        String generatorType = generatorAlgorithmChoiceBox.getValue();

        if (difficulty == null || difficulty.isEmpty()) {
            showErrorField(mazeSizeChoiceBox, "Please select all fields.");
            isValid = false;
        }
        if (generatorType == null || generatorType.isEmpty()) {
            showErrorField(generatorAlgorithmChoiceBox, "Please select all fields.");
            isValid = false;
        }
        return isValid;
    }

    private void resetErrorFields() {
        unselectedFieldsError.setVisible(false);
        unselectedFieldsError.setManaged(false);

        mazeSizeChoiceBox.getStyleClass().remove(FIELD_ERROR_CLASS);
        generatorAlgorithmChoiceBox.getStyleClass().remove(FIELD_ERROR_CLASS);
    }

    private void showErrorField(ChoiceBox<String> field, String errorMessage) {
        field.getStyleClass().add(FIELD_ERROR_CLASS);
        unselectedFieldsError.setText(errorMessage);
        unselectedFieldsError.setVisible(true);
        unselectedFieldsError.setManaged(true);
    }

}
