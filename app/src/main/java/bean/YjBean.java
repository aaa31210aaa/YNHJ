package bean;

public class YjBean {
    private int id;
    //预警信息名称
    private String yjName;
    //预警等级
    private String yjLevel;
    //发布时间
    private String fbTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYjName() {
        return yjName;
    }

    public void setYjName(String yjName) {
        this.yjName = yjName;
    }

    public String getYjLevel() {
        return yjLevel;
    }

    public void setYjLevel(String yjLevel) {
        this.yjLevel = yjLevel;
    }

    public String getFbTime() {
        return fbTime;
    }

    public void setFbTime(String fbTime) {
        this.fbTime = fbTime;
    }
}
