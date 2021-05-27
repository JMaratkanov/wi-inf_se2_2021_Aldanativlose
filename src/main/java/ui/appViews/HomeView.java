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
        addClassName("home-view");
        add(createTitle());
        add(new Text("Wie geht's?"));
    }
    private Component createTitle() {
        return new H3("Hallo Timo Salda!");
    }
}