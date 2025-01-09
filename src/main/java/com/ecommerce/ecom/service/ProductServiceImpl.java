package com.ecommerce.ecom.service;

import com.ecommerce.ecom.exceptions.APIException;
import com.ecommerce.ecom.exceptions.ResourceNotFoundException;
import com.ecommerce.ecom.model.Category;
import com.ecommerce.ecom.model.Product;
import com.ecommerce.ecom.payload.ProductDTO;
import com.ecommerce.ecom.payload.ProductResponse;
import com.ecommerce.ecom.repositories.CategoryRepository;
import com.ecommerce.ecom.repositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    public ProductServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));

        boolean isProductNotPresent=true;

        List<Product> products=category.getProducts();

        for(int i=0;i<products.size();i++){
            if(products.get(i).getProductName().equals(productDTO.getProductName())){
                isProductNotPresent=false;
                break;
            }
        }

        if(!isProductNotPresent){
            throw new APIException("Product already exists");
        }
        else{
            Product product=modelMapper.map(productDTO, Product.class);
            product.setCategory(category);
            product.setImage("default.png");
            double specialPrice= product.getPrice()*((100 - product.getDiscount())/100);
            product.setSpecialPrice(specialPrice);
            Product savedProduct=productRepository.save(product);
            return modelMapper.map(savedProduct, ProductDTO.class);
        }



    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber,Integer pageSize,String sortBy,String sortOrder) {
        Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc")? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails= PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        Page<Product> pageProducts=productRepository.findAll(pageDetails);



        List<Product> products=pageProducts.getContent();
        List<ProductDTO> productDTOS=products.stream().map(product->modelMapper.map(product, ProductDTO.class)).toList();

        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getTotalElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(pageProducts.isLast());

        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","categoryId",categoryId));
        List<Product> products=productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productDTOS=products.stream().map(product->modelMapper.map(product, ProductDTO.class)).toList();

        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword) {

        List<Product> products=productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%');
        List<ProductDTO> productDTOS=products.stream().map(product->modelMapper.map(product, ProductDTO.class)).toList();

        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;

    }

    @Override
    public ProductDTO updateProduct(ProductDTO productDTO, Long productId,Long categoryId) {
        //get existing product from db
        //update
        //save
        Product productFromDb=productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));
        Product product=modelMapper.map(productDTO, Product.class);
        productFromDb.setPrice(product.getPrice());
        productFromDb.setDiscount(product.getDiscount());
        productFromDb.setCategory(product.getCategory());
        productFromDb.setProductName(product.getProductName());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setDescription(product.getDescription());

        double specialPrice = product.getPrice() * ((100 - product.getDiscount()) / 100);
        productFromDb.setSpecialPrice(specialPrice);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        productFromDb.setCategory(category);

        Product savedProduct=productRepository.save(productFromDb);
        return modelMapper.map(savedProduct, ProductDTO.class);



    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product productToDelete=productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));
        productRepository.delete(productToDelete);
        return modelMapper.map(productToDelete, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        //for now we are uploading to file system later we will shiftto cloud
        Product productFromDb =productRepository.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));

       //Path is injected from application.properties

        String filename=fileService.uploadImage(path,image);
        productFromDb.setImage(filename);
        Product updatedProduct=productRepository.save(productFromDb);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }




}
