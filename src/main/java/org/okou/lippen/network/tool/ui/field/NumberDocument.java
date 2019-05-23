package org.okou.lippen.network.tool.ui.field;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class NumberDocument extends PlainDocument {
    private BigInteger maxValue;
    private final Pattern INT_PATTERN = Pattern
            .compile("0|([1-9]\\d*)?");

    public NumberDocument(BigInteger maxValue) {
        this.maxValue = maxValue;
    }

    public NumberDocument(long maxValue) {
        this.maxValue = BigInteger.valueOf(maxValue);
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        Matcher matcher = INT_PATTERN.matcher(str);
        if (!matcher.matches()) {
            return;
        }
        String text1 = getText(0, getLength());
        if (text1.length() == 1 && text1.charAt(0) == '0') {
            return;
        }
        super.insertString(offs, str, a);
        String text2 = getText(0, getLength());
        BigInteger b = new BigInteger(text2);
        if (b.compareTo(maxValue) > 0) {
            remove(0, getLength());
            super.insertString(0, text1, a);
        }
    }
}
