package io.rachidassouani.eshopbackend.product;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.rachidassouani.eshopcommon.model.Product;

@Controller
@RequestMapping("products")
public class ProductController {

	private final ProductService productService;	
	
	public ProductController(ProductService productService) {
		this.productService = productService;
	}
	
	@GetMapping
	public String findAllProducts(Model model) {
		// finding list of products
		List<Product> allProducts = productService.findAllProducts();
		
		// assign the list of products to the model
		model.addAttribute("allProducts", allProducts);
		
		return "products/products";
	}
}
