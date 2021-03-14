package com.example.demo.components;

import com.example.demo.backend.Client;
import com.example.demo.backend.Credit;
import com.example.demo.backend.KeyForOffer;
import com.example.demo.backend.Offer;
import com.example.demo.repo.ClientRepo;
import com.example.demo.repo.CreditRepo;
import com.example.demo.repo.OfferRepo;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class OfferEditor extends VerticalLayout implements KeyNotifier {
    private final OfferRepo offerRepo;
    private final ClientRepo clientRepo;
    private final CreditRepo creditRepo;
    ComboBox clientName = new ComboBox("Имя клиента");
    ComboBox creditName = new ComboBox("Тип кредита");
    IntegerField amount = new IntegerField("Сумма займа");
    private Button save = new Button("Сохранить", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Отмена");
    private HorizontalLayout actions = new HorizontalLayout(save, cancel);
    private Binder<Offer> binder = new Binder<>(Offer.class);
    @Setter
    private ChangeHandler changeHandler;

    @Autowired
    public OfferEditor(OfferRepo offerRepo, ClientRepo clientRepo, CreditRepo creditRepo) {
        this.offerRepo = offerRepo;
        this.clientRepo = clientRepo;
        this.creditRepo = creditRepo;


        add(clientName, creditName, amount, actions);
        clientName.setItems(clientRepo.findAll().stream().map(Client::getName));
        creditName.setItems(creditRepo.findAll().stream().map(Credit::getName));
        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        addKeyPressListener(Key.ENTER, с -> save());

        save.addClickListener(с -> save());
        cancel.addClickListener(с -> cancel());
        setVisible(false);
    }

    private void save() {
        String clientNameValue = (String) clientName.getValue();
        String creditNameValue = (String) creditName.getValue();
        if ((clientNameValue != null) || (creditNameValue != null)) {
            Client client = clientRepo.findByName(clientNameValue).get(0);
            Credit credit = creditRepo.findByName(creditNameValue).get(0);
            KeyForOffer key = new KeyForOffer();
            key.setClientID(client.getId());
            key.setCreditID(credit.getId());

            offerRepo.save(new Offer(key, client, credit, amount.getValue()));
            changeHandler.onChange();
        }
    }

    private void cancel() {
        changeHandler.onChange();
    }

    public void openAddingForm() {
        setVisible(true);
        clientName.focus();
    }

}
