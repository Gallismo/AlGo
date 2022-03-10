package com.example.algo.custom;


import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class MoneyTextWatcher implements TextWatcher {
    private final WeakReference<TextInput> editTextWeakReference;

    public MoneyTextWatcher(TextInput editText) {
        editTextWeakReference = new WeakReference<>(editText);
//        numberFormat.setMaximumFractionDigits(0);
//        numberFormat.setRoundingMode(RoundingMode.FLOOR);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        EditText editText = editTextWeakReference.get();
        if (editText == null || editText.getText().toString().equals("")) {
            return;
        }
        String text = editText.getText().toString();

        if ( text.substring(text.length() - 1).equals(".") ) {
            return;
        }

        editText.removeTextChangedListener(this);

        DecimalFormat formatter = new DecimalFormat("###,###.##");
        formatter.setRoundingMode(RoundingMode.DOWN);

        String value = text.replaceAll(" ", "");
        double doubleValue = Double.parseDouble(value);
        String formatted = formatter.format(doubleValue);
        formatted = formatted.replaceAll(",", " ");


        editText.setText(formatted);
        editText.setSelection(formatted.length());
        editText.addTextChangedListener(this);
    }
}
