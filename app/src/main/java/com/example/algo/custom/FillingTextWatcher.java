package com.example.algo.custom;

import android.app.Application;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import com.example.algo.R;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.ref.WeakReference;

public class FillingTextWatcher implements TextWatcher {
    public static final int MIN_TEXT_LENGTH = 1;
    public static final String EMPTY_STRING = "";
    private final WeakReference<TextInput> editTextWeakReference;
    private final WeakReference<TextInputLayout> layoutWeakReference;
    private final Application context;

    public FillingTextWatcher(TextInput editText, TextInputLayout layout, Application appContext) {
        editTextWeakReference = new WeakReference<>(editText);
        layoutWeakReference = new WeakReference<>(layout);
        context = appContext;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (shouldShowError(editTextWeakReference.get())) {
            showError(layoutWeakReference.get());
        } else {
            hideError(layoutWeakReference.get());
        }
    }

    private boolean shouldShowError(TextInput textInput) {
        int length = textInput.getText().length();
        return length < MIN_TEXT_LENGTH;
    }

    private void showError(TextInputLayout layout) {
        layout.setError(context.getString(R.string.error));
    }

    private void hideError(TextInputLayout layout) {
        layout.setError(EMPTY_STRING);
    }
}
