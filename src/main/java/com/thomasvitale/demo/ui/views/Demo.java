package com.thomasvitale.demo.ui.views;

import com.thomasvitale.demo.ai.DemoService;
import com.thomasvitale.demo.ui.components.Chatbot;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;

@Menu(title = "Demo", order = 1)
@Route("")
public class Demo extends Chatbot {

    public Demo(DemoService demoService) {
        super(demoService);
    }

}
