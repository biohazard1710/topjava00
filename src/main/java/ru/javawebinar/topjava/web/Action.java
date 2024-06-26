package ru.javawebinar.topjava.web;

import java.util.Arrays;

public enum Action {
    CREATE("create"),
    UPDATE("update"),
    DELETE("delete"),
    FILTER("filter"),
    ALL("all");

    private final String action;

    Action(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public static Action fromString(String action) {
        return Arrays.stream(Action.values())
                .filter(a -> a.getAction().equalsIgnoreCase(action))
                .findFirst()
                .orElse(null);
    }
}