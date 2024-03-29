package com.headhunter.Mytodoapp.ItemList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findByIdAndUserUserName(Long id, String userName);

    Item findByIdAndUserId(Long id, int userId);

    List<Item> findByUserId(int userId);

}
