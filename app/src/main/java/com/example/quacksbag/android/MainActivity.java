package com.example.quacksbag.android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quacksbag.baserules.BagDrawer;
import com.example.quacksbag.baserules.BagManager;
import com.example.quacksbag.R;
import com.example.quacksbag.baserules.ChipsStore;
import com.example.quacksbag.baserules.RoundBagManager;
import com.example.quacksbag.gamematerial.Chip;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private BagManager bagManager;
    private RoundBagManager currentRoundBagManager;
    private BagDrawer bagDrawer;
    private ChipsStore chipsStore;

    private TextView tvBagCount;
    private TextView tvDrawnCount;
    private TextView tvChipValue; // the text inside the circle
    private View viewChipCircle;
    private Button btnDraw;
    private Button btnReturnLast;
    private Button btnAddChips;
    private Button btnNewRound;
    private Button btnReturnSelect;

    private Chip lastDrawn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bagManager = new BagManager();
        bagDrawer=new BagDrawer();
        chipsStore=new ChipsStore();
        tvBagCount = findViewById(R.id.tvBagCount);
        tvDrawnCount = findViewById(R.id.tvDrawnCount);
        tvChipValue = findViewById(R.id.tvChipValue);
        viewChipCircle = findViewById(R.id.viewChipCircle);
        btnDraw = findViewById(R.id.btnDraw);
        btnReturnLast = findViewById(R.id.btnReturnLast);
        btnAddChips = findViewById(R.id.btnAddChips);
        btnNewRound = findViewById(R.id.btnNewRound);
        btnReturnSelect = findViewById(R.id.btnReturnSelect);

        // initialize default + start a round
        currentRoundBagManager=bagManager.startNewRound();
        updateUI();

        btnDraw.setOnClickListener(v -> {
            Chip c = bagDrawer.drawRandomChipAndUpdateBag(currentRoundBagManager);
            if (c == null) {
                Toast.makeText(this, "Bag is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            lastDrawn = c;
            showChip(c);
            updateUI();
        });

        btnReturnLast.setOnClickListener(v -> {
            if (lastDrawn == null) {
                Toast.makeText(this, "No last drawn chip to return", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean ok = currentRoundBagManager.throwChipBackInBag(lastDrawn);
            if (ok) {
                lastDrawn = null;
                clearChipDisplay();
                updateUI();
            } else {
                Toast.makeText(this, "Chip could not be returned", Toast.LENGTH_SHORT).show();
            }
        });

        btnReturnSelect.setOnClickListener(v -> {
            // let user pick which drawn chip to return (if any)
            List<Chip> drawn = currentRoundBagManager.getDrawnChips();
            if (drawn.isEmpty()) {
                Toast.makeText(this, "No drawn chips", Toast.LENGTH_SHORT).show();
                return;
            }
            CharSequence[] items = new CharSequence[drawn.size()];
            for (int i = 0; i < drawn.size(); i++) items[i] = drawn.get(i).toString();

            new AlertDialog.Builder(this)
                    .setTitle("Return which drawn chip?")
                    .setItems(items, (dialog, which) -> {
                        Chip sel = drawn.get(which);
                        currentRoundBagManager.throwChipBackInBag(sel);
                        if (sel == lastDrawn) {
                            lastDrawn = null;
                            clearChipDisplay();
                        }
                        updateUI();
                    })
                    .show();
        });

        btnAddChips.setOnClickListener(v -> showAddChipDialog());

        btnNewRound.setOnClickListener(v -> {
            currentRoundBagManager=bagManager.startNewRound();
            lastDrawn = null;
            clearChipDisplay();
            updateUI();
            Toast.makeText(this, "New round started (bag refilled with defaults + purchased chips)", Toast.LENGTH_SHORT).show();
        });
    }

    private void showAddChipDialog() {
        // Present a list of available presets
        List<Chip> presets = chipsStore.getAllKindsOfChips();
        CharSequence[] names = new CharSequence[presets.size()];
        for (int i = 0; i < presets.size(); i++) names[i] = presets.get(i).toString();

        new AlertDialog.Builder(this)
                .setTitle("Select chip preset to add (purchase)")
                .setItems(names, (dialog, which) -> {
                    Chip chosen = presets.get(which);
                    // ask for quantity
                    final EditText input = new EditText(MainActivity.this);
                    input.setInputType(InputType.TYPE_CLASS_NUMBER);
                    input.setHint("Number to add (e.g. 1)");
                    new AlertDialog.Builder(MainActivity.this)
                            .setPositiveButton("Add", (d2, which2) -> {
                                String txt = input.getText().toString();

                                bagManager.purchaseChipPreset(chosen);
                                updateUI();
                                Toast.makeText(MainActivity.this,   " x " + chosen.toString(), Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                })
                .setNegativeButton("Close", null)
                .show();
    }

    private void showChip(Chip chip) {
        tvChipValue.setText(String.valueOf(chip.getValue()));
        // create an oval drawable with fill color and black stroke
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.OVAL);
        gd.setColor(ColorMapper.mapChipColorToHex(chip.getColor()));
        gd.setStroke(4, Color.BLACK);
        viewChipCircle.setBackground(gd);
        // set text color for readability
        int textColor = (isColorDark(ColorMapper.mapChipColorToHex(chip.getColor())) ? Color.WHITE : Color.BLACK);
        tvChipValue.setTextColor(textColor);
    }


    private void clearChipDisplay() {
        tvChipValue.setText("-");
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.OVAL);
        gd.setColor(Color.LTGRAY);
        gd.setStroke(4, Color.BLACK);
        viewChipCircle.setBackground(gd);
        tvChipValue.setTextColor(Color.BLACK);
    }

    private boolean isColorDark(int color) {
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return darkness >= 0.5;
    }

    private void updateUI() {
        tvBagCount.setText("Bag: " + currentRoundBagManager.currentNumberOfUndrawnChips());
        tvDrawnCount.setText("Drawn: " + currentRoundBagManager.currentNumberOfDrawnChips());
    }
}