package ui.appViews;

import ui.layouts.AppLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "setting", layout = AppLayout.class)
@PageTitle("Settings")
public class SettingsView extends Div {

    public SettingsView() {
        addClassName("wrapper");
        setId("settings-view");
        add(createTitle());
        add(new Text("Hier können Sie Anpassungen an den Einstellungen vornehmen."));
    }
    private Component createTitle() {
        return new H3("Einstellungen");
    }
}