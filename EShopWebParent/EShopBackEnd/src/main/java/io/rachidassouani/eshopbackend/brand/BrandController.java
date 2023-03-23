package io.rachidassouani.eshopbackend.brand;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
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

import io.rachidassouani.eshopbackend.util.Constant;
import io.rachidassouani.eshopbackend.util.FileUploadService;
import io.rachidassouani.eshopbackend.util.RandomCodeService;
import io.rachidassouani.eshopcommon.model.Brand;
import io.rachidassouani.eshopcommon.model.Category;

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
	
	@GetMapping("new")
	public String newBrandForm(Model model) {
		List<Category> categoriesInbrandsForm = brandService.findAllCategoriesInBrandsForm();
			
		model.addAttribute("brand", new Brand());
		model.addAttribute("categoriesInbrandsForm", categoriesInbrandsForm);
		model.addAttribute("pageTitle", "Create new brand");
		
		return "brands/brandForm";
	}
	
	@PostMapping("save")
	public String saveCategory(Brand brand,
			@RequestParam("fileLogo") MultipartFile multipartFile,
			RedirectAttributes redirectAttributes) throws IOException {
			
		
		if (brand.getId() == null) {
			// setting the code to the new brand
			brand.setCode(RandomCodeService.generatCode());
		}
		
		// getting the name of the image from multipartfile
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		brand.setLogo(fileName);
		
		// saving brand
		Brand savedBrand = brandService.saveBrand(brand);	
		
		// upload category's image 
		String brandsImageDir = "../brandsLogo/"+savedBrand.getCode();
		FileUploadService.saveFile(brandsImageDir, fileName, multipartFile);
		
		redirectAttributes.addFlashAttribute("successMessage", "The Brand "+ savedBrand.getName() + " has been saved successfully.");
		
		return "redirect:/brands";
	}
	
	@GetMapping("delete/{code}")
	public String delete(@PathVariable("code") String code, Model model, 
			RedirectAttributes redirectAttributes) {
		
		try {
			brandService.deleteCategoryByCode(code);
			redirectAttributes.addFlashAttribute("successMessage", "Brand code " + code
					+" has been deleted succefully");
			
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
			e.printStackTrace();
		}
		
		return "redirect:/brands";	
	}
	
	@GetMapping("edit/{code}")
	public String editCategoryForm(@PathVariable("code") String code,Model model,
			RedirectAttributes redirectAttributes) {
		
		try {
			Brand brand = brandService.findBrandByCode(code);
			List<Category> categoriesInbrandsForm = brandService.findAllCategoriesInBrandsForm();
			 
			model.addAttribute("brand", brand);
			model.addAttribute("categoriesInbrandsForm", categoriesInbrandsForm);
			model.addAttribute("pageTitle", "Edit existing brand (CODE: "+code+")");
			
			return "brands/brandForm";
		
		} catch (BrandNotFoundException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		}	
		return "redirect:/brands";
	}
}