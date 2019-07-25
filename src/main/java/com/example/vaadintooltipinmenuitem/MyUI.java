package com.example.vaadintooltipinmenuitem;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

@Theme("mytheme")
public class MyUI extends UI {

	private static final long serialVersionUID = 1L;

	@Override
    protected void init(VaadinRequest vaadinRequest) {

        final VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new Label("No Tooltip in menuItem dialog with: " + 
        		com.vaadin.shared.Version.getFullVersion()));
        Label issueLabel = new Label("Demonstrate MenuBar Tooltip problem when displayed in a Dialog box");
        issueLabel.setContentMode(ContentMode.HTML);
        layout.addComponent(issueLabel);
        
        Button openDialogButton = new Button("Open Dialog");
        openDialogButton.addClickListener(event -> {
        	Window dialog = new Window("DialogWithMenu");
        	dialog.center();
        	dialog.setWidth("500px");
        	dialog.setHeight("500px");
        	
            MenuBar dialogmenuBar = new MenuBar();
            MenuItem menuItem = dialogmenuBar.addItem("First item in dialog");
            MenuItem firstDropdownItem = menuItem.addItem("Dropdown dialog item");
            firstDropdownItem.setDescription("Dropdown dialog item tooltip");
        	
        	dialog.setContent(dialogmenuBar);
        	addWindow(dialog);
        });
        
        MenuBar menuBar = new MenuBar();
        MenuItem menuItem = menuBar.addItem("First item");
        MenuItem firstDropdownItem = menuItem.addItem("Dropdown item");
        firstDropdownItem.setDescription("Dropdown Item Tooltip");
 
        layout.addComponent(openDialogButton);
        
        layout.addComponent(menuBar);
        layout.setMargin(true);
        layout.setSpacing(true);
        
        setContent(layout);
        
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
		private static final long serialVersionUID = 1L;
    }
    
}
