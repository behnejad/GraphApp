package com.sinacomsys.prj1;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        GUI gui = new GUI();
        gui.setSize(480, 480);
        gui.setVisible(true);

    }
}
