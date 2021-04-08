package view.exitDialoBuilder;

import controller.logController.LogWindowSource;

import javax.swing.event.InternalFrameAdapter;

public interface InternalExitDialogBuilder {
    InternalFrameAdapter buildInternalFrameAdapter();
    ExitDialogBuilder setSource(LogWindowSource source);
}
