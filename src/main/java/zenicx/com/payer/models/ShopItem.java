package zenicx.com.payer.models;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import java.util.List;

public class ShopItem {
    private List<String> paymentMethods;
    private String price;
    private String description;
    private String name;
    private List<String> commands;

    public ShopItem(List<String> paymentMethods, String price, String description, String name, List<String> commands) {
        this.paymentMethods = paymentMethods;
        this.price = price;
        this.description = description;
        this.name = name;
        this.commands = commands;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public List<String> getPaymentMethods() {
        return paymentMethods;
    }

    public List<String> getCommands() {
        return commands;
    }

    public String getDescription() {
        return description;
    }

    public void setPaymentMethods(List<String> paymentMethods) {
        this.paymentMethods = paymentMethods;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public void perform() {
        ConsoleCommandSender commandSender = Bukkit.getConsoleSender();
        commands.forEach(command -> {
            Bukkit.dispatchCommand(commandSender, command);
        });
    }
}
