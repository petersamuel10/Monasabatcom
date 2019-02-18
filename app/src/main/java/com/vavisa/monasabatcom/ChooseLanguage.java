package com.vavisa.monasabatcom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

public class ChooseLanguage extends AppCompatActivity {

    RadioButton lanRB;
    @BindView(R.id.lanGroup)
    RadioGroup radioGroup;
    @OnClick(R.id.lanBtn)
    public void setLanguage() { getLanguage(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);
        ButterKnife.bind(this);
        Paper.init(this);

    }

    private void getLanguage() {
        int lanId = radioGroup.getCheckedRadioButtonId();
        lanRB  = findViewById(lanId);
        if(lanRB.getText().equals("English"))
            Paper.book("Monasabatcom").write("language","en");
        else
            Paper.book("Monasabatcom").write("language","ar");

        startActivity(new Intent(ChooseLanguage.this,MainActivity.class));
        finish();

    }

}
