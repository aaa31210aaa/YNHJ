package ui.workbench;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.dimine.ynhj.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import utils.AppUtils;
import utils.BaseActivity;
import utils.DateUtils;
import utils.ShowToast;

public class AddDbrw extends BaseActivity {
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.dbrw_zydd)
    Spinner dbrw_zydd;
    @BindView(R.id.dbrw_bc)
    Spinner dbrw_bc;
    @BindView(R.id.dbrw_yfc)
    EditText dbrw_yfc;
    @BindView(R.id.dbrw_zcs)
    EditText dbrw_zcs;
    private String itemString = "";

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_add_dbrw;
    }

    @Override
    protected void initView() {
        AppUtils.EtvNoSpace(dbrw_yfc);
        AppUtils.EtvNoSpace(dbrw_zcs);
        initSpinnerZydd();
        initSpinnerBc();
    }

    @Override
    protected void initData() {
        title_name.setText(R.string.dbsy);
        int hour = Integer.parseInt(DateUtils.getHour());
        if (hour > 7 && hour < 17) {
            dbrw_bc.setSelection(0);
        }else{
            dbrw_bc.setSelection(1);
        }
    }

    @OnClick(R.id.back)
    void Back() {
        finish();
    }

    /**
     * 确认添加
     */
    @OnClick(R.id.add_dbrw_submit)
    void Submit() {
        if (dbrw_yfc.getText().toString().equals("") || dbrw_zcs.getText().toString().equals("")) {
            ShowToast.showShort(this, "请填写发车数和总车数");
        } else {
            ShowToast.showShort(this, "新增成功");
            setResult(RESULT_OK);
            finish();
        }
    }


    private void initSpinnerZydd() {
        // 数据源手动添加
        ArrayAdapter<String> spinnerAadapter = new ArrayAdapter<String>(this,
                R.layout.custom_spiner_text_item, getDataSource());
        spinnerAadapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        dbrw_zydd.setAdapter(spinnerAadapter);

        dbrw_zydd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                itemString = dbrw_zydd.getItemAtPosition(i).toString();
                return;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private List<String> getDataSource() {
        List<String> spinnerList = new ArrayList<String>();
        spinnerList.add("西部/1604/KT52-2a/p50-52");
        spinnerList.add("东部/1624/KT/p52-54");
        spinnerList.add("西部/1684/KT52-2a/p62-64");
        spinnerList.add("北部/1654/KT52-1/p68-70");
        spinnerList.add("北部/1734/KT52-1/p74-76");
        spinnerList.add("北部/1724/KT52-1/p72-74");
        spinnerList.add("东部/1744/KT/p54-56");
        return spinnerList;
    }


    private void initSpinnerBc() {
        // 数据源手动添加
        ArrayAdapter<String> spinnerAadapter = new ArrayAdapter<String>(this,
                R.layout.custom_spiner_text_item, getBc());
        spinnerAadapter.setDropDownViewResource(R.layout.custom_spinner_dropdown_item);
        dbrw_bc.setAdapter(spinnerAadapter);

        dbrw_bc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                return;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private List<String> getBc() {
        List<String> spinnerList = new ArrayList<String>();
        spinnerList.add("白班");
        spinnerList.add("晚班");
        return spinnerList;
    }


}
