package Views.Login_Registration;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import control.LoginControl;
import control.exceptions.DatabaseUserException;
import dtos.UserDTO;

import globals.Globals;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "login" )
//@RouteAlias(value = "login")
public class LoginView extends VerticalLayout {

    //Autowired macht Probleme
    //@Autowired
    private LoginControl loginControl;

    public LoginView() {
        setSizeFull();
        LoginForm component = new LoginForm();

        component.addLoginListener(e -> {

            boolean isAuthenticated = false;
            /* not implemented
            try {
                isAuthenticated = loginControl.authentificate( e.getUsername() , e.getPassword() );

            } catch (DatabaseUserException databaseException) {
                Dialog dialog = new Dialog();
                dialog.add( new Text( databaseException.getReason()) );
                dialog.setWidth("400px");
                dialog.setHeight("150px");
                dialog.open();
            }*/
            if (isAuthenticated) {
                grabAndSetUserIntoSession();
                navigateToMainPage();

            } else {
                component.setError(true);
            }
        });

        add(component);
        this.setAlignItems( Alignment.CENTER );
    }

    private void grabAndSetUserIntoSession() {
        UserDTO userDTO = loginControl.getCurrentUser();
        UI.getCurrent().getSession().setAttribute( Globals.CURRENT_USER, userDTO );
    }


    private void navigateToMainPage() {
        UI.getCurrent().navigate(Globals.Pages.SHOW_MAIN);
    }
}
