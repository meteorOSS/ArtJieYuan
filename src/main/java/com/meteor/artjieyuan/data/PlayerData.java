package com.meteor.artjieyuan.data;

public class PlayerData {
    private String playername;
    private Gender gender;
    private Marry marry;
    private Boolean tp;
    private Boolean apply;
    public PlayerData(String playername, com.meteor.artjieyuan.data.Gender gender, com.meteor.artjieyuan.data.Marry marry, Boolean tp, Boolean apply) {
        this.playername = playername;
        this.gender = gender;
        this.marry = marry;
        this.tp = tp;
        this.apply = apply;
    }
    //
    public String getPlayername() {
        return playername;
    }
    public void setPlayername(String playername) {
        this.playername = playername;
    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public Marry getMarry() {
        return marry;
    }
    public void setMarry(Marry marry) {
        this.marry = marry;
    }
    public Boolean getTp() {
        return tp;
    }
    public void setTp(Boolean tp) {
        this.tp = tp;
    }
    public Boolean getApply() {
        return apply;
    }
    public void setApply(Boolean apply) {
        this.apply = apply;
    }

}
