package com.meteor.artjieyuan.mysqlmanager;

import com.meteor.artjieyuan.Main;
import com.meteor.artjieyuan.data.Gender;
import com.meteor.artjieyuan.data.Marry;
import com.meteor.artjieyuan.data.PlayerData;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;

public class MySql {
    private Main plugin;
    private Connection mysql;
    private String user;
    private String password;
    //数据库连接
    public MySql(Main plugin) {
        this.plugin = plugin;
        this.user = plugin.getConfig().getString("mysql.user");
        this.password = plugin.getConfig().getString("mysql.password");
        try {
            this.mysql = DriverManager.getConnection(plugin.getConfig().getString("mysql.url"), user, password);
            PreparedStatement ps = this.mysql.prepareStatement(MySqlCommands.CREATE_TAB.getCommand());
            ps.execute();
            System.out.println("-----------------------------------");
            plugin.getLogger().info("结缘插件正在启用....");
            plugin.getServer().getLogger().info(" 已连接数据库...");
        } catch (SQLException e) {
            e.printStackTrace();
            plugin.getLogger().info("[ArtJieYuan] 数据库链接失败，请检查配置");
        }
    }
    public void doCommand(PreparedStatement ps){
        try {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Connection getMysql(){
        return this.mysql;
    }
    //玩家数据相关操作
    public YamlConfiguration getSetting(ResultSet set) throws IOException, InvalidConfigurationException, SQLException {
        YamlConfiguration yml = new YamlConfiguration();
        ByteArrayInputStream inputStream = (ByteArrayInputStream)set.getBinaryStream("setting");
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        String str = new String(bytes, StandardCharsets.UTF_8);
        yml.load(new StringReader(str));
        inputStream.close();
        return yml;
    }
    public Gender getGender(String gender){
        Gender rgender = null;
        switch (gender){
            case "GIRL":
                rgender = Gender.GIRL;
                return rgender;
            case "BOY":
                rgender = Gender.BOY;
                return rgender;
            case "NO":
                rgender = Gender.NO;
                return rgender;
        }
        return rgender;
    }
    public Marry getMarry(String marry){
        Marry rmarry = null;
        switch (marry){
            case "MARRY":
                rmarry =Marry.MARRY;
                return rmarry;
            case "NO":
                rmarry = Marry.NO;
                return rmarry;
        }
        return rmarry;
    }
    public void loadPlayerData(String player){
        PreparedStatement ps;
        try {
            ps = getMysql().prepareStatement(MySqlCommands.SELECT_DATA.getCommand());
            ps.setString(1,player);
            ResultSet set = ps.executeQuery();
            if(set.next()){
                YamlConfiguration yml = getSetting(set);
                Main.playerdata.put(player,new PlayerData(player,getGender(set.getString("gender"))
                , getMarry(set.getString("marry")),yml.getBoolean("setting.tp"),yml.getBoolean("setting.apply")));
                return;
            }
            String str = "";
            YamlConfiguration yml = new YamlConfiguration();
            yml.set("setting.tp",true);
            yml.set("setting.apply",true);
            ps = getMysql().prepareStatement(MySqlCommands.ADD_DATA.getCommand());
            ps.setString(1,player);
            ps.setString(2,Gender.values()[0].toString());
            ps.setString(3,Marry.values()[0].toString());
            ps.setString(4,str);
            ps.setBinaryStream(5,new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8)));
            ps.setInt(6,0);
            ps.setString(7,str);
            ps.setBinaryStream(8,new ByteArrayInputStream(yml.saveToString().getBytes(StandardCharsets.UTF_8)));
            ps.execute();
            loadPlayerData(player);
        } catch (SQLException | IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

}
