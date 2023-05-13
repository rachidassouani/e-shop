package io.rachidassouani.eshopbackend.product;

import org.springframework.stereotype.Service;

import io.rachidassouani.eshopbackend.util.RandomCodeService;
import io.rachidassouani.eshopcommon.dto.ProductRequest;
import io.rachidassouani.eshopcommon.model.Product;

@Service
public class ProductMapperImpl implements ProductMapper {

	@Override
	public ProductRequest modelToDTO(Product product) {
		ProductRequest productRequest = ProductRequest.builder()
				.code(product.getCode())
				.name(product.getName())
				.alias(product.getAlias())
				.inStock(product.isInStock())
				.enabled(product.isEnabled())
				.fullDescription(product.getFullDescription())
				.shortDescription(product.getShortDescription())
				.cost(product.getCost())
				.price(product.getPrice())
				.discountPercent(product.getDiscountPercent())
				.mainImageName(product.getMainImageName())
				.length(product.getLength())
				.width(product.getWidth())
				.height(product.getHeight())
				.weight(product.getWeight())
				.createdTime(product.getCreatedTime())
				.updatedTime(product.getUpdatedTime())
				.brandCode(product.getBrand().getCode())
				.categoryCode(product.getCategory().getCode())
				.build();
		
		return productRequest;
	}

	@Override
	public Product DTOToModel(ProductRequest productRequest) {

		Product product = Product.builder()
				.code(RandomCodeService.generatCode())
				.name(productRequest.getName())
				.alias(productRequest.getAlias())
				//.brand(productRequest.getBrandCode())
				//.category(category)
				.enabled(productRequest.isEnabled())
				.inStock(productRequest.isInStock())
				.shortDescription(productRequest.getShortDescription())
				.fullDescription(productRequest.getFullDescription())
				.cost(productRequest.getCost())
				.price(productRequest.getPrice())
				.discountPercent(productRequest.getDiscountPercent())
				.mainImageName(productRequest.getMainImageName())
				.length(productRequest.getLength())
				.width(productRequest.getWidth())
				.height(productRequest.getHeight())
				.weight(productRequest.getWeight())				
				
				.createdTime(productRequest.getCreatedTime())
				.updatedTime(productRequest.getUpdatedTime())
				.build();
		
		return product;
	}

}
