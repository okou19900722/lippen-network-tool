package org.okou.lippen.network.tool.ui.menu;

import java.nio.charset.Charset;

import javax.swing.JCheckBoxMenuItem;

@SuppressWarnings("serial")
public class CharsetCheckBoxMenuItem extends JCheckBoxMenuItem {
    private Charset charset;

    public CharsetCheckBoxMenuItem(String text, String charset) {
        super(text);
        this.charset = Charset.forName(charset);
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }
}
