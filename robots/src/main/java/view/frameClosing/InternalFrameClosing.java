package view.frameClosing;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Setter;
import model.Constants;
import model.serialization.InternalFrameDeserializer;
import model.serialization.InternalFrameSerializer;

import javax.swing.*;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

@JsonSerialize(using = InternalFrameSerializer.class)
@JsonDeserialize(using = InternalFrameDeserializer.class)
public class InternalFrameClosing extends JInternalFrame {

    @Setter
    private Runnable actionExiting = () -> {
    };

    public InternalFrameClosing(String title, Boolean resizable, Boolean closable, Boolean maximizable, Boolean iconf) {
        super(title, resizable, closable, maximizable, iconf);
        addInternalFrameListener(new InternalFrameAdapter() {
            @Override
            public void internalFrameClosing(InternalFrameEvent e) {
                Object[] options = {Constants.ExitPaneOptions.YES, Constants.ExitPaneOptions.NO};
                var decision = JOptionPane
                        .showOptionDialog(
                                e.getInternalFrame(),
                                Constants.ExitPaneOptions.WINDOW_MESSAGE,
                                Constants.ExitPaneOptions.WINDOW_TITLE,
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                options,
                                options[0]);
                if (decision == JOptionPane.YES_OPTION) {
                    actionExiting.run();
                    InternalFrameClosing.this.setDefaultCloseOperation(JInternalFrame.HIDE_ON_CLOSE);
                } else {
                    InternalFrameClosing.this.setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }
}
