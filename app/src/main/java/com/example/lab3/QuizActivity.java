package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.lab3.model.Lap3Response;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuizActivity extends AppCompatActivity {
    private TextView tvBack;
    private TextView tvTitle;
    private TextView tvNext;
    private TextView tvQuiz;
    private CheckBox ck1;
    private TextView tvAnswer1;
    private CheckBox ck2;
    private TextView tvAnswer2;
    private CheckBox ck3;
    private TextView tvAnswer3;
    private CheckBox ck4;
    private TextView tvAnswer4;
    private Button result;
    private int nb_next = 2;
    private int nb_back = 1;
    private int position = 0;
    private String number_next;
    private String number_back;
    private boolean bl_1 = false;
    private boolean bl_2 = false;
    private boolean bl_3 = false;
    private boolean bl_4 = false;
    private String txt_result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        init();
        setstart();
    }

    private void setstart() {
        if (Common.TYPE == 1) {
            tvTitle.setText("Sport");
        } else {
            tvTitle.setText("Maths");
        }
//

        tvBack.setText(number_back);
        tvNext.setText(number_next);

        getdata();
    }

    private void getdata() {
        ApiUlti.getService().getlap3().enqueue(new Callback<Lap3Response>() {
            @Override
            public void onResponse(Call<Lap3Response> call, Response<Lap3Response> response) {
                if (response.isSuccessful()) {
                    Lap3Response lap3Response = response.body();
                    if (Common.TYPE == 1) {
                        Common.SIZE = lap3Response.getQuiz().getSport().size();
                        tvQuiz.setText(lap3Response.getQuiz().getSport().get(position).getQuestion());
                        tvAnswer1.setText(lap3Response.getQuiz().getSport().get(position).getOptions().get(0));
                        tvAnswer2.setText(lap3Response.getQuiz().getSport().get(position).getOptions().get(1));
                        tvAnswer3.setText(lap3Response.getQuiz().getSport().get(position).getOptions().get(2));
                        tvAnswer4.setText(lap3Response.getQuiz().getSport().get(position).getOptions().get(3));


                    } else {
                        Common.SIZE = lap3Response.getQuiz().getMaths().size();
                        tvQuiz.setText(lap3Response.getQuiz().getMaths().get(position).getQuestion());
                        tvAnswer1.setText(lap3Response.getQuiz().getMaths().get(position).getOptions().get(0));
                        tvAnswer2.setText(lap3Response.getQuiz().getMaths().get(position).getOptions().get(1));
                        tvAnswer3.setText(lap3Response.getQuiz().getMaths().get(position).getOptions().get(2));
                        tvAnswer4.setText(lap3Response.getQuiz().getMaths().get(position).getOptions().get(3));
                    }
                }
            }

            @Override
            public void onFailure(Call<Lap3Response> call, Throwable t) {
                t.getMessage();
            }
        });
    }

    private void init() {
        tvBack = findViewById(R.id.tv_back);
        tvTitle = findViewById(R.id.tv_title);
        tvQuiz = findViewById(R.id.tv_quiz);
        ck1 = findViewById(R.id.ck_1);
        tvAnswer1 = findViewById(R.id.tv_answer1);
        ck2 = findViewById(R.id.ck_2);
        tvAnswer2 = findViewById(R.id.tv_answer2);
        ck3 = findViewById(R.id.ck_3);
        tvAnswer3 = findViewById(R.id.tv_answer3);
        ck4 = findViewById(R.id.ck_4);
        tvAnswer4 = findViewById(R.id.tv_answer4);
        result = findViewById(R.id.result);
        tvNext = findViewById(R.id.tv_next);
        number_next = String.valueOf(nb_next);
        number_back = String.valueOf(nb_back);
    }

    public void result(View view) {

        ApiUlti.getService().getlap3().enqueue(new Callback<Lap3Response>() {
            @Override
            public void onResponse(Call<Lap3Response> call, Response<Lap3Response> response) {
                if (response.isSuccessful()) {
                    Lap3Response lap3Response = response.body();
                    if (Common.TYPE == 1) {
                        if (txt_result.equals(lap3Response.getQuiz().getSport().get(position).getAnswer())) {
                            Common.RESULT++;
                            Log.e("Tag", "a" + Common.RESULT);
                        }
                    } else {
                        if (txt_result.equals(lap3Response.getQuiz().getMaths().get(position).getAnswer())) {
                            Common.RESULT++;
                            Log.e("Tag", "b" + Common.RESULT);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Lap3Response> call, Throwable t) {
                t.getMessage();
            }
        });

        Log.e("Tag", "c" + Common.RESULT);


        if (position == Common.SIZE - 1) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.show();

            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    startActivity(new Intent(QuizActivity.this, ResultActivity.class));
                    finish();
                    progressDialog.dismiss();
                }
            }, 3000);
        }

        if (position < Common.SIZE - 1) {
            position++;
            getdata();

            bl_1 = false;
            bl_2 = false;
            bl_3 = false;
            bl_4 = false;

            ck1.setChecked(bl_1);
            ck2.setChecked(bl_2);
            ck3.setChecked(bl_3);
            ck4.setChecked(bl_4);

            if (position == Common.SIZE - 1) {
                result.setText("Result");
            }
        }

    }

    public void back(View view) {
//        position--;
//        getdata();
    }

    public void next(View view) {
//        position++;
//        getdata();
    }

    public void answar1(View view) {
        bl_1 = true;
        bl_2 = false;
        bl_3 = false;
        bl_4 = false;

        ck1.setChecked(bl_1);
        ck2.setChecked(bl_2);
        ck3.setChecked(bl_3);
        ck4.setChecked(bl_4);

        txt_result = tvAnswer1.getText().toString().trim();
    }

    public void answar2(View view) {

        bl_1 = false;
        bl_2 = true;
        bl_3 = false;
        bl_4 = false;

        ck1.setChecked(bl_1);
        ck2.setChecked(bl_2);
        ck3.setChecked(bl_3);
        ck4.setChecked(bl_4);
        txt_result = tvAnswer2.getText().toString().trim();

    }

    public void answar3(View view) {

        bl_1 = false;
        bl_2 = false;
        bl_3 = true;
        bl_4 = false;

        ck1.setChecked(bl_1);
        ck2.setChecked(bl_2);
        ck3.setChecked(bl_3);
        ck4.setChecked(bl_4);
        txt_result = tvAnswer3.getText().toString().trim();

    }

    public void answar4(View view) {

        bl_1 = false;
        bl_2 = false;
        bl_3 = false;
        bl_4 = true;

        ck1.setChecked(bl_1);
        ck2.setChecked(bl_2);
        ck3.setChecked(bl_3);
        ck4.setChecked(bl_4);
        txt_result = tvAnswer4.getText().toString().trim();

    }

    public void setTvBack(TextView tvBack) {
        this.tvBack = tvBack;
    }

    public void setTvTitle(TextView tvTitle) {
        this.tvTitle = tvTitle;
    }

    public void setTvNext(TextView tvNext) {
        this.tvNext = tvNext;
    }
}
