package com.bluefighter2007.toastcontrol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ToastManager {

    private static boolean announce = false, toast = true;

    public static void AdvancementSend(NamespacedKey id, Player p, String title, String description, String icon) {

        add(id, title, description, icon);

        Advancement advancement = Bukkit.getAdvancement(id);
        final AdvancementProgress[] progress = new AdvancementProgress[1];
        progress[0] = p.getAdvancementProgress(advancement);
        if (!progress[0].isDone())	{
            for (String criteria : progress[0].getRemainingCriteria())	{
                progress[0].awardCriteria(criteria);
            }
        }
        new BukkitRunnable() {

            @Override
            public void run() {

                progress[0] = p.getAdvancementProgress(advancement);
                if (progress[0].isDone())	{
                    for (String criteria : progress[0].getAwardedCriteria())	{
                        progress[0].revokeCriteria(criteria);
                    }
                }
                remove(id);
            }
        }.runTaskLater(controlMain.getInstance(), 20);




    }

    @SuppressWarnings("deprecation")
    private static void add(NamespacedKey id, String title, String description, String icon)	{
        try {
            Bukkit.getUnsafe().loadAdvancement(id, getJson(title, description, icon));
            Bukkit.getLogger().info("Advancement " + id + " saved");
        } catch (IllegalArgumentException e){
            Bukkit.getLogger().info("Error while saving, Advancement " + id + " seems to already exist");
            remove(id);
        }
    }

    @SuppressWarnings("deprecation")
    private static void remove(NamespacedKey id)	{
        try {
            Bukkit.getUnsafe().removeAdvancement(id);
            Bukkit.getLogger().info("Advancement " + id + " removed");
        } catch (IllegalArgumentException e){
            Bukkit.getLogger().info("Error while deleting" + e);
        }
        Bukkit.getUnsafe().removeAdvancement(id);
    }

    public static String getJson(String title, String description, String ixcon)	{

        JsonObject json = new JsonObject();

        JsonObject icon = new JsonObject();
        icon.addProperty("item", ixcon);

        JsonObject display = new JsonObject();
        display.add("icon", icon);


        String title2 = title + "\n" + description;
        title2 = title2.replace("&","§");



        display.add("icon", icon);
        display.addProperty("title",title2);
        display.addProperty("description", description);
        display.addProperty("background", "minecraft:textures/gui/advancements/backgrounds/adventure.png");
        display.addProperty("frame", "challenge");
        display.addProperty("announce_to_chat", announce);
        display.addProperty("show_toast", toast);
        display.addProperty("hidden", true);

        JsonObject criteria = new JsonObject();
        JsonObject trigger = new JsonObject();

        trigger.addProperty("trigger", "minecraft:impossible");
        criteria.add("impossible", trigger);

        json.add("criteria", criteria);
        json.add("display", display);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(json);

    }


}
