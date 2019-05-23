package org.okou.lippen.network.tool.ui.field;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;

/**
 * IPV4地址输入框
 */
public class IPV4Field extends JTextField implements Serializable {
    private static final Border EMPTY_BORDER = BorderFactory
            .createEmptyBorder();
    private static final long serialVersionUID = -2754807884601930339L;

    private Dot[] dots;

    private JIPV4Field[] ipFields;
    private KeyAdapter KeyListener = new IPKeyAdapter();

    public IPV4Field() {
        this(null);
    }

    public IPV4Field(String ipAddress) {
        setLayout(new GridLayout(1, 4, 0, 0));
        setFocusable(false);
        createIPFields();
        setIpAddress(ipAddress);
    }

    @Override
    public void addFocusListener(FocusListener listener) {
        if (this.ipFields != null) {
            for (JIPV4Field field : this.ipFields) {
                field.addFocusListener(listener);
            }
        }
    }

    @Override
    public void removeFocusListener(FocusListener listener) {
        if (this.ipFields != null) {
            for (JIPV4Field field : this.ipFields) {
                field.removeFocusListener(listener);
            }
        }
    }

    @Override
    public void addInputMethodListener(InputMethodListener listener) {
        if (this.ipFields != null) {
            for (JIPV4Field field : this.ipFields) {
                field.addInputMethodListener(listener);
            }
        }
    }

    @Override
    public void removeInputMethodListener(InputMethodListener listener) {
        if (this.ipFields != null) {
            for (JIPV4Field field : this.ipFields) {
                field.removeInputMethodListener(listener);
            }
        }
    }

    @Override
    public void addCaretListener(CaretListener listener) {
        if (this.ipFields != null) {
            for (JIPV4Field field : this.ipFields) {
                field.addCaretListener(listener);
            }
        }
    }

    @Override
    public void removeCaretListener(CaretListener listener) {
        if (this.ipFields != null) {
            for (JIPV4Field field : this.ipFields) {
                field.removeCaretListener(listener);
            }
        }
    }

    public void addDocumentListener(DocumentListener listener) {
        if (this.ipFields != null) {
            for (JIPV4Field field : this.ipFields) {
                field.getDocument().addDocumentListener(listener);
            }
        }
    }

    public void removeDocumentListener(DocumentListener listener) {
        if (this.ipFields != null) {
            for (JIPV4Field field : this.ipFields) {
                field.getDocument().removeDocumentListener(listener);
            }
        }
    }

