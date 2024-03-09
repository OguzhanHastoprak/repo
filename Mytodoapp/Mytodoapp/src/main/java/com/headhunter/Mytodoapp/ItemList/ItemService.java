package com.headhunter.Mytodoapp.ItemList;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @PostAuthorize("returnObject.user.id == authentication.principal.id")
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

    //@PostFilter("filterObject.user.id == authentication.principal.id")
    public List<Item> getItems() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String user = authentication.getName();
        Optional<User> userId = userRepository.findByUserName(user);
        if (userId.isPresent())
            return itemRepository.findByUserId(userId.get().getId());
        else
            throw new IllegalStateException("Current user not found");
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

    //@PreAuthorize("#id")
    public void deleteTask(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        if (item.isPresent()) {
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            String user = authentication.getName();
            Optional<User> userId = userRepository.findByUserName(user);
            if (userId.isPresent()) {
                if (Objects.equals(item.get().getUser().getId(), userId.get().getId()))
                    itemRepository.deleteById(id);
                else throw new IllegalStateException("403");
            } else throw new IllegalStateException("Current user not found");
        } else throw new IllegalStateException("Item not found");
    }

    @Transactional
    public void updateItem(Long id, Item itemUpdate, Principal principal) {
        Item item = itemRepository.findByIdAndUserUserName(id, principal.getName());
        if (item == null)
            throw new IllegalStateException("item not found");
        Item updatedItem = new Item(id, itemUpdate.getText(), itemUpdate.isDone(), itemUpdate.getDeadline(), item.getUser());
        itemRepository.save(updatedItem);
    }
}
