package org.example.xenos.chatListenerPlugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatListenerPlugin extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("ChatListenerPlugin enabled!");
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("ChatListenerPlugin disabled!");
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String playerName = event.getPlayer().getName();
        String message = event.getMessage();
        getLogger().info(playerName + ": " + message);

        // Отправка сообщения на Node.js бэкенд
        sendToBackend(playerName, message);
    }

    private void sendToBackend(String playerName, String message) {
        try {
            URL url = new URL("http://localhost:5000/chat"); // Укажите ваш адрес
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonPayload = String.format("{\"player\":\"%s\", \"message\":\"%s\"}", playerName, message);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(jsonPayload.getBytes());
                os.flush();
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                getLogger().info("Message sent to backend successfully.");
            } else {
                getLogger().warning("Failed to send message to backend: " + responseCode);
            }
        } catch (Exception e) {
            getLogger().severe("Error sending message to backend: " + e.getMessage());
        }
    }
}
