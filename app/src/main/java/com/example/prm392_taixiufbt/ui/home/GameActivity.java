package com.example.prm392_taixiufbt.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm392_taixiufbt.R;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    private int playerMoney = 1000;
    private EditText etBetAmount;
    private TextView tvMoney, tvResult, tvCountdown, tvDice1, tvDice2, tvDice3, tvTaiBetAmount, tvXiuBetAmount;
    private Button btnTai, btnXiu, btnCancelBet;
    private Random random;
    private Handler handler;
    private Runnable gameRunnable;
    private int countdownTime = 30;  // 30 seconds countdown
    private boolean canBet = true;
    private boolean betPlaced = false;
    private boolean isTaiBet = false;
    private final int MIN_BET_AMOUNT = 2;
    private int betAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        countdownTime = getIntent().getIntExtra("countdownTime", 30);
        etBetAmount = findViewById(R.id.etBetAmount);
        tvMoney = findViewById(R.id.tvMoney);
        tvResult = findViewById(R.id.tvResult);
        tvCountdown = findViewById(R.id.tvCountdown);  // Ensure this matches the ID in XML
        btnTai = findViewById(R.id.btnTai);
        btnXiu = findViewById(R.id.btnXiu);
        btnCancelBet = findViewById(R.id.btnCancelBet);
        tvDice1 = findViewById(R.id.tvDice1);
        tvDice2 = findViewById(R.id.tvDice2);
        tvDice3 = findViewById(R.id.tvDice3);
        tvTaiBetAmount = findViewById(R.id.tvTaiBetAmount);
        tvXiuBetAmount = findViewById(R.id.tvXiuBetAmount);
        random = new Random();
        handler = new Handler();

        updateMoneyText();

        btnTai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canBet) {
                    placeBet(true);
                } else {
                    Toast.makeText(GameActivity.this, "Không thể cược trong thời gian này.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnXiu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canBet) {
                    placeBet(false);
                } else {
                    Toast.makeText(GameActivity.this, "Không thể cược trong thời gian này.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancelBet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelBet();
            }
        });

        startGameLoop();
    }

    private void placeBet(boolean isTai) {
        String betAmountStr = etBetAmount.getText().toString();
        if (betAmountStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số tiền cược.", Toast.LENGTH_SHORT).show();
            return;
        }

        betAmount = Integer.parseInt(betAmountStr);

        if (betAmount < MIN_BET_AMOUNT) {
            Toast.makeText(this, "Số tiền cược tối thiểu là 2$.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (betAmount > playerMoney) {
            Toast.makeText(this, "Bạn không có đủ tiền để cược.", Toast.LENGTH_SHORT).show();
            return;
        }

        isTaiBet = isTai;
        betPlaced = true;
        if (isTai) {
            tvTaiBetAmount.setText("Cược Tài: " + betAmount + "$");
            tvXiuBetAmount.setText("");
        } else {
            tvXiuBetAmount.setText("Cược Xỉu: " + betAmount + "$");
            tvTaiBetAmount.setText("");
        }
        Toast.makeText(this, "Bạn đã đặt cược thành công!", Toast.LENGTH_SHORT).show();
    }

    private void cancelBet() {
        if (betPlaced && canBet) {
            betPlaced = false;
            betAmount = 0;
            isTaiBet = false;
            tvXiuBetAmount.setText("");
            tvTaiBetAmount.setText("");
            Toast.makeText(this, "Bạn đã hủy cược thành công!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Không thể hủy cược trong thời gian này.", Toast.LENGTH_SHORT).show();
        }
    }

    private void startGameLoop() {
        gameRunnable = new Runnable() {
            @Override
            public void run() {
                if (countdownTime > 0) {
                    countdownTime--;
                    if (countdownTime <= 5) {
                        canBet = false;
                    }
                    tvCountdown.setText("Thời gian còn lại: " + countdownTime + "s");
                    handler.postDelayed(this, 1000);
                } else {
                    playGame();
                    countdownTime = 30;
                    canBet = true;
                    handler.postDelayed(this, 1000);
                }
            }
        };
        handler.post(gameRunnable);
    }

    private void playGame() {
        if (betPlaced) {
            int dice1 = random.nextInt(6) + 1;
            int dice2 = random.nextInt(6) + 1;
            int dice3 = random.nextInt(6) + 1;
            int total = dice1 + dice2 + dice3;
            tvDice1.setText("Xúc xắc 1: " + dice1);
            tvDice2.setText("Xúc xắc 2: " + dice2);
            tvDice3.setText("Xúc xắc 3: " + dice3);
            boolean isWin = (total >= 11 && isTaiBet) || (total <= 10 && !isTaiBet);

            if (isWin) {
                playerMoney += betAmount;
                tvResult.setText("Kết quả: " + total + " - Bạn đã thắng!");
            } else {
                playerMoney -= betAmount;
                tvResult.setText("Kết quả: " + total + " - Bạn đã thua.");
            }

            updateMoneyText();
            betPlaced = false;
            betAmount = 0;
            tvTaiBetAmount.setText("");
            tvXiuBetAmount.setText("");
        } else {
            tvResult.setText("Không có cược nào được đặt.");
        }
    }

    private void updateMoneyText() {
        tvMoney.setText("Số tiền còn lại: " + playerMoney + "$");
    }
}