package com.shopme.shopmebackend.admin.category.repository;

import com.shopme.shopmecommon.entity.Category;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

}
