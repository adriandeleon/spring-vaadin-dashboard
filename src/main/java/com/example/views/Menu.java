package com.example.views;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by adria on 3/28/2017.
 */
public class Menu extends CssLayout {

    private static final String VALO_MENUITEMS = "valo-menuitems";
    private static final String VALO_MENU_TOGGLE = "valo-menu-toggle";
    private static final String VALO_MENU_VISIBLE = "valo-menu-visible";
    private Navigator navigator;
    private Map<String, Button> viewButtons = new HashMap<String, Button>();

    private CssLayout menuItemsLayout;
    private CssLayout menuPart;

    /**
     * Instantiates a new Menu.
     *
     * @param navigator the navigator
     */
    public Menu(Navigator navigator) {

        this.navigator = navigator;
        setPrimaryStyleName(ValoTheme.MENU_ROOT);

        menuPart = new CssLayout();
        menuPart.addStyleName(ValoTheme.MENU_PART);

        // Header of the menu
        final HorizontalLayout top = new HorizontalLayout();
        top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        top.addStyleName(ValoTheme.MENU_TITLE);

        Label title = new Label("My CRUD");
        title.addStyleName(ValoTheme.LABEL_H3);
        title.setSizeUndefined();

        Image image = new Image(null, new ThemeResource("img/table-logo.png"));
        image.setStyleName("logo");

        top.addComponent(image);
        top.addComponent(title);

        menuPart.addComponent(top);

        // logout menu item
        MenuBar logoutMenu = new MenuBar();
        logoutMenu.addItem("Logout", VaadinIcons.SIGN_OUT, new MenuBar.Command() {

            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                VaadinSession.getCurrent().getSession().invalidate();
                Page.getCurrent().reload();
            }
        });

        logoutMenu.addStyleName("user-menu");
        menuPart.addComponent(logoutMenu);

        // button for toggling the visibility of the menu when on a small screen
        final Button showMenu = new Button("Menu", new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent event) {
                if (menuPart.getStyleName().contains(VALO_MENU_VISIBLE)) {
                    menuPart.removeStyleName(VALO_MENU_VISIBLE);
                } else {
                    menuPart.addStyleName(VALO_MENU_VISIBLE);
                }
            }
        });
        showMenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
        showMenu.addStyleName(ValoTheme.BUTTON_SMALL);
        showMenu.addStyleName(VALO_MENU_TOGGLE);
        showMenu.setIcon(VaadinIcons.MENU);
        menuPart.addComponent(showMenu);

        // container for the navigation buttons, which are added by addView()
        menuItemsLayout = new CssLayout();
        menuItemsLayout.setPrimaryStyleName(VALO_MENUITEMS);
        menuPart.addComponent(menuItemsLayout);

        addComponent(menuPart);
    }

    /**
     * Add view.
     *
     * @param view    the view
     * @param name    the name
     * @param caption the caption
     * @param icon    the icon
     */
    public void addView(View view, final String name, String caption,
                        Resource icon) {
        navigator.addView(name, view);
        createViewButton(name, caption, icon);
    }

    /**
     * Add view.
     *
     * @param viewClass the view class
     * @param name      the name
     * @param caption   the caption
     * @param icon      the icon
     */
    public void addView(Class<? extends View> viewClass, final String name,
                        String caption, Resource icon) {
        navigator.addView(name, viewClass);
        createViewButton(name, caption, icon);
    }

    /**
     * Sets active view.
     *
     * @param viewName the view name
     */
    public void setActiveView(String viewName) {
        for (Button button : viewButtons.values()) {
            button.removeStyleName("selected");
        }
        Button selected = viewButtons.get(viewName);
        if (selected != null) {
            selected.addStyleName("selected");
        }
        menuPart.removeStyleName(VALO_MENU_VISIBLE);
    }

    private void createViewButton(final String name, String caption,
                                  Resource icon) {
        Button button = new Button(caption, new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                navigator.navigateTo(name);

            }
        });
        button.setPrimaryStyleName(ValoTheme.MENU_ITEM);
        button.setIcon(icon);
        menuItemsLayout.addComponent(button);
        viewButtons.put(name, button);
    }

}
