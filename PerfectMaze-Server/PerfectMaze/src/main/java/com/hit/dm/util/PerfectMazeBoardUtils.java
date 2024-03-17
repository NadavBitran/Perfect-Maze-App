package com.hit.dm.util;

import com.hit.dm.PerfectMazeBoard;

public class PerfectMazeBoardUtils {
    public static boolean isPerfectMazeBoardPropertiesExist(PerfectMazeBoard perfectMazeBoard)
    {
        return perfectMazeBoard != null &&
                perfectMazeBoard.getAlgorithm() != null &&
                perfectMazeBoard.getMaze() != null &&
                perfectMazeBoard.getEndingLocation() != null &&
                perfectMazeBoard.getStartingLocation() != null;
    }
}
