package com.example.demo.view;

import com.example.demo.backend.Credit;
import com.example.demo.components.CreditEditor;
import com.example.demo.repo.CreditRepo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
@Route("credits")
public class CreditView extends VerticalLayout {
    private final CreditRepo creditRepo;

    private Grid<Credit> grid = new Grid<>(Credit.class);

    private final TextField filter = new TextField("", "Отфильтровать");
    private final Button addNewButton = new Button("Добавить", VaadinIcon.PLUS.create());
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewButton);
    private final MainView mainView = new MainView();
    private final HorizontalLayout navigationLayout = mainView.getNavigationButtons();

    private final CreditEditor editor;


    public CreditView(CreditRepo creditRepo, CreditEditor editor) {
        this.creditRepo = creditRepo;
        this.editor = editor;
        viewForCredit();
    }

    private void viewForCredit() {


        grid.removeColumnByKey("id");
        grid.setColumns("name", "percent", "limit");

        add(navigationLayout, toolbar, grid, editor);

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showCredit(e.getValue()));

        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editClient(e.getValue());
        });

        addNewButton.addClickListener(e -> editor.editClient(new Credit()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            showCredit(filter.getValue());
        });

        showCredit("");
    }

    private void showCredit (String name) {
        if (name.isEmpty()) {
            grid.setItems(creditRepo.findAll());
        } else {
            grid.setItems(creditRepo.findByName(name));
        }
    }

}
