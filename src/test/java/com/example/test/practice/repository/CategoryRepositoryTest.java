package com.example.test.practice.repository;

import com.example.test.practice.PracticeApplicationTests;
import com.example.test.practice.model.entity.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;


public class CategoryRepositoryTest extends PracticeApplicationTests {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void create(){

        String type = "COMPUTER";
        String title = "컴퓨터";
        LocalDateTime createdAt = LocalDateTime.now();
        String createdBy = "AdminServer";

        Category category = new Category();
        category.setType(type);
        category.setTitle(title);
        category.setCreatedAt(createdAt);
        category.setCreatedBy(createdBy);

        Category newCategory = categoryRepository.save(category);
        Assertions.assertNotNull(newCategory);
        Assertions.assertEquals(newCategory.getType(), type);
        Assertions.assertEquals(newCategory.getTitle(), title);

    }

    @Test
    public void read() {
        //id조회 보다는 select * from where type="COMPUTER" 이런식으로 조회
//        Optional<Category> optionalCategory = categoryRepository.findById(1L);
        String type = "COMPUTER";
        Optional<Category> optionalCategory = categoryRepository.findByType(type);


        optionalCategory.ifPresent(c -> {

            Assertions.assertEquals(c.getType(), type);
            System.out.println(c.getId());
            System.out.println(c.getType());
            System.out.println(c.getTitle());

        });

    }


}