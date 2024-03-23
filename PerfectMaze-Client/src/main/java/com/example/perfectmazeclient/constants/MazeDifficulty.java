package com.example.perfectmazeclient.constants;

import java.util.Map;

public class MazeDifficulty {
    public static final Map<String, Integer> DIFFICULTY_MAP = Map.of(
            "Small", 10,
            "Medium", 20,
            "Large", 30
    );

    public static final Map<Integer, String> REVERSE_DIFFICULTY_MAP = Map.of(
            10, "Small",
            20, "Medium",
            30, "Large"
    );
}
