package com.meteor.artjieyuan.file;

import com.meteor.artjieyuan.Main;
import com.meteor.artjieyuan.data.GuiType;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private final Main plugin;
    public FileManager(Main plugin){
        this.plugin = plugin;
    }
    public void loadConfig(){
        File file = new File(plugin.getDataFolder()+"/config.yml");
        if(!file.exists()){
            plugin.getServer().getLogger().info(" 未检测到config.yml,已自动创建");
            plugin.saveDefaultConfig();
        }
        loadGenders();
        loadPaytype();
    }
    public void loadGenders(){
        File file = new File(plugin.getDataFolder()+"/genders.yml");
        if(!file.exists()){
            plugin.saveResource("genders.yml",false);
            plugin.getServer().getLogger().info(" 未检测到genders.yml,已自动创建");
        }
    }
    public void loadPaytype(){
        File file = new File(plugin.getDataFolder()+"/paytype.yml");
        if(!file.exists()){
            plugin.saveResource("paytype.yml",false);
            plugin.getServer().getLogger().info(" 未检测到paytype.yml,已自动创建");
            return;
        }
    }
    public void addFlag(Inventory inv, ItemStack item){
        int[] i = {0,1,2,4,6,7,8};
        for(int t : i){
            inv.setItem(t,item);
        }
    }
    public static ItemMeta getItemMeta(ItemStack item,String name, List<String> lore){
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(name);
        List<String> itemlore = new ArrayList<>();
        for(String str : lore){
            str = str.replace("&","§");
        }
        itemMeta.setLore(itemlore);
        return itemMeta;
    }
    public void loadGendersGui(){
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder()+"/genders.yml"));
        Inventory inv = Bukkit.createInventory(null,54,yml.getString("gender.title").replace("&","§"));
        ItemStack flag = new ItemStack(Material.valueOf(yml.getString("gender.flag.ID")),1,(short)yml.getInt("gender.data"));
        ItemMeta itemMeta = flag.getItemMeta();
        itemMeta.setDisplayName(yml.getString("gender.flag.name").replace("&","§"));
        flag.setItemMeta(itemMeta);
        addFlag(inv,flag);
        //男
        ItemStack boy = new ItemStack(Material.valueOf(yml.getString("gender.boy.ID")),1,(short)yml.getInt("gender.boy.data"));
        boy.setItemMeta(getItemMeta(boy,yml.getString("gender.boy.name").replace("&","§"),yml.getStringList("gender.boy.lore")));
        //女
        ItemStack girl = new ItemStack(Material.valueOf(yml.getString("gender.girl.ID")),1,(short)yml.getInt("gender.girl.data"));
        girl.setItemMeta(getItemMeta(girl,yml.getString("gender.girl.name").replace("&","§"),yml.getStringList("gender.girl.lore")));
        //
        inv.setItem(3,boy);
        inv.setItem(5,girl);
        Main.gui.put(GuiType.GENDERS,inv);
        loadPaytype();
    }
    public void loadPaytypeGui(){
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(new File(plugin.getDataFolder()+"/paytype.yml"));
        Inventory inv = Bukkit.createInventory(null,54,yml.getString("type.title").replace("&","§"));
        ItemStack flag = new ItemStack(Material.valueOf(yml.getString("type.flag.ID")),1,(short)yml.getInt("type.flag.data"));
        ItemMeta itemMeta = flag.getItemMeta();
        itemMeta.setDisplayName(yml.getString("type.flag.name").replace("&","§"));
        flag.setItemMeta(itemMeta);
        addFlag(inv,flag);

        //点卷支付
        ItemStack points = new ItemStack(Material.valueOf(yml.getString("type.points.ID")),1,(short)yml.getInt("type.points.data"));
        points.setItemMeta(getItemMeta(points,yml.getString("type.points.name").replace("&","§"),yml.getStringList("type.points.lore")));

        //金币支付
        ItemStack money = new ItemStack(Material.valueOf(yml.getString("type.money.ID")),1,(short)yml.getInt("type.money.data"));
        money.setItemMeta(getItemMeta(money,yml.getString("type.money.name").replace("&","§"),yml.getStringList("type.money.lore")));

        //
        inv.setItem(3,points);
        inv.setItem(5,money);
        Main.gui.put(GuiType.PAYTYPE,inv);
    }
}
