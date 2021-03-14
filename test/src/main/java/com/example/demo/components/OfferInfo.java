package com.example.demo.components;

import com.example.demo.backend.Offer;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;


@SpringComponent
@UIScope
public class OfferInfo extends HorizontalLayout implements KeyNotifier {
    private Button cancel = new Button("Отмена", VaadinIcon.CHECK.create());
    private final Label descClientName = new Label("Имя клиента: ");
    private final Label desCreditName = new Label("Тип кредита: ");
    private final TextArea graphPay = new TextArea("Информация о платеже:");
    private final TextArea clientName = new TextArea();
    private final TextArea creditName = new TextArea();
    VerticalLayout verticalLayout = new VerticalLayout(descClientName, desCreditName);
    HorizontalLayout horizontalLayout = new HorizontalLayout(graphPay, cancel);

    @Autowired
    public OfferInfo() {
        add(verticalLayout, horizontalLayout);
        cancel.addClickListener(с -> cancel());
        setVisible(false);
    }

    public void getInfo(Offer offer) {
        if (offer != null) {
            graphPay.setWidth("400px");
            clientName.setValue(offer.getClient().getName());
            creditName.setValue(offer.getCredit().getName());
            descClientName.add(clientName);
            desCreditName.add(creditName);
            graphPay.setValue(getGraph(offer));
            setVisible(true);
        }

    }

    private void cancel() {
        setVisible(false);
    }

    public String getGraph(Offer offer) {
        Double percent = Double.valueOf(offer.getCredit().getPercent());
        Double amount = Double.valueOf(offer.getAmount());
        double debt = amount * (1 + percent / 100);
        double interestAmount = amount * percent / 100;


        return "Начальная сумма кредита: " + String.format("%.2f", amount) + " руб." +
                "\nДолг: " + String.format("%.2f", debt) + " руб." +
                "\nЕжемесячный платеж: " + String.format("%.2f", debt / 12) + " руб." +
                "\nОсновной долг: " + String.format("%.2f", amount / 12) + " руб." +
                "\nПроценты: " + String.format("%.2f", interestAmount / 12) + " руб.";
    }
}
