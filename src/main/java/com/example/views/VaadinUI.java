package com.example.views;

import com.example.views.authentication.AccessControl;
import com.example.views.authentication.BasicAccessControl;
import com.example.views.authentication.LoginScreen;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Viewport;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Created by adria on 3/27/2017.
 */
@SpringUI
@Theme("mytheme")
@Viewport("user-scalable=no,initial-scale=1.0")
public class VaadinUI extends UI {

    private AccessControl accessControl = new BasicAccessControl();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        //setContent(new Button("Click me", e -> Notification.show("Hello Spring+Vaadin user!")));

        Responsive.makeResponsive(this);
        setLocale(vaadinRequest.getLocale());
        getPage().setTitle("My");
        if (!accessControl.isUserSignedIn()) {
            setContent(new LoginScreen(accessControl, new LoginScreen.LoginListener() {
                @Override
                public void loginSuccessful() {
                    showMainView();
                }
            }));
        } else {
            showMainView();
        }
    }

    protected void showMainView() {
        addStyleName(ValoTheme.UI_WITH_MENU);
        setContent(new MainScreen(VaadinUI.this));
        getNavigator().navigateTo(getNavigator().getState());
    }

    public static VaadinUI get() {
        return (VaadinUI) UI.getCurrent();
    }

    public AccessControl getAccessControl() {
        return accessControl;
    }
}

