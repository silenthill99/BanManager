package fr.silenthill99.banmanager.ban;

import fr.silenthill99.banmanager.Main;

import java.util.UUID;

public class BanManager {

    private final Main main = Main.getInstance();

    public void ban(UUID uuid, long end, String reason) {

    }

    public void unban(UUID uuid) {

    }

    public boolean isBanned(UUID uuid) {
        return false;
    }

    public void checkDuration(UUID uuid) {

    }

    public long getEnd(UUID uuid) {
        return 0;
    }

    public String getTimeLeft(UUID uuid) {
        return "";
    }

    public String getReason(UUID uuid) {
        return "";
    }

}
