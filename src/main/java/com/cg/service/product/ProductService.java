package com.cg.service.product;



import com.cg.dto.ProductDto;
import com.cg.entity.Category;
import com.cg.entity.Product;
import com.cg.repo.CategoryRepository;
import com.cg.repo.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProductService implements IProductService{
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Override
    public Iterable<ProductDto> findAll() {
        Iterable<Product> products = productRepository.findAll();
        return StreamSupport.stream(products.spliterator(),true)
                .map(product -> {
                    ProductDto productDto = modelMapper.map(product, ProductDto.class);
                    productDto.setCategory_name(product.getCategory().getName().toString());
                    return productDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        Product product = productRepository.findById(id).get();
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        return Optional.ofNullable(productDto);
    }

    @Override
    public void save(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        Category category = categoryRepository.findByName(productDto.getCategory_name());
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
         modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public void remove(Long id) {
        productRepository.deleteById(id);
    }
}
