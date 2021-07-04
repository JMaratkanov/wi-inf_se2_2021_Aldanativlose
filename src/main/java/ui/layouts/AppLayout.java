package ui.layouts;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import dtos.UserDTO;
import globals.Globals;
import ui.appViews.AdView;
import ui.appViews.ApplicationView;
import ui.appViews.HomeView;
import ui.appViews.SettingsView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
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

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.UI;

import java.util.Optional;

/**
 * The main view is a top-level placeholder for other views.
 */
@PWA(name = "Coll@HBRS", shortName = "Coll@HBRS", enableInstallPrompt = false)
@Theme(themeFolder = "collathbrs")
public class AppLayout extends com.vaadin.flow.component.applayout.AppLayout implements BeforeEnterObserver {

    private final Tabs menu;
    private H1 helloUser;

    public AppLayout() {
        HorizontalLayout header = createHeader();
       /* if(getTrueIfSessionIsEmployer()){
            menu = createMenuTabsEmployer();
        }
        else {
            menu = createMenuTabs();
        }*/
        menu = createMenuTabs();
        addToNavbar(createTopBar(header, menu));
        getElement().getStyle().set("background-image", Globals.Backgrounds.APP_LAYOUT_BACKGROUND);
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

        header.add(new H1("Coll@HBRS - Das Kollaborations Portal"));

        Avatar avatar = new Avatar();
        avatar.setId("avatar");
        header.add(avatar);

        helloUser = new H1();
        // Der Name des Users wird später reingesetzt, falls die Navigation stattfindet
        header.add(helloUser);

        Button logoutbtn = new Button("Logout" , e -> logoutUser());
        logoutbtn.getElement().getStyle().set("margin-left", "10px");
        logoutbtn.getElement().getStyle().set("margin-right", "10px");
        logoutbtn.setId("logoutbtn");
        header.add(logoutbtn);

        return header;
    }

    private void logoutUser() {
        UI ui = this.getUI().get();
        ui.getSession().close();
        ui.getPage().setLocation("./");
    }

    private static Tabs createMenuTabs() {
        final Tabs tabs = new Tabs();
        tabs.getStyle().set("max-width", "100%");
        tabs.add(getAvailableTabs());
        return tabs;
    }

    private static Tab[] getAvailableTabs() {

        return new Tab[]{createTab("Home", HomeView.class),
                createTab("Stellenanzeigen", AdView.class),
                createTab("Bewerbungen", ApplicationView.class),
                createTab("Einstellungen", SettingsView.class)};
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

        // Falls der Benutzer nicht eingeloggt ist, dann wird er auf die Startseite gelenkt
        if ( !checkIfUserIsLoggedIn() ) return;

        getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
        helloUser.setText("Hallo "  + this.getCurrentEmailOfUser());
    }

    private Optional<Tab> getTabForComponent(Component component) {
        return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
                .findFirst().map(Tab.class::cast);
    }

    private boolean checkIfUserIsLoggedIn() {
        // Falls der Benutzer nicht eingeloggt ist, dann wird er auf die Startseite gelenkt
        UserDTO userDTO = this.getCurrentUser();
        if (userDTO == null) {
            UI.getCurrent().navigate(Globals.Pages.LOGIN_VIEW);
            return false;
        }
        return true;
    }

    private String getCurrentEmailOfUser() {
        return getCurrentUser().getEmail();
    }

    private UserDTO getCurrentUser() {
        return (UserDTO) UI.getCurrent().getSession().getAttribute(Globals.CURRENT_USER);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if (getCurrentUser() == null){
            beforeEnterEvent.rerouteTo(Globals.Pages.LOGIN_VIEW);
        }
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

