package org.okou.lippen.network.tool.ui.field;

import javax.swing.JTextField;

@SuppressWarnings("serial")
public class PortField extends JTextField {
    public PortField() {
        super();
    }

    public PortField(int number) {
        super(new NumberDocument(Short.MAX_VALUE - Short.MIN_VALUE), String.valueOf(number), 6);
    }

    public int getNumber() {
        return Integer.parseInt(getText());
    }
}
