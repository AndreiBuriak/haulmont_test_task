package com.example.demo.components;

import com.example.demo.backend.Credit;
import com.example.demo.repo.CreditRepo;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class CreditEditor extends VerticalLayout implements KeyNotifier {
    private final CreditRepo creditRepo;
    private Credit credit;
    @Setter
    private ChangeHandler changeHandler;
    private TextField name = new TextField("Вид кредита");
    private IntegerField limit = new IntegerField("Лимит по кредиту");
    private IntegerField percent = new IntegerField("Процент");
    private Button save = new Button("Сохранить", VaadinIcon.CHECK.create());
    private Button cancel = new Button("Отмена", VaadinIcon.CHECK.create());
    private Button delete = new Button("Удалить", VaadinIcon.TRASH.create());
    private HorizontalLayout fields = new HorizontalLayout(name, limit, percent);
    private HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);
    private Binder<Credit> binder = new Binder<>(Credit.class);

    @Autowired
    public CreditEditor(CreditRepo creditRepo) {
        this.creditRepo = creditRepo;
        add(fields, actions);
        binder.bindInstanceFields(this);

        setSpacing(true);

        save.getElement().getThemeList().add("primary");
        delete.getElement().getThemeList().add("error");
        addKeyPressListener(Key.ENTER, с -> save());


        save.addClickListener(с -> save());
        delete.addClickListener(с -> delete());
        cancel.addClickListener(с -> cancel());
        setVisible(false);
    }

    private void delete() {
        creditRepo.delete(credit);
        changeHandler.onChange();
    }

    private void save() {
        creditRepo.save(credit);
        changeHandler.onChange();
    }

    private void cancel() {
        changeHandler.onChange();
    }

    public void editClient(Credit newCredit) {
        if (newCredit == null) {
            setVisible(false);
            return;
        }
        if (newCredit.getId() != null) {
            credit = creditRepo.findById(newCredit.getId()).orElse(newCredit);
        } else {
            credit = newCredit;
        }
        binder.setBean(credit);
        setVisible(true);
        name.focus();

    }

}
