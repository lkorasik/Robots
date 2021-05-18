package view.frameClosing;

import translation.LanguageBundle;
import translation.LocalizationTextKeys;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrameClosing extends JFrame {
    public FrameClosing() {
        super();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                var languageBundle = LanguageBundle.getInstance();
                Object[] options = {
                        languageBundle.getString(LocalizationTextKeys.EXIT_DIALOG_YES.getKey()),
                        languageBundle.getString(LocalizationTextKeys.EXIT_DIALOG_NO.getKey())};
                var decision = JOptionPane
                        .showOptionDialog(
                                e.getWindow(),
                                languageBundle.getString(LocalizationTextKeys.EXIT_DIALOG_MESSAGE.getKey()),
                                languageBundle.getString(LocalizationTextKeys.EXIT_DIALOG_TITLE.getKey()),
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]);
                if (decision == JOptionPane.YES_OPTION)
                    FrameClosing.this.setDefaultCloseOperation(JInternalFrame.EXIT_ON_CLOSE);
                else
                    FrameClosing.this.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
            }
        });
    }
}
