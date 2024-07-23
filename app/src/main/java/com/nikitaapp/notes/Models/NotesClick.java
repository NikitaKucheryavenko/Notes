package com.nikitaapp.notes.Models;

import androidx.cardview.widget.CardView;

import com.nikitaapp.notes.Models.Notes;

public interface NotesClick {
    void onClick(Notes notes, int position);

    void onLongClick(Notes notes, CardView cardView, int position);
}