    /**
     * 是否输入完整。<br>
     * 如果有一个输入域为空，则认为没有输入完整
     */
    public boolean isInputAll() {
        if (this.ipFields != null) {
            for (JIPV4Field field : this.ipFields) {
                if (field.getText() == null || field.getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    private void createIPFields() {
        updatePrefersize();
        this.ipFields = new JIPV4Field[4];
        this.dots = new Dot[3];
        JPanel[] fieldPanes = new JPanel[4];

        for (int i = 0; i < 4; i++) {
            this.ipFields[i] = new JIPV4Field();
            fieldPanes[i] = new JPanel();

            this.ipFields[i].addKeyListener(this.KeyListener);
            fieldPanes[i].setOpaque(false);
            fieldPanes[i].setLayout(new BorderLayout());
            fieldPanes[i].add(this.ipFields[i], "Center");

            if (i != 3) {
                fieldPanes[i].add(dots[i] = new Dot(), "East");
            }

            add(fieldPanes[i]);
        }

        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                this.ipFields[i]
                        .addPropertyChangeListener(new KeyPressListener(null,
                                this.ipFields[(i + 1)]));
            } else if (i == 3) {
                this.ipFields[i]
                        .addPropertyChangeListener(new KeyPressListener(
                                this.ipFields[(i - 1)], null));
            } else {
                this.ipFields[i]
                        .addPropertyChangeListener(new KeyPressListener(
                                this.ipFields[(i - 1)], this.ipFields[(i + 1)]));
            }
        }
    }

    protected void updatePrefersize() {
        Dimension size = new Dimension(80, getFont().getSize() * 2);
        size.width = getFont().getSize() * 11;
        setPreferredSize(size);
    }

    public JTextField[] getFieldComponents() {
        return this.ipFields;
    }

    @Override
    public void requestFocus() {
        if (ipFields != null) {
            ipFields[0].requestFocus();
        }
    }

    public String getIpAddress() {
        StringBuilder ip = new StringBuilder();
        int emptyCount = 0;

        if (this.ipFields != null) {
            for (JIPV4Field field : this.ipFields) {
                String str = field.getText();

                if (str.isEmpty()) {
                    emptyCount++;
                }

                ip.append('.');
                ip.append(str);
            }

            ip.deleteCharAt(0);
        }

        return emptyCount == 4 ? "" : ip.toString();
    }

    @Override
    public String getText() {
        return getIpAddress();
    }

    @Override
    public void setCaretColor(Color color) {
        super.setCaretColor(color);

        if (this.ipFields != null) {
            for (JIPV4Field field : this.ipFields) {
                field.setCaretColor(color);
            }
        }
    }

    @Override
    public void setDisabledTextColor(Color color) {
        super.setDisabledTextColor(color);

        if (this.ipFields != null) {
            for (JIPV4Field field : this.ipFields) {
                field.setDisabledTextColor(color);

                if (field.isEnabled())
                    continue;
                field.repaint();
            }

        }

        if ((this.dots != null) && (!isEnabled())) {
            for (Dot dot : this.dots) {
                dot.setForeground(color);
            }
        }
    }

    @Override
    public void setEditable(boolean isEditable) {
        super.setEditable(isEditable);

        if (this.ipFields != null) {
            for (JIPV4Field field : this.ipFields) {
                field.setEditable(isEditable);
            }
        }
    }

    @Override
    public void setEnabled(boolean isEnable) {
        super.setEnabled(isEnable);

        if (this.ipFields != null) {
            for (JIPV4Field field : this.ipFields) {
                field.setEnabled(isEnable);
            }
        }

        if (this.dots != null) {
            for (Dot dot : this.dots) {
                dot.setForeground(isEnable ? getForeground()
                        : getDisabledTextColor());
            }
        }
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);

        if (this.ipFields != null) {
            for (JIPV4Field field : this.ipFields) {
                field.setFont(font);
            }
        }
        /*
         * if (this.dots != null) { for (Dot dot : this.dots) {
         * dot.setFont(font); } }
         */
    }

    @Override
    public void setForeground(Color color) {
        super.setForeground(color);

        if (this.ipFields != null) {
            for (JIPV4Field field : this.ipFields) {
                field.setForeground(color);
            }
        }

        if ((this.dots != null) && (isEnabled())) {
            for (Dot dot : this.dots) {
                dot.setForeground(color);
            }
        }
    }

    public void setIpAddress(String ipAddress) {
        int index;

        if ((ipAddress != null) && (!ipAddress.isEmpty())) {
            if (!Pattern
                    .matches(
                            "((2[0-4]\\d|25[0-5]|[01]?\\d?\\d)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d?\\d)",
                            ipAddress)) {
                throw new IllegalArgumentException("Invalid IP Address:"
                        + ipAddress);
            }

            String[] ipBit = ipAddress.split("\\.");
            index = 0;

            for (JIPV4Field field : this.ipFields) {
                field.setText(Integer.parseInt(ipBit[(index++)]) + "");
            }
        } else {
            for (JIPV4Field field : this.ipFields) {
                field.setText("");
            }
        }
    }

    /**
     * 已整数形式设置IP地址
     *
     * @param ip
     */
    public void setIpValue(int ip) {
        setIpAddress(toIPString(ip));
    }

    /**
     * 获取IP地址的整数
     *
     * @return
     */
    public int getIPValue() {
        int ip = 0;
        ip = (ip << 8) | ipFields[0].getIntValue();
        ip = (ip << 8) | ipFields[1].getIntValue();
        ip = (ip << 8) | ipFields[2].getIntValue();
        ip = (ip << 8) | ipFields[3].getIntValue();
        return ip;
    }

    /**
     * 清空IP地址
     */
    public void clearIPAddress() {
        if (this.ipFields != null) {
            for (JIPV4Field field : this.ipFields) {
                field.setText("");
            }
        }
    }

    public static String toIPString(int ip) {
        return ((ip >> 24) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "."
                + ((ip >> 8) & 0xFF) + "." + ((ip >> 0) & 0xFF);
    }

    @Override
    public void setSelectedTextColor(Color color) {
        super.setSelectedTextColor(color);

        if (this.ipFields != null) {
            for (JIPV4Field field : this.ipFields) {
                field.setSelectedTextColor(color);
            }
        }
    }

    @Override
    public void setSelectionColor(Color color) {
        super.setSelectionColor(color);

        if (this.ipFields != null) {
            for (JIPV4Field field : this.ipFields) {
                field.setSelectionColor(color);
            }
        }
    }

    @Override
    public void setText(String text) {
        setIpAddress(text);
    }

    @Override
    public void updateUI() {
        Component[] children = getComponents();

        for (Component child : children) {
            remove(child);
        }

        super.updateUI();

        for (Component child : children) {
            add(child);
        }

        if (getCaret() != null) {
            getCaret().deinstall(this);
        }
    }

    private class Dot extends JLabel {
        private static final long serialVersionUID = -2704811830155290868L;

        public Dot() {
            super();
            setText(".");
            setOpaque(false);
            setIconTextGap(0);
            setFont(new Font(Font.DIALOG, Font.BOLD, 12));
            setBorder(IPV4Field.EMPTY_BORDER);
            setForeground(IPV4Field.this.getForeground());
            setEnabled(IPV4Field.this.isEnabled());
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public void setEnabled(boolean enabled) {
            super.setEnabled(enabled);
            setForeground(enabled ? IPV4Field.this.getForeground()
                    : IPV4Field.this.getDisabledTextColor());
        }

        @Override
        public void updateUI() {
            super.updateUI();
            setFont(IPV4Field.this.getFont());
        }
    }

    private class JIPV4Field extends JTextField implements ActionListener,
            FocusListener, Serializable {
        private static final long serialVersionUID = 1411564647463716520L;
        private boolean selectAll;

        public JIPV4Field() {
            this.selectAll = true;
            setHorizontalAlignment(0);
            setBorder(IPV4Field.EMPTY_BORDER);
            setOpaque(false);
            setMargin(new Insets(0, 0, 0, 0));
            setDocument(new IPBlockDocument(this));
            setFont(IPV4Field.this.getFont());
            setSelectionColor(IPV4Field.this.getSelectionColor());
            setSelectedTextColor(IPV4Field.this.getSelectedTextColor());
            setEditable(IPV4Field.this.isEditable());
            setDisabledTextColor(IPV4Field.this.getDisabledTextColor());
            setCaretColor(IPV4Field.this.getCaretColor());
            setForeground(IPV4Field.this.getForeground());
            setEnabled(IPV4Field.this.isEnabled());
            addActionListener(this);
            addFocusListener(this);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            transferFocus();
        }

        @Override
        public void focusGained(FocusEvent e) {
            if (this.selectAll) {
                selectAll();
            } else {
                this.selectAll = true;
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
        }

        public void unSelectAllWhenFocusGained() {
            if (!isFocusOwner()) {
                this.selectAll = false;
            }
        }

        /**
         * 获取值
         *
         * @return
         */
        public int getIntValue() {
            String value = this.getText();
            if (value == null || value.isEmpty()) {
                return 0;
            }
            return Integer.parseInt(value);
        }

        private class IPBlockDocument extends PlainDocument {
            private static final long serialVersionUID = -2645957214215338331L;
            private final Pattern INT_PATTERN = Pattern
                    .compile("0|([1-9]\\d*)?");

            private int ipBlockInt;
            private int length;
            private Matcher matcher;
            private int newLength;
            private int oldLength;
            private StringBuilder text = new StringBuilder();
            private JTextField textField;

            private IPBlockDocument(JTextField textField) {
                this.textField = textField;
            }

            @Override
            public void insertString(int offset, String input, AttributeSet a)
                    throws BadLocationException {
                this.matcher = INT_PATTERN.matcher(input);
                if (!this.matcher.matches()) {
                    return;
                }
                this.oldLength = getLength();
                this.newLength = input.length();
                this.length = (this.oldLength + this.newLength);
                this.text.delete(0, this.text.length());// clear
                this.text.append(getText(0, this.oldLength));// add old
                this.text.insert(offset, input);// replace or add
                if ((this.length > 3)) {

                    return;
                }

                this.ipBlockInt = (this.text.length() == 0 ? 0 : Integer
                        .parseInt(this.text.toString()));

                if (this.ipBlockInt > 255) {
                    return;
                }

                super.insertString(offset, input, a);
                if (this.length == 3) {
                    textField.firePropertyChange("Right", 0, 1);
                }
                if (this.text.length() == 2
                        && Integer.parseInt(this.text.toString()) > 25) {
                    textField.firePropertyChange("Right", 0, 1);
                }
            }
        }

    }

    private class KeyPressListener implements PropertyChangeListener {
        private IPV4Field.JIPV4Field leftField;
        private IPV4Field.JIPV4Field rightField;

        public KeyPressListener(IPV4Field.JIPV4Field leftField,
                                IPV4Field.JIPV4Field rightField) {
            this.leftField = leftField;
            this.rightField = rightField;
        }

        public void propertyChange(PropertyChangeEvent evt) {
            String name = evt.getPropertyName();

            if ((name == "Left") && (this.leftField != null)) {
                this.leftField.requestFocus();
            } else if ((name == "Right") && (this.rightField != null)) {
                this.rightField.requestFocus();
            } else if ((name == "BackSpace") && (this.leftField != null)) {
                this.leftField.unSelectAllWhenFocusGained();
                this.leftField.requestFocus();
                this.leftField.setCaretPosition(this.leftField.getText()
                        .length());
            } else if ((name == "Delete") && (this.rightField != null)) {
                this.rightField.unSelectAllWhenFocusGained();
                this.rightField.requestFocus();
                this.rightField.setCaretPosition(0);
            }
        }
    }

    class IPKeyAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            JTextComponent field = (JTextComponent) e.getComponent();
            int keyCode = e.getKeyCode();
            char keyChar = e.getKeyChar();
            String text = field.getText();
            String selText = field.getSelectedText();
            int caretPos = field.getCaretPosition();
            int textLength = text.length();
            if ((keyCode == KeyEvent.VK_LEFT) && (caretPos == 0)
                    && (selText == null)) {
                field.firePropertyChange("Left", 0, 1);
            } else if (((keyCode == KeyEvent.VK_RIGHT)
                    && (caretPos == textLength) && (selText == null))
                    || ((keyChar == '.') && (!text.isEmpty()) && (selText == null))) {
                field.firePropertyChange("Right", 0, 1);
            } else if ((keyCode == KeyEvent.VK_BACK_SPACE) && (caretPos == 0)
                    && (selText == null)) {
                field.firePropertyChange("BackSpace", 0, 1);
            } else if ((keyCode == KeyEvent.VK_DELETE)
                    && (caretPos == textLength) && (selText == null)) {
                field.firePropertyChange("Delete", 0, 1);
            } else if (keyCode == KeyEvent.VK_HOME) {
                IPV4Field.this.ipFields[0].unSelectAllWhenFocusGained();
                IPV4Field.this.ipFields[0].requestFocus();
                IPV4Field.this.ipFields[0].setCaretPosition(0);
            } else if (keyCode == KeyEvent.VK_END) {
                int last = IPV4Field.this.ipFields.length - 1;
                textLength = IPV4Field.this.ipFields[last].getText()
                        .length();
                IPV4Field.this.ipFields[last]
                        .unSelectAllWhenFocusGained();
                IPV4Field.this.ipFields[last].requestFocus();
                IPV4Field.this.ipFields[last]
                        .setCaretPosition(textLength);
            } else if ((keyCode == KeyEvent.VK_0 || keyCode == KeyEvent.VK_NUMPAD0) && (caretPos == 0 || (text != null && text.equals(selText)))) {
                field.firePropertyChange("Right", 0, 1);
            } else if (("0123456789".indexOf(keyChar) >= 0)) {
                if (selText == null) {
                    int ipInt = (text.length() == 0 ? 0 : Integer
                            .parseInt(text));

                    if (ipInt > 25) {
                        field.firePropertyChange("Right", 0, 1);
                    }
                } else {
                    if (field.getSelectionStart() == 2
                            && field.getSelectionEnd() == 3) {
                        field.firePropertyChange("Right", 0, 1);
                    }
                }
            }
        }
    }

    ;

    public static void main(String[] args) {
        IPV4Field ipFiled = new IPV4Field();
        ipFiled.setIpAddress("192.168.131.1");
        //ipFiled.setIpValue(12345);
        JPanel pl = new JPanel();
        pl.add(ipFiled);
        JFrame frame = new JFrame();
        frame.add(pl);
        frame.setSize(new Dimension(200, 100));
        frame.setVisible(true);
    }

}