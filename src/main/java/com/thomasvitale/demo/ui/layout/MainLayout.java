package com.thomasvitale.demo.ui.layout;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Layout
public class MainLayout extends AppLayout {

    private static final String APPLICATION_NAME = "Modular RAG";

    public MainLayout() {
        var title = new H2(APPLICATION_NAME);
        title.addClassNames(LumoUtility.FontSize.LARGE);

        var header = new HorizontalLayout();
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.add(new DrawerToggle());
        header.add(title);

        addToNavbar(header);

        var sidebar = new VerticalLayout();
        MenuConfiguration.getMenuEntries().forEach(menuEntry
                -> sidebar.add(new RouterLink(menuEntry.title(), menuEntry.menuClass())));

        addToDrawer(sidebar);
    }

}
