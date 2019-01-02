package ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.project.dimine.ynhj.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import utils.StatusBarUtils;

public class Welcome extends AppCompatActivity {
    private long send_time = 2500;
    @BindView(R.id.skip)
    Button skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        skip.setVisibility(View.VISIBLE);
        countDownTimer.start();
        StatusBarUtils.transparencyBar(this);
    }


    private Handler handler = new Handler() {
        @Override
        public void dispatchMessage(Message msg) {
            super.dispatchMessage(msg);
            switch (msg.what) {
                case 0:
                    Intent intent = new Intent(Welcome.this, Login.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };

    @OnClick(R.id.skip)
    void Skip() {
        countDownTimer.cancel();
        startActivity(new Intent(Welcome.this, Login.class));
        finish();
    }

    //跳过欢迎页面
    private CountDownTimer countDownTimer = new CountDownTimer(4000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            skip.setText("跳过(" + millisUntilFinished / 1000 + "s)");
        }

        @Override
        public void onFinish() {
            skip.setText("跳过(" + 0 + "s)");
            startActivity(new Intent(Welcome.this, Login.class));
            finish();
        }
    };

    /**
     * 防止用户返回键退出APP
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME || super.onKeyDown(keyCode, event);
    }
}
