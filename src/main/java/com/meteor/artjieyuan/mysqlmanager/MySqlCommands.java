package com.meteor.artjieyuan.mysqlmanager;

public enum MySqlCommands {
    SELECT_DATA("select * from jieyuan where player = ?"),
    ADD_DATA("insert into jieyuan (player,gender,marry,partner,home,points,time,setting)values(?,?,?,?,?,?,?,?)"),
    CREATE_TAB(
            "CREATE TABLE IF NOT EXISTS jieyuan ("+
                    "player VARCHAR(100),"+ //主键 玩家名
                    "gender VARCHAR(100),"+ //性别
                    "marry VARCHAR(100),"+  //结缘状态
                    "partner VARCHAR(15),"+ //结缘对象
                    "home LONGBLOB,"+ //家
                    "points INT,"+ //亲密值
                    "time VARCHAR(100),"+ //结缘日期
                    "setting LONGBLOB,"+ //状态
                    "PRIMARY KEY (player))"
    );
    private String command;
    MySqlCommands(String command){
        this.command = command;
    }
    public String getCommand(){
        return this.command;
    }
}
