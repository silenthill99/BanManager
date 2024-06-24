package fr.silenthill99.banmanager.utils;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public enum TimeUnit {
    SECONDE("Secondes", "sec", 1),
    MINUTE("Minutes", "min", 60),
    HEURE("Heures", "h", 3600),
    JOUR("Jours", "j", 24*3600),
    MOIS("Mois", "mo", 30*34*3600);

    private final String name;
    private final String shortcut;
    private final long toSecond;

    private static final HashMap<String, TimeUnit> ID_SHORTCUT = new HashMap<>();

    TimeUnit(String name, String shortcut, long toSecond) {
        this.name = name;
        this.shortcut = shortcut;
        this.toSecond = toSecond;
    }

    static {
        for (TimeUnit unit : values()) {
            ID_SHORTCUT.put(unit.shortcut, unit);
        }
    }

    /**
     * Récupérer le TimeUnit associé au shortcut
     */
    public static TimeUnit getFromShortCut(String shortcut) {
        return ID_SHORTCUT.get(shortcut);
    }

    @NotNull
    public String getName() {
        return name;
    }

    public String getShortcut() {
        return shortcut;
    }

    public long getToSecond() {
        return toSecond;
    }
}
