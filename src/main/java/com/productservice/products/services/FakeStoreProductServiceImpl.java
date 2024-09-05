package com.productservice.products.services;

import com.productservice.products.clients.fakestoreapi.FakeStoreClient;
import com.productservice.products.clients.fakestoreapi.FakeStoreProductDto;
import com.productservice.products.dtos.ProductDto;
import com.productservice.products.models.Category;
import com.productservice.products.models.Product;
import io.micrometer.common.lang.Nullable;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class  FakeStoreProductServiceImpl implements ProductService{

    private RestTemplateBuilder restTemplateBuilder;
    private FakeStoreClient fakeStoreClient;
    private Map<Long, Object> fakeStoreProducts = new HashMap<>();

    public FakeStoreProductServiceImpl(RestTemplateBuilder restTemplateBuilder, FakeStoreClient fakeStoreClient) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.fakeStoreClient = fakeStoreClient;
    }

    private <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod, String url, @Nullable Object request,
                                                   Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.requestFactory(
                HttpComponentsClientHttpRequestFactory.class
        ).build();

        RequestCallback requestCallback =restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }


    private Product convertFakeStoreProductDtoToProduct(FakeStoreProductDto productDto) {

        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setPrice(productDto.getPrice());
        // category is a class and we cannot directly map the values of it(String -> class)
        Category category = new Category();
        category.setName(productDto.getCategory());
        product.setCategory(category);
        product.setImageUrl(productDto.getImage());
        return product;

    }


    @Override
    public List<Product> getAllProducts() {
        List<FakeStoreProductDto> fakeStoreProductDtos = fakeStoreClient.getAllProducts();
        List<Product> answer = new ArrayList<>();
        for(FakeStoreProductDto productDto : fakeStoreProductDtos)
        {
            answer.add(convertFakeStoreProductDtoToProduct(productDto));
        }

        return answer;
    }
    /*
    Return a Product object with all the details of the fetched product.
    The ID of the category will be null but the name of the category shall be
    correct.
     */

    @Override
    public Product getSingleProduct(Long productId) {
//        FakeStoreProductDto fakeStoreProductDto = (FakeStoreProductDto) redisTemplate.opsForHash().get(productId, "PRODUCTS");
//
//        if (fakeStoreProductDto != null) {
//            return Optional.of(convertFakeStoreProductDtoToProduct(fakeStoreProductDto));
//        }
//        RestTemplate restTemplate = restTemplateBuilder.build();
//        ResponseEntity<FakeStoreProductDto> response = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}",
//                FakeStoreProductDto.class, productId);
//
//        FakeStoreProductDto productDto = response.getBody();
//        redisTemplate.opsForHash().put(productId, "PRODUCTS", productDto);
//        if (productDto == null) {
//            return Optional.empty();
//        }
//
//        return Optional.of(convertFakeStoreProductDtoToProduct(productDto));
        FakeStoreProductDto fakeStoreProductDto = fakeStoreClient.getSingleProduct(productId);

        return convertFakeStoreProductDtoToProduct(fakeStoreProductDto);

    }


    @Override
    public Product addNewProduct(ProductDto product) {

        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<FakeStoreProductDto> response = restTemplate.postForEntity("https://fakestoreapi.com/products", product, FakeStoreProductDto.class);
        FakeStoreProductDto ProductDto = response.getBody();
        return convertFakeStoreProductDtoToProduct(ProductDto);

    }

    @Override
    public Product updateProduct(Long productId, Product product) {

        RestTemplate restTemplate = restTemplateBuilder.requestFactory( HttpComponentsClientHttpRequestFactory.class).build();

        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImageUrl());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setCategory(product.getCategory().getName());

        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = requestForEntity(
                HttpMethod.PATCH,
                "https://fakestoreapi.com/products/{id}",
                fakeStoreProductDto,
                FakeStoreProductDto.class,
                productId
        );

        FakeStoreProductDto fakeStoreProductDtoResponse = restTemplate.patchForObject(
                "https://fakestoreapi.com/products/{id}",
                fakeStoreProductDto,
                FakeStoreProductDto.class,
                productId
        );

        return convertFakeStoreProductDtoToProduct(fakeStoreProductDtoResponse);

    }

    // Replacement is basically a put request
    @Override
    public Product replaceProduct(Long productId, Product product) {
        RestTemplate restTemplate = restTemplateBuilder.requestFactory(HttpComponentsClientHttpRequestFactory.class).build();

        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setImage(product.getImageUrl());
        fakeStoreProductDto.setCategory(product.getCategory().getDescription());

        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = requestForEntity(
                HttpMethod.PUT,
                "https://fakestoreapi.com/products/{id}",
                fakeStoreProductDto,
                FakeStoreProductDto.class,
                productId);

        // using patchforObject can also be done, it directly returns the object

//            FakeStoreProductDto fakeStoreProductDtoResponseEntity1 = restTemplate.patchForObject(
//                "https://fakestoreapi.com/products/{id}",
//                fakeStoreProductDto,
//                FakeStoreProductDto.class,
//                productId);
        // for object return no need to do getBody

            return convertFakeStoreProductDtoToProduct(fakeStoreProductDtoResponseEntity.getBody());

    }

    @Override
    public boolean deleteProduct(Long productId) {

        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.delete("https://fakestoreapi.com/products/{id}", productId);
        return true;


    }
}
