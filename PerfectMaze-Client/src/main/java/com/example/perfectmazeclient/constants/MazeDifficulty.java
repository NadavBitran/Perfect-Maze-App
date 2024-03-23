package com.example.perfectmazeclient.constants;

import java.util.Map;

public class MazeDifficulty {
    public static final Map<String, Integer> DIFFICULTY_MAP = Map.of(
            "Small", 10,
            "Medium", 15,
            "Large", 20
    );

    public static final Map<Integer, String> REVERSE_DIFFICULTY_MAP = Map.of(
            10, "Small",
            15, "Medium",
            20, "Large"
    );
}
