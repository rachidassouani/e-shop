package io.rachidassouani.eshopbackend.product;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.rachidassouani.eshopbackend.brand.BrandService;
import io.rachidassouani.eshopcommon.model.Brand;
import io.rachidassouani.eshopcommon.model.Product;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("products")
@Slf4j
public class ProductController {

	private final ProductService productService;
	private final BrandService brandService;	
	
	public ProductController(ProductService productService, BrandService brandService) {
		this.productService = productService;
		this.brandService = brandService;
	}
	
	@GetMapping
	public String findAllProducts(Model model) {
		// finding list of products
		List<Product> allProducts = productService.findAllProducts();
		
		// assign the list of products to the model
		model.addAttribute("allProducts", allProducts);
		
		return "products/products";
	}
	
	@GetMapping("new")
	public String newProductForm(Model model) {
		List<Brand> allBrands = brandService.findAllBrands();
		ProductRequest productRequest = ProductRequest.builder()
				.enabled(true)
				.inStock(true)
				.build();
		
		model.addAttribute("allBrands", allBrands);
		model.addAttribute("productRequest", productRequest);
		model.addAttribute("pageTitle", "Create new product");
		return "products/productForm";
	}
	
	@PostMapping("save")
	public String saveProduct(ProductRequest productRequest, RedirectAttributes redirectAttributes) {
		
		log.info("Saving new product");
		try {
			productService.save(productRequest);
			redirectAttributes.addFlashAttribute("successMessage", "The product has been saved successfully.");
			
			log.info("Product saved succefully");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/products";
	}
	
	@GetMapping("{code}/enabled/{status}")
	public String enableProductStatusByCode(@PathVariable("code") String code, 
			@PathVariable("status") boolean status, 
			RedirectAttributes redirectAttributes) {
		
		log.info("enabling / disabling product status by code");
		
		// updating product status by its code
		productService.enableProductStatusByCode(code, status);
		
		// check if user wants to enable or disable the product
		final String messageStatus = (status) ? "enabled" : "disabled";
		
		// confirmation message
		final String message = "The product code " + code + " has been " + messageStatus;
		
		redirectAttributes.addFlashAttribute("successMessage", message);
		
		return "redirect:/products";
	}
	
	@GetMapping("delete/{code}")
	public String deleteUser(@PathVariable("code") String code, Model model, 
			RedirectAttributes redirectAttributes) {
		
		log.info("Deleting product: " + code);
		try {
			
			productService.deleteProductByCode(code);
			
			redirectAttributes.addFlashAttribute("successMessage", "Product code " + code
					+" has been deleted succefully");
			
		} catch (ProductNotFoundException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
			
		}
		return "redirect:/products";
	}
	
}