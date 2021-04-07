package view.exitDialoBuilder;

import controller.LogController.LogWindowSource;
import model.property.PropertyContainer;

import java.awt.event.WindowAdapter;

public interface WindowExitDialogBuilder {
    WindowAdapter buildWindowAdapter();
}
