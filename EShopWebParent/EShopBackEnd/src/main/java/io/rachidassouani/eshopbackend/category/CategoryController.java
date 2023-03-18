package io.rachidassouani.eshopbackend.category;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import io.rachidassouani.eshopbackend.util.FileUploadService;
import io.rachidassouani.eshopbackend.util.RandomCodeService;
import io.rachidassouani.eshopcommon.model.Category;

@Controller
@RequestMapping("categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public String findAllCategories(Model model) {
		
		//getting all categories from the database using categoryService
		List<Category>allCategories  = categoryService.findAllCategories();
		
		//setting list of returned categories to the model
		model.addAttribute("allCategories", allCategories);
		
		return "categories/categories";
	}
	
	@GetMapping("new")
	public String newCategoryForm(Model model) {
		
		model.addAttribute("category", new Category());
		model.addAttribute("pageTitle", "Create new category");
		
		List<Category> categoriesInForm = categoryService.findAllCategoriesForForm();
		model.addAttribute("categoriesInForm", categoriesInForm);
		
		return "categories/categoryForm";
	}
	
	@PostMapping("save")
	public String saveCategory(Category category,
			@RequestParam("fileImage") MultipartFile multipartFile,
			RedirectAttributes redirectAttributes) throws IOException {
			
		// setting the code to the new category
		category.setCode(RandomCodeService.generatCode());
		
		
		// getting the name of the image from multipartfile
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		category.setImageName(fileName);
		
		// saving category
		Category savedCategory = categoryService.saveCategory(category);
		
		// upload category's image 
		String categoriesImageDir = "../categoriesImage/"+savedCategory.getCode();
		FileUploadService.saveFile(categoriesImageDir, fileName, multipartFile);
		
		redirectAttributes.addFlashAttribute("successMessage", "The Category has been saved successfully.");
		
		return "redirect:/categories";
	}
	
	@GetMapping("edit/{code}")
	public String editCategoryForm(@PathVariable("code") String code,Model model,
			RedirectAttributes redirectAttributes) {
		
		try {
			Category category = categoryService.findCategoryByCode(code);
			List<Category> categoriesInForm = categoryService.findAllCategoriesForForm();
			
			model.addAttribute("category", category);
			model.addAttribute("categoriesInForm", categoriesInForm);
			model.addAttribute("pageTitle", "Edit existing category (CODE: "+code+")");
			
			return "categories/categoryForm";
		
		} catch (CategoryNotFoundException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
		}
		
		return "redirect:/categories";
	}
	
	@GetMapping("delete/{code}")
	public String deleteUser(@PathVariable("code") String code, Model model, 
			RedirectAttributes redirectAttributes) {
		
		try {
			
			categoryService.deleteCategoryByCode(code);
			
			redirectAttributes.addFlashAttribute("successMessage", "Category code " + code
					+" has been deleted succefully");
			
		} catch (CategoryNotFoundException e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
			
		}
		return "redirect:/categories";
	}
}
