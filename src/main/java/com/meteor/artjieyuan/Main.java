package com.meteor.artjieyuan;

import com.meteor.artjieyuan.Events.PlayerJoin;
import com.meteor.artjieyuan.data.GuiType;
import com.meteor.artjieyuan.data.PlayerData;
import com.meteor.artjieyuan.file.FileManager;
import com.meteor.artjieyuan.mysqlmanager.MySql;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public final class Main extends JavaPlugin {
    private MySql mySql;
    private FileManager filem;
    public static HashMap<String, PlayerData> playerdata = new HashMap<>();
    public static HashMap<GuiType, Inventory> gui = new HashMap<>();
    @Override
    public void onEnable() {
        // Plugin startup logic
        filem = new FileManager(this);
        {
            filem.loadConfig();
            this.mySql = new MySql(this);
            getServer().getPluginManager().registerEvents(new PlayerJoin(this),this);
            getServer().getLogger().info(" 插件监听器注册完成...");
            System.out.println("-----------------------------------");
        }
        filem.loadGendersGui();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public MySql getMySql() {
        return mySql;
    }
}
