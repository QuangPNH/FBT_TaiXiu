package com.example.prm392_taixiufbt.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.prm392_taixiufbt.DAO.FBTTaiXiuDatabase;
import com.example.prm392_taixiufbt.LoginActivity;
import com.example.prm392_taixiufbt.R;
import com.example.prm392_taixiufbt.models.GameProfile;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameActivity extends AppCompatActivity {
    private String thisUsername;
    private int playerMoney = 1000;
    private EditText etBetAmount;
    private TextView tvMoney, tvResult, tvCountdown, tvDice1, tvDice2, tvDice3, tvTaiBetAmount, tvXiuBetAmount;
    private Button btnTai, btnXiu, btnCancelBet;
    private Random random;
    private Handler handler;
    private Runnable gameRunnable;
    public int countdownTime = 30;
    public int currentCountdown=0;
    private boolean canBet = true;
    private boolean betPlaced = false;
    private boolean isTaiBet = false;
    private final int MIN_BET_AMOUNT = 2;
    private int betAmount = 0;
    private GameProfile gameProfile;
    boolean[] actualResults = new boolean[10];
    private int actualResultsIndex = 0;

    private void loadActualResults(){
        actualResults[0] = gameProfile.isRoll1Rate();
        actualResults[1] = gameProfile.isRoll2Rate();
        actualResults[2] = gameProfile.isRoll3Rate();
        actualResults[3] = gameProfile.isRoll4Rate();
        actualResults[4] = gameProfile.isRoll5Rate();
        actualResults[5] = gameProfile.isRoll6Rate();
        actualResults[6] = gameProfile.isRoll7Rate();
        actualResults[7] = gameProfile.isRoll8Rate();
        actualResults[8] = gameProfile.isRoll9Rate();
        actualResults[9] = gameProfile.isRoll10Rate();

    }

    private String getCurrentUsername() {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("Username", null); // Returns null if "Username" doesn't exist
    }
    private void fetchUserMoneyAndUpdateUI(String username) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());


        executor.execute(() -> {
            // Assuming you have a method getDatabase() that returns the instance of your Room database
            FBTTaiXiuDatabase db = FBTTaiXiuDatabase.getDatabase(getApplicationContext());
            int money = db.userDao().getUserMoney(username);

            handler.post(() -> {
                // Update the UI thread with the fetched money
                playerMoney = money;
                updateMoneyText();
            });
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Extract GameProfile from Intent
        gameProfile = (GameProfile) getIntent().getSerializableExtra("currentGameProfile");
        if (gameProfile != null) {
            // Now that gameProfile is not null, it's safe to call loadActualResults
            loadActualResults();
        } else {
            // Handle case where GameProfile is not passed or found
            // Consider finishing the activity or showing an error message
        }
        setContentView(R.layout.activity_game);
        String currentUsername = getCurrentUsername();
        thisUsername = currentUsername;
        if (currentUsername != null) {
            fetchUserMoneyAndUpdateUI(currentUsername);
        } else {
            if (currentUsername == null) {
                Intent intent = new Intent(GameActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Close GameActivity
            }
        }
        countdownTime = getIntent().getIntExtra("countdownTime", 30);
        currentCountdown = countdownTime;
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
                    playGame(actualResults[actualResultsIndex]);
                    if (actualResultsIndex == 9){
                        actualResultsIndex = 0;
                    }else {
                        actualResultsIndex++;
                    }
                    countdownTime = currentCountdown;
                    canBet = true;
                    handler.postDelayed(this, 5000);
                }
            }
        };
        handler.post(gameRunnable);
    }


    private int[] taiToXiu(int dice1,int dice2,int dice3, int total){
        int[] result = new int[4];
        if(total >= 11){
            int diff = total - 10;
            int max = total -3;
            int numberToLost = random.ints(diff, max).findFirst().getAsInt();
            int newTotal = total - numberToLost;
            int firstNumber = random.nextInt(Math.min(6, newTotal - 2)) + 1;
            int secondNumber = random.nextInt(Math.min(6, newTotal - firstNumber - 1)) + 1;
            int thirdNumber = total - firstNumber - secondNumber;
            return new int[]{firstNumber,secondNumber,thirdNumber,newTotal};
        }
        return new int[]{dice1,dice2,dice3,total};
    }

    private int[] xiuToTai(int dice1,int dice2,int dice3, int total){
        int[] result = new int[4];
        if(total <= 10){
            int diff = 11 - total;
            int max = 18- total;
            int numberToAdd = random.ints(diff, max).findFirst().getAsInt();
            int newTotal = total + numberToAdd;
            int firstNumber = random.nextInt(Math.min(6, newTotal - 2)) + 1;
            int secondNumber = random.nextInt(Math.min(6, newTotal - firstNumber - 1)) + 1;
            int thirdNumber = total - firstNumber - secondNumber;
            return new int[]{firstNumber,secondNumber,thirdNumber,newTotal};
        }
        return new int[]{dice1,dice2,dice3,total};
    }
    private void playGame(boolean results) {
        if (betPlaced) {
            int dice1 = random.nextInt(6) + 1;
            int dice2 = random.nextInt(6) + 1;
            int dice3 = random.nextInt(6) + 1;
            int total = dice1 + dice2 + dice3;

            if(results == true){
                int[] newResult = taiToXiu(dice1,dice2,dice3,total);
                dice1 = newResult[0];
                dice2 = newResult[1];
                dice3 = newResult[2];
                total = newResult[3];
            }else {
                int[] newResult = xiuToTai(dice1,dice2,dice3,total);
                dice1 = newResult[0];
                dice2 = newResult[1];
                dice3 = newResult[2];
                total = newResult[3];
            }

            tvDice1.setText("Xúc xắc 1: " + dice1);
            tvDice2.setText("Xúc xắc 2: " + dice2);
            tvDice3.setText("Xúc xắc 3: " + dice3);
            boolean isWin = (total >= 11 && isTaiBet) || (total <= 10 && !isTaiBet);

            if (isWin) {
                playerMoney += betAmount;
                updateUserMoney(thisUsername, playerMoney);
                tvResult.setText("Kết quả: " + total + " - Bạn đã thắng!");
            } else {
                playerMoney -= betAmount;
                updateUserMoney(thisUsername, playerMoney);
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
    private void updateUserMoney(String username, int newMoney) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            // Get the database instance
            FBTTaiXiuDatabase db = FBTTaiXiuDatabase.getDatabase(getApplicationContext());
            // Update the user's money
            db.userDao().updateUserMoney(username, newMoney);

            handler.post(() -> {
                // Optionally, update the UI or notify the user of the update
                Toast.makeText(GameActivity.this, "Money updated successfully", Toast.LENGTH_SHORT).show();
                // Update the displayed money amount
                updateMoneyText();
            });
        });
    }
    private void updateMoneyText() {
        tvMoney.setText("Số tiền còn lại: " + playerMoney + "$");
    }
}