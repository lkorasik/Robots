package view.exitDialoBuilder;

import controller.LogController.LogWindowSource;
import view.exitDialoBuilder.ExitDialogBuilder;

import javax.swing.event.InternalFrameAdapter;

public interface InternalExitDialogBuilder {
    InternalFrameAdapter buildInternalFrameAdapter();
    ExitDialogBuilder setSource(LogWindowSource source);
}
