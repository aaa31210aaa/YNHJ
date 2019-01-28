package ui.workbench;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.widget.TextView;

import com.project.dimine.ynhj.R;

import butterknife.BindView;
import butterknife.OnClick;
import utils.BaseActivity;

public class ChooseDestination extends BaseActivity {
    @BindView(R.id.title_name)
    TextView title_name;
    @BindView(R.id.cardview_2100txc)
    CardView cardview_2100txc;
    @BindView(R.id.cardview_3000txc)
    CardView cardview_3000txc;
    @BindView(R.id.cardview_4000txc)
    CardView cardview_4000txc;
    @BindView(R.id.cardview_lhkd2g)
    CardView cardview_lhkd2g;
    @BindView(R.id.cardview_lhkd2d)
    CardView cardview_lhkd2d;
    @BindView(R.id.cardview_lhkd3)
    CardView cardview_lhkd3;
    @BindView(R.id.cardview_qkd)
    CardView cardview_qkd;
    @BindView(R.id.cardview_gxcbsd)
    CardView cardview_gxcbsd;
    public static Activity instance;

    @Override
    protected void initView() {
        instance = this;
    }

    @Override
    protected void initData() {
        title_name.setText(R.string.choose_destination_title);
    }

    @Override
    protected int getPageLayoutID() {
        return R.layout.activity_choose_destination;
    }

    @OnClick(R.id.back)
    void Back() {
        finish();
    }


    @OnClick(R.id.cardview_2100txc)
    void Card1() {
        Intent intent = new Intent(this, FillInMineralTickets.class);
        startActivity(intent);
    }

    @OnClick(R.id.cardview_3000txc)
    void Card2() {
        Intent intent = new Intent(this, FillInMineralTickets.class);
        startActivity(intent);
    }

    @OnClick(R.id.cardview_4000txc)
    void Card3() {
        Intent intent = new Intent(this, FillInMineralTickets.class);
        startActivity(intent);
    }

    @OnClick(R.id.cardview_lhkd2g)
    void Card4() {
        Intent intent = new Intent(this, FillInMineralTickets.class);
        startActivity(intent);
    }

    @OnClick(R.id.cardview_lhkd2d)
    void Card5() {
        Intent intent = new Intent(this, FillInMineralTickets.class);
        startActivity(intent);
    }

    @OnClick(R.id.cardview_lhkd3)
    void Card6() {
        Intent intent = new Intent(this, FillInMineralTickets.class);
        startActivity(intent);
    }

    @OnClick(R.id.cardview_qkd)
    void Card7() {
        Intent intent = new Intent(this, FillInMineralTickets.class);
        startActivity(intent);
    }

    @OnClick(R.id.cardview_gxcbsd)
    void Card8() {
        Intent intent = new Intent(this, FillInMineralTickets.class);
        startActivity(intent);
    }
}
