package ui.appViews;

import ui.layouts.AppLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "home", layout = AppLayout.class)
@PageTitle("Home")
public class HomeView extends Div {

    public HomeView() {
        setId("home-view");
        addClassName("wrapper");
        add(createTitle());
        add(new Text("Schön dich wieder zu sehen, wie geht's dir?"));
    }
    private Component createTitle() {
        return new H3("Guten Tag!");
    }
}