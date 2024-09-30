package com.bitsvalley.micro.model.response;

import com.bitsvalley.micro.model.Category;
import com.bitsvalley.micro.model.Products;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductsCategories {

  private Map<Long, Category> categories;
  private Map<Long, List<Products>> products;

}
