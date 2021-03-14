package com.example.demo.view;

import com.example.demo.backend.Client;
import com.example.demo.components.ClientEditor;
import com.example.demo.repo.ClientRepo;
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


@SpringComponent
@UIScope
@Route (value = "clients")
public class ClientView extends VerticalLayout {
    private final ClientRepo clientRepo;

    private Grid<Client> grid = new Grid<>(Client.class);

    private final TextField filter = new TextField("", "Отфильтровать");
    private final Button addNewButton = new Button("Добавить", VaadinIcon.PLUS.create());
    private final HorizontalLayout toolbar = new HorizontalLayout(filter, addNewButton);
    private final MainView mainView = new MainView();
    private final HorizontalLayout navigationLayout = mainView.getNavigationButtons();

    private final ClientEditor editor;

    public ClientView(ClientRepo clientRepo, ClientEditor editor) {
        this.clientRepo = clientRepo;
        this.editor = editor;
        viewForClient();
    }

    private void viewForClient() {


        grid.removeColumnByKey("id");
        grid.removeColumnByKey("offers");
        grid.setColumns("name", "document", "telephone", "email");

        add(navigationLayout, toolbar, grid, editor);

        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> showClient(e.getValue()));

        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editClient(e.getValue());
        });

        addNewButton.addClickListener(e -> editor.editClient(new Client()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            showClient(filter.getValue());
        });

        showClient("");
    }

    private void showClient(String name) {
        if (name.isEmpty()) {
            grid.setItems(clientRepo.findAll());
        } else {
            grid.setItems(clientRepo.findByName(name));
        }
    }
}

