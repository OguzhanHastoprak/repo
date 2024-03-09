package com.headhunter.Mytodoapp.ItemList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/item")
//@PreAuthorize("hasRole('USER')")
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
    public void updateTask(@PathVariable("id") Long id, @RequestBody Item item, Principal principal) {
        itemService.updateItem(id, item, principal);
    }
}