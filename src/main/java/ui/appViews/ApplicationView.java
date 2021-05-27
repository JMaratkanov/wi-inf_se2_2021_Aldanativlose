package ui.appViews;

import ui.layouts.AppLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "application", layout = AppLayout.class)
@PageTitle("Bewerbungen")
public class ApplicationView extends Div {

    public ApplicationView() {
        addClassName("application-view");
        add(createTitle());
        add(new Text("Hier sehen Sie ihre Bewerbungen."));
    }

    private Component createTitle() {
        return new H3("Bewerbungen");
    }
}
