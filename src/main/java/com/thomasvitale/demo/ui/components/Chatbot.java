package com.thomasvitale.demo.ui.components;

import com.thomasvitale.demo.ai.AiService;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.vaadin.firitin.components.messagelist.MarkdownMessage;

public class Chatbot extends VerticalLayout {

    private static final String USER_LABEL = "You";
    private static final String ASSISTANT_LABEL = "AI";

    public Chatbot(AiService aiService) {
        setSizeFull();

        var chatWindow = new VerticalLayout();
        var inputForm = new MessageInput();
        inputForm.setWidthFull();

        inputForm.addSubmitListener(event -> {
            var userMessage = event.getValue();
            chatWindow.add(new MarkdownMessage(userMessage, USER_LABEL));

            var assistantMessage = new MarkdownMessage(ASSISTANT_LABEL);
            chatWindow.add(assistantMessage);

            aiService.chat(userMessage)
                    .subscribe(assistantMessage::appendMarkdownAsync);
        });

        addAndExpand(new Scroller(chatWindow));
        add(inputForm);
    }

}
