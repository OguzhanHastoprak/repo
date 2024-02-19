package com.headhunter.Mytodoapp.ItemList;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    public void addNewItem(Item item) {
        itemRepository.save(item);
    }

    public void deleteTask(Long id) {
        boolean exists = itemRepository.existsById(id);
        if (!exists) {
            throw new IllegalStateException("id does not exist");
        }
        itemRepository.deleteById(id);
    }

    @Transactional
    public void updateItem(Long id, String text, boolean done) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new IllegalStateException("id does not exist"));

        if (text != null && text.length() > 0 && !Objects.equals(item.getText(), text)) {
            item.setText(text);
        }

        if (!Objects.equals(item.isDone(), done)) {
            item.setDone(done);
        }
    }
}
