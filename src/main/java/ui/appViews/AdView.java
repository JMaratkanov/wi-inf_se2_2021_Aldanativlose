package ui.appViews;

import ui.layouts.AppLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "ads", layout = AppLayout.class)
@PageTitle("Stellenanzeigen")
public class AdView extends Div {

    public AdView() {
        addClassName("ad-view");
        add(createTitle());
        add(new Text("Hier sehen Sie alle Stellenanzeigen"));
    }
    private Component createTitle() {
        return new H3("Stellenanzeigen");
    }
}