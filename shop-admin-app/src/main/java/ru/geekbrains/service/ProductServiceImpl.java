package ru.geekbrains.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.controller.ProductDto;
import ru.geekbrains.persist.model.Picture;
import ru.geekbrains.persist.model.Product;
import ru.geekbrains.persist.ProductRepository;
import ru.geekbrains.persist.ProductSpecifications;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final PictureService pictureService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, PictureService pictureService) {
        this.productRepository = productRepository;
        this.pictureService = pictureService;
    }

    @Override
    public Page<ProductDto> findWithFilters(ProductSearchFilters productSearchFilters) {
        Specification<Product> spec = Specification.where(null);
        Sort sortConfiguration;

        if (productSearchFilters.getCategoryFilter() != null && !productSearchFilters.getCategoryFilter().isBlank()) {
            if (productSearchFilters.getCategoryFilter().equals("No category")) {
                spec = spec.and(ProductSpecifications.emptyProductCategoryPrefix());
            } else {
                spec = spec.and(ProductSpecifications.productCategoryPrefix(productSearchFilters.getCategoryFilter()));
            }
        }

        if (productSearchFilters.getProductNameFilter() != null && !productSearchFilters.getProductNameFilter().isBlank()) {
            spec = spec.and(ProductSpecifications.productNamePrefix(productSearchFilters.getProductNameFilter()));
        }

        if (productSearchFilters.getMinCostFilter() != null) {
            spec = spec.and(ProductSpecifications.minCost(productSearchFilters.getMinCostFilter()));
        }

        if (productSearchFilters.getMaxCostFilter() != null) {
            spec = spec.and(ProductSpecifications.maxCost(productSearchFilters.getMaxCostFilter()));
        }

        if (productSearchFilters.getSize() == null || productSearchFilters.getSize() <= 0)
            productSearchFilters.setSize(5);

        if (productSearchFilters.getPage() == null || productSearchFilters.getPage() <= 0)
            productSearchFilters.setPage(1);

        if (productSearchFilters.getSortField() != null && !productSearchFilters.getSortField().isBlank())
            sortConfiguration = Sort.by(productSearchFilters.getSortField());
        else
            sortConfiguration = Sort.by("id");

        if ("desc".equals(productSearchFilters.getSortDirection()))
            sortConfiguration = sortConfiguration.descending();

        return productRepository.findAll(spec, PageRequest.of(productSearchFilters.getPage() - 1,
                productSearchFilters.getSize(), sortConfiguration))
                .map(product -> {
                    ProductDto productDto = new ProductDto(product.getId(),
                            product.getName(),
                            product.getCost(),
                            product.getCategories(),
                            product.getBrand()
                    );
                    if (product.getPictures().size() != 0)
                        productDto.setPictures(product.getPictures().stream()
                                .map(Picture::getId)
                                .collect(Collectors.toList())
                        );
                    return productDto;
                });
    }

    @Override
    public Optional<ProductDto> findById(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    ProductDto productDto = new ProductDto(product.getId(),
                            product.getName(),
                            product.getCost(),
                            product.getCategories(),
                            product.getBrand()
                    );
                    if (product.getPictures().size() != 0)
                        productDto.setPictures(product.getPictures().stream()
                                .map(Picture::getId)
                                .collect(Collectors.toList())
                        );
                    return productDto;
                });
    }

    @Override
    @Transactional
    public void save(ProductDto productDto) throws NotFoundException {
        Product product = (productDto.getId() != null) ? productRepository.findById(productDto.getId())
                .orElseThrow(() -> new NotFoundException("")) : new Product();

        product.setName(productDto.getName());
        product.setCost(productDto.getCost());
        product.setCategories(productDto.getCategories());
        product.setBrand(productDto.getBrand());

        if (productDto.getNewPictures() != null) {
            for (MultipartFile newPicture : productDto.getNewPictures()) {
                if (!("application/octet-stream").equals(newPicture.getContentType())) {
                    try {
                        product.getPictures().add(new Picture(null,
                                newPicture.getOriginalFilename(),
                                newPicture.getContentType(),
                                pictureService.createPicture(newPicture.getBytes()),
                                product
                        ));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductDto> findAll() {
        return productRepository.findAll().stream().map(product -> new ProductDto(product.getId(),
                        product.getName(),
                        product.getCost(),
                        product.getCategories(),
                        product.getBrand()
                        )).collect(Collectors.toList());
    }
}
