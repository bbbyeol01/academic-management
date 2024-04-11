package com.ezen.management.repository;

import com.ezen.management.domain.Category;
import com.ezen.management.dto.CategoryDTO;
import com.ezen.management.repository.search.CategorySearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long>, CategorySearch {

    @Query("select c from Category c where c.name = :name")
    Category getCategoryByName(String name);

}
