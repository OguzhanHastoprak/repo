package com.headhunter.Mytodoapp.ItemList;

import java.net.URI;
import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path = "api/v1/item")
@PreAuthorize("hasRole('USER')")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> getItems() {
        return itemService.getItems();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Item> findById(@PathVariable Long id) {
        return ResponseEntity.ok(this.itemService.findItem(id));
    }

    /*
     * @GetMapping(path = "{id}")
     * public ResponseEntity<Item> getItem(@PathVariable("id") Long id, Principal
     * principal) {
     * Item item = itemService.findById(id, principal);
     * if (item != null)
     * return ResponseEntity.ok(item);
     * else
     * return ResponseEntity.notFound().build();
     * }
     */

    @PostMapping
    private ResponseEntity<Void> createItem(@RequestBody Item newItemRequest, UriComponentsBuilder ucb,
            Principal principal) {
        Item savedItem = itemService.addNewItem(newItemRequest, principal);
        if (savedItem != null) {
            URI locationOfNewItem = ucb
                    .path("api/v1/item/{id}")
                    .buildAndExpand(savedItem.getId())
                    .toUri();
            return ResponseEntity.created(locationOfNewItem).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*
     * @PostMapping
     * public void addNewTask(@RequestBody Item item) {
     * itemService.addNewItem(item);
     * }
     */

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