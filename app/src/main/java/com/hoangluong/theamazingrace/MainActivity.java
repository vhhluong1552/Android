package com.hoangluong.theamazingrace;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    CheckBox cbOne, cbTwo, cbThree;
    SeekBar sbOne, sbTwo, sbThree;
    TextView tvScore;
    ImageButton imgButtonPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        DisableSeekBar();
        //1s = 1000 ms
        CountDownTimer countDownTimer = new CountDownTimer(60000, 300) {
           
            @Override

            public void onTick(long millisUntilFinished) {
                // check each seekbar animal higher than max = 100
                // check WIN

                ArrayList<CheckBox> arrCheckBox = new ArrayList<>();
                arrCheckBox.add(cbOne);
                arrCheckBox.add(cbTwo);
                arrCheckBox.add(cbThree);
                ArrayList<SeekBar> arrAnimal = new ArrayList<>();
                arrAnimal.add(sbOne);
                arrAnimal.add(sbTwo);
                arrAnimal.add(sbThree);
                checkAnimalWin(arrAnimal, arrCheckBox);
                initRandom();
            }

            @SuppressLint("SetTextI18n")
            private void checkAnimalWin(ArrayList<SeekBar> arrAnimal, ArrayList<CheckBox> arrCheckBox) {
                int ans = Integer.parseInt((String) tvScore.getText());
                for(int i=0; i<3;i++){
                   if(arrAnimal.get(i).getProgress()>= arrAnimal.get(i).getMax()){
                       this.cancel();
                       EnableCheckBox();
                       imgButtonPlay.setVisibility(View.VISIBLE);
                       String res= "Animal " + (i+1)+ " win";
                       Toast.makeText(MainActivity.this, res, Toast.LENGTH_SHORT).show();
                       if(arrCheckBox.get(i).isChecked()){
                           ans += 10;
                           Toast.makeText(MainActivity.this, "VICTORY", Toast.LENGTH_SHORT).show();

                       }
                       else{
                           ans -= 10;
                           Toast.makeText(MainActivity.this, "DEFEAT", Toast.LENGTH_SHORT).show();
                       }
                       tvScore.setText(ans +"");
                       arrCheckBox.get(0).setChecked(false);
                       arrCheckBox.get(1).setChecked(false);
                       arrCheckBox.get(2).setChecked(false);
                   }

               }
            }

            @Override
            public void onFinish() {

            }
        };
        // nhấn vào play thì mới chạy. phải gọi countDownTimer.start() mới chạy được

        setCheckBok();

        imgButtonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbOne.isChecked() || cbTwo.isChecked() || cbThree.isChecked()){
                    imgButtonPlay.setVisibility(View.INVISIBLE);
                    // trước khi cho nó chạy, set lại cho nó về 0 hết
                    sbOne.setProgress(0);
                    sbTwo.setProgress(0);
                    sbThree.setProgress(0);
                    countDownTimer.start();
                    DisableCheckBox();
                }
                else{
                    Toast.makeText(MainActivity.this, "Please bet", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void setCheckBok() {
        cbOne.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cbTwo.setChecked(false);
                    cbThree.setChecked(false);
                }
            }
        });

        cbTwo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cbOne.setChecked(false);
                    cbThree.setChecked(false);
                }
            }
        });

        cbThree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cbTwo.setChecked(false);
                    cbOne.setChecked(false);
                }
            }
        });
    }

    // random bước nhảy của mỗi con vật
    private void initRandom() {
        int number = 4;
        Random random =new Random();
        int one = random.nextInt(number);
        int two = random.nextInt(number);
        int three = random.nextInt(number);
        sbOne.setProgress(sbOne.getProgress() + one );
        sbTwo.setProgress(sbTwo.getProgress() + two );
        sbThree.setProgress(sbThree.getProgress() + three );
    }

    private void init() {
        cbOne =(CheckBox) findViewById(R.id.checkBoxOne);
        cbTwo =(CheckBox) findViewById(R.id.checkBoxTwo);
        cbThree =(CheckBox) findViewById(R.id.checkBoxThree);
        sbOne = (SeekBar) findViewById(R.id.seekBarOne);
        sbTwo= (SeekBar) findViewById(R.id.seekBarTwo);
        sbThree = (SeekBar) findViewById(R.id.seekBarThree);
        tvScore = (TextView) findViewById(R.id.textViewScore);
        imgButtonPlay = (ImageButton) findViewById(R.id.imgButtonPlay);
    }

    // nếu play game thì không được thay đổi lựa chọn
    private void EnableCheckBox(){
        cbOne.setEnabled(true);
        cbTwo.setEnabled(true);
        cbThree.setEnabled(true);

    }

    private void DisableCheckBox(){
        cbOne.setEnabled(false);
        cbTwo.setEnabled(false);
        cbThree.setEnabled(false);

    }

    private void DisableSeekBar() {
        sbOne.setEnabled(false);
        sbTwo.setEnabled(false);
        sbThree.setEnabled(false);

    }

}