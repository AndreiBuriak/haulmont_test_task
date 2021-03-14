package com.example.demo.view;

import com.example.demo.backend.Offer;
import com.example.demo.components.OfferEditor;
import com.example.demo.components.OfferInfo;
import com.example.demo.repo.OfferRepo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;


@SpringComponent
@UIScope
@Route("offers")
public class OfferView extends VerticalLayout {
    private OfferRepo offerRepo;

    private Grid<Offer> grid = new Grid<>(Offer.class);

    private final MainView mainView = new MainView();
    private final Button addNewButton = new Button("Добавить", VaadinIcon.PLUS.create());
    private final HorizontalLayout navigationLayout = mainView.getNavigationButtons();

    private final OfferEditor editor;
    private final OfferInfo info;
    @Autowired
    public OfferView(OfferRepo offerRepo, OfferEditor editor, OfferInfo info) {
        this.offerRepo = offerRepo;
        this.editor = editor;
        this.info = info;

        viewForOffer(offerRepo);

    }
    private void viewForOffer(OfferRepo offerRepo) {

        grid.removeAllColumns();
        grid.addColumn(e -> e.getClient().getName()).setHeader("Имя клиента");
        grid.addColumn(e -> e.getCredit().getName()).setHeader("Тип кредита");
        grid.addColumn(e -> e.getAmount()).setHeader("Сумма займа");


        add(navigationLayout, addNewButton, grid, editor, info);


        addNewButton.addClickListener(e -> editor.openAddingForm());
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            showOffer("");
        });

        grid.asSingleSelect().addValueChangeListener(e -> info.getInfo(e.getValue()));

        showOffer("");
    }
    private void showOffer (String name) {
        if (name.isEmpty()) {
            grid.setItems(offerRepo.findAll());
        } else {
            grid.setItems(offerRepo.findByClientName(name));
        }
    }
}
