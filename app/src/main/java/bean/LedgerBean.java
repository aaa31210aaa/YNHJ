package bean;

import com.contrarywind.interfaces.IPickerViewData;

public class LedgerBean implements IPickerViewData {
    private String id;
    private String tj_content; //台阶
    private String date; //日期
    private String kkgzm;//矿块(工作面)
    private String kslx; //矿石类型
    private String pw;//品位
    private String jsl;//金属量
    private String ksl;//矿石量

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTj_content() {
        return tj_content;
    }

    public void setTj_content(String tj_content) {
        this.tj_content = tj_content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKkgzm() {
        return kkgzm;
    }

    public void setKkgzm(String kkgzm) {
        this.kkgzm = kkgzm;
    }

    public String getKslx() {
        return kslx;
    }

    public void setKslx(String kslx) {
        this.kslx = kslx;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getJsl() {
        return jsl;
    }

    public void setJsl(String jsl) {
        this.jsl = jsl;
    }

    public String getKsl() {
        return ksl;
    }

    public void setKsl(String ksl) {
        this.ksl = ksl;
    }

    @Override
    public String getPickerViewText() {
        return tj_content;
    }
}
