package com.headhunter.Mytodoapp.ItemList;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import com.headhunter.Mytodoapp.User.User;
import com.headhunter.Mytodoapp.User.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public ItemService(ItemRepository itemRepository, UserRepository userRepository) {
        this.itemRepository = itemRepository;
        this.userRepository = userRepository;
    }

    // deneme
    @PostAuthorize("returnObject.user.id==authentication.principal.id")
    public Item findItem(Long requestedId) {
        Optional<Item> optionalItem = itemRepository.findById(requestedId);
        if (optionalItem.isPresent())
            return optionalItem.get();
        else
            throw new IllegalStateException("Item not found");
    }

    public Item findById(Long requestedId, Principal principal) {
        Optional<User> currentUser = userRepository.findByUserName(principal.getName());
        if (currentUser.isPresent()) {
            int userId = findUserIdByUsername(principal.getName());
            return itemRepository.findByIdAndUserId(requestedId, userId);
        } else
            throw new IllegalStateException("Current user not found");
    }

    public int findUserIdByUsername(String userName) {
        Optional<User> userOptional = userRepository.findByUserName(userName);
        return userOptional.map(User::getId).orElse(null);
    }

    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    @Transactional
    public Item addNewItem(Item newItemRequest, Principal principal) {
        Optional<User> currentUser = userRepository.findByUserName(principal.getName());
        if (currentUser.isPresent())
            newItemRequest.setUser(currentUser.get());
        else
            throw new IllegalStateException("Current user not found");
        Item itemWithUser = new Item(newItemRequest.getText(),
                newItemRequest.isDone(),
                newItemRequest.getDeadline(),
                newItemRequest.getUser());
        itemRepository.save(itemWithUser);
        return itemWithUser;
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
