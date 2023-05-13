package io.rachidassouani.eshopbackend.product;

import io.rachidassouani.eshopcommon.dto.ProductRequest;
import io.rachidassouani.eshopcommon.model.Product;

public interface ProductMapper {
	ProductRequest modelToDTO(Product product);
	Product DTOToModel(ProductRequest productRequest);
}
