package io.rachidassouani.eshopbackend.product;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.rachidassouani.eshopbackend.brand.BrandService;
import io.rachidassouani.eshopbackend.util.FileUploadService;
import io.rachidassouani.eshopcommon.dto.ProductRequest;
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
	public String saveProduct(ProductRequest productRequest, 
			RedirectAttributes redirectAttributes,
			@RequestParam("fileImage") MultipartFile multipartFile,
			@RequestParam(name="detailNames", required = false) String[] detailNames,
			@RequestParam(name="detailValues", required = false) String[] detailValues) {
		
		log.info("Saving new product");
		try {
			
			setProductDetails(detailNames, detailValues, productRequest);
			
			
			if (!multipartFile.isEmpty()) {
				String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
				productRequest.setMainImageName(fileName);
				
				// saving product
				Product savedProduct = productService.save(productRequest);
				
				String uploadDirectory = "../productImages/" + savedProduct.getCode();
				
				FileUploadService.saveFile(uploadDirectory, fileName, multipartFile);
				
				redirectAttributes.addFlashAttribute("successMessage", "The product has been saved successfully.");
				log.info("Product saved succefully");	
			
			} else {
				productService.save(productRequest);				
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/products";
	}
	
	private void setProductDetails(String[] detailNames, String[] detailValues, ProductRequest productRequest) {
		if (detailNames == null || detailNames.length == 0)
			return;
		
		for(int i = 0; i< detailNames.length; i++) {
			String detailName = detailNames[i];
			String detailValue = detailValues[i];
			
			if (!detailName.isEmpty() && !detailValue.isEmpty()) {
				productRequest.addProductDetail(detailName, detailValue);
			}
		}
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