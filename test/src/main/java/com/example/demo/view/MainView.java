package com.example.demo.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;


@Route
public class MainView extends HorizontalLayout {
    @Autowired
    public MainView() {
        HorizontalLayout mainLayout = getNavigationButtons();
        add(mainLayout);
    }

    public HorizontalLayout getNavigationButtons() {
        Button creditButton = new Button("Кредиты");
        Button clientButton = new Button("Клиенты");
        Button offerButton = new Button("Кредитные предложения");
        creditButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        clientButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        offerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout layout = new HorizontalLayout(clientButton, creditButton, offerButton);
        layout.setHeightFull();

        clientButton.addClickListener(c -> clientButton.getUI().ifPresent(ui -> ui.navigate("clients")));
        creditButton.addClickListener(c -> creditButton.getUI().ifPresent(ui -> ui.navigate("credits")));
        offerButton.addClickListener(c -> offerButton.getUI().ifPresent(ui -> ui.navigate("offers")));
        return layout;
    }
}
