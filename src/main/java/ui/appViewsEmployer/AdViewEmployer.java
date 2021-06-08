package ui.appViewsEmployer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import ui.layouts.AppLayout;

//@Route(value = "ads", layout = AppLayout.class)
//@PageTitle("Stellenanzeigen_Unternehmer")
public class AdViewEmployer extends Div {

    public AdViewEmployer() {
        setId("ad-view");
        addClassName("wrapper");
        add(createTitle());
        add(new Text("Hier sehen Sie alle ihre aufgegebenen Stellenanzeigen"));
    }
    private Component createTitle() {
        return new H3("Stellenanzeigen");
    }
}