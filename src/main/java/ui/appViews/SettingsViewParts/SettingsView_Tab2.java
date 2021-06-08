package ui.appViews.SettingsViewParts;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import control.LoginControl;
import control.SettingsControl;

public class SettingsView_Tab2 {
    private LoginControl loginControl = new LoginControl(); //Um die current user ID zu bekommen
    private SettingsControl settingsControl = new SettingsControl();

    public Div createView(Div page2) {

        //Upload
        MemoryBuffer buffer = new MemoryBuffer();
        Upload upload = new Upload(buffer);
        upload.setMaxFiles(1);
        upload.setDropLabel(new Label("Lade deinen Lebenslauf als PDF hoch"));
        upload.setAcceptedFileTypes("text/pdf");
        upload.setMaxFileSize(10000000);
        Div output = new Div();

        upload.addFileRejectedListener(event -> {
            Paragraph component = new Paragraph();
            MyUploadContext.showOutput(event.getErrorMessage(), component, output);
        });
        upload.getElement().addEventListener("file-remove", event -> {
            output.removeAll();
            //TODO PDF irgendwo speichern
        });

        page2.add(upload, output);
        return page2;
    }
}


//Diese Klasse wird nur in Tab 2 zum Upload verwendet; Da Sie nichts mit der restlichen Logik zu tun hat ist sie der Übersicht halber
//ausgelagert. Die Methode kann wiederverwendet werden

class MyUploadContext {
    static void showOutput(String text, Component content, HasComponents outputContainer) {
        HtmlComponent p = new HtmlComponent(Tag.P);
        p.getElement().setText(text);
        outputContainer.add(p);
        outputContainer.add(content);
    }
}