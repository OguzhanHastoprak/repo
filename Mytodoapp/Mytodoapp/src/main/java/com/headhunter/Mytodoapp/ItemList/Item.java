package com.headhunter.Mytodoapp.ItemList;

import java.time.LocalDate;
import java.time.Period;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table
public class Item {
    @Id
    @SequenceGenerator(name = "item_sequence", sequenceName = "item_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_sequence")
    private Long id;
    private String text;
    private boolean done;
    private LocalDate deadline;
    @Transient
    private int remainingDays;

    public int getRemainingDays() {
        return Period.between(LocalDate.now(), this.deadline).getDays();
    }

    public void setRemainingDays(int remainingDays) {
        this.remainingDays = remainingDays;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Item() {

    }

    public Item(Long id,
            String text,
            boolean done,
            LocalDate deadline) {
        this.id = id;
        this.text = text;
        this.done = done;
        this.deadline = deadline;
    }

    public Item(String text,
            boolean done,
            LocalDate deadline) {
        this.text = text;
        this.done = done;
        this.deadline = deadline;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", text=" + text + '\'' +
                ", done=" + done + '\'' +
                ", deadline=" + deadline +
                ", remaining days=" + remainingDays +
                '}';
    }
}
