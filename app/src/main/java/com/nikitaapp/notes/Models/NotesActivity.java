package com.nikitaapp.notes.Models;

import static com.nikitaapp.notes.Models.PermissionUtils.verifyStoragePermissions;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nikitaapp.notes.Models.Notes;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.elevation.SurfaceColors;
import com.nikitaapp.notes.R;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;


public class NotesActivity extends AppCompatActivity {
    EditText editTextTitle, editTextNotes;
    TextView textViewDate;
    private Notes notes;
    String dateStr;
    private boolean isOldNote = false;

    private void initView() {
        textViewDate = findViewById(R.id.date);
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextNotes = findViewById(R.id.edit_text_notes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.in, R.anim.out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_note) {
            String title = editTextTitle.getText().toString();
            String description = editTextNotes.getText().toString();
            if (description.isEmpty()) {
                Toast.makeText(NotesActivity.this, getString(R.string.toAdd), Toast.LENGTH_SHORT).show();
                return false;
            }
            notes.setTitle(title);
            notes.setNotes(description);
            notes.setDate(dateStr);
            Intent intent = new Intent();
            intent.putExtra("note", notes);
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DynamicColors.applyToActivityIfAvailable(this);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(SurfaceColors.SURFACE_2.getColor(this));
        setContentView(R.layout.activity_notes);
        verifyStoragePermissions(this);
        initView();

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                finish();
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);

        isOldNote = true;
        notes = new Notes();
        if (null != getIntent().getSerializableExtra("old_note")) {
            notes = (Notes) getIntent().getSerializableExtra("old_note");
            editTextTitle.setText(Objects.requireNonNull(notes).getTitle());
            editTextNotes.setText(notes.getNotes());
            dateStr = notes.getDate();
            textViewDate.setText(getString(R.string.create) + dateStr);
        } else {
            isOldNote = false;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, yyyy-MM-dd hh:mm a");
            dateStr = formatter.format(LocalDateTime.now());
            textViewDate.setText(getString(R.string.create) + dateStr);
        }
    }
}
