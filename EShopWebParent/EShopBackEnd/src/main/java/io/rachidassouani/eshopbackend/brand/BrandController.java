package io.rachidassouani.eshopbackend.brand;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import io.rachidassouani.eshopbackend.util.Constant;
import io.rachidassouani.eshopcommon.model.Brand;

@Controller
@RequestMapping("brands")
public class BrandController {
	
	private final BrandService brandService;
	
	public BrandController(BrandService brandService) {
		this.brandService = brandService;
	}
	
	@GetMapping
	public String findAllBrands(Model model) {
		return findBrandsPerPage(1, model);
	}
	
	@GetMapping("page/{pageNumber}")
	public String findBrandsPerPage(@PathVariable("pageNumber") int pageNumber, Model model) {
		
		// getting brands by page from the database using brandService
		Page<Brand> pageBrands = brandService.findBrandsPerPage(pageNumber);
		
		// casting the brands page to list of brands
		List<Brand> allBrands = pageBrands.getContent();
		
		long totalBrands = pageBrands.getTotalElements();
		long startCount = (pageNumber - 1) * Constant.BRANDS_PER_PAGE + 1;
		long endCount = startCount + Constant.BRANDS_PER_PAGE - 1;
		
		if (endCount > totalBrands) {
			endCount = totalBrands;
		}
		
		//setting list of returned brands to the model
		model.addAttribute("allBrands", allBrands);
		model.addAttribute("startCount", startCount);
		model.addAttribute("endCount", endCount);
		model.addAttribute("currentPage", pageNumber);
		model.addAttribute("totalBrands", totalBrands);
		model.addAttribute("totalPages", pageBrands.getTotalPages());	
		
		return "brands/brands";
	}
}