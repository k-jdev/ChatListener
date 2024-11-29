import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

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
        // Реализуем HTTP-запрос здесь
    }
}
