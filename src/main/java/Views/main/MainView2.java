package Views.main;

import Views.Login_Registration.LoginView;
import Views.Login_Registration.Selection;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabVariant;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
@PWA(name = "Coll@HBRS2", shortName = "Coll@HBRS2", enableInstallPrompt = false)
@Theme(themeFolder = "collathbrs")
public class MainView2 extends AppLayout {

    private final Tabs menu;

    public MainView2() {
        HorizontalLayout header = createHeader();
        menu = createMenuTabs();
        addToNavbar(createTopBar(header, menu));
    }

    private VerticalLayout createTopBar(HorizontalLayout header, Tabs menu) {
        VerticalLayout layout = new VerticalLayout();
        layout.getThemeList().add("dark");
        layout.setWidthFull();
        layout.setSpacing(false);
        layout.setPadding(false);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.add(header, menu);
        return layout;
    }

    private HorizontalLayout createHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.setPadding(false);
        header.setSpacing(false);
        header.setWidthFull();
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setId("header");
        Image logo = new Image("images/logo1.png", "Coll@HBRS Logo");
        logo.setId("logo");
        header.add(logo);
        Avatar avatar = new Avatar();
        avatar.setId("avatar");
        header.add(new H1("Coll@HBRS"));
        header.add(avatar);
        return header;
    }

    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.getStyle().set("max-width", "100%");
        tabs.add(getAvailableTabs());
        return tabs;
    }

    private static Tab[] getAvailableTabs() {
        return new Tab[]{createTab("Home", Home.class),//wie Tristan sie nennt anpassen statt LoginView
                         createTab("Stellenanzeigen", LoginView.class),//hier auch
                         createTab("Bewerbungen", LoginView.class),//hier auch
                         createTab("Einstellungen", Selection.class)};//hier auch
    }

    private static Tab createTab(String text, Class<? extends Component> navigationTarget) {
        final Tab tab = new Tab();
        tab.addThemeVariants(TabVariant.LUMO_ICON_ON_TOP);
        tab.add(new RouterLink(text, navigationTarget));
        ComponentUtil.setData(tab, Class.class, navigationTarget);
        return tab;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    ProgressBar progressBar = new ProgressBar(20, 100, 40); //läuft noch nicht, weiß noch nicht wie man es implementiert
    NativeButton progressButton = new NativeButton("Profil Vervollständigung", e -> {
        double value = progressBar.getValue() + 10;
        if (value > progressBar.getMax()) {
            value = progressBar.getMin();
        }
        progressBar.setValue(value);
    });

    //add(progressBar, progressButton);
}

