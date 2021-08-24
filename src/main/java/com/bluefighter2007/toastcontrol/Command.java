package com.bluefighter2007.toastcontrol;

import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(args.length == 2) {
                ToastManager.AdvancementSend(new NamespacedKey(controlMain.getInstance(), "Toa" + sender.getName()), ((Player) sender).getPlayer(), args[0], args[1], "minecraft:bookshelf");
            }
        }
        return false;
    }
}
