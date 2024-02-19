package com.headhunter.Mytodoapp.ItemList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/item")
public class ItemController {

    private final ItemService itemService;

    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> getItems() {
        return itemService.getItems();
    }

    @PostMapping
    public void addNewTask(@RequestBody Item item) {
        itemService.addNewItem(item);
    }

    @DeleteMapping(path = "{id}")
    public void removeTask(@PathVariable("id") Long id) {
        itemService.deleteTask(id);
    }

    @PutMapping(path = "{id}")
    public void updateTask(@PathVariable("id") Long id,
            @RequestParam(required = false) String text,
            @RequestParam(required = false) boolean done) {
        itemService.updateItem(id, text, done);
    }
}