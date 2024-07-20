package vn.nguyendong.laptopshop.controller.admin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.nguyendong.laptopshop.domain.Product;
import vn.nguyendong.laptopshop.service.ProductService;
import vn.nguyendong.laptopshop.service.UploadService;

@Controller
public class ProductController {
    private final ProductService productService;
    private final UploadService uploadService;

    public ProductController(ProductService productService, UploadService uploadService) {
        this.productService = productService;
        this.uploadService = uploadService;
    }

    @GetMapping("/admin/product")
    public String getProductPage(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        int pageSize = 6;
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Product> pageProducts = this.productService.getAllProducts(pageable);
        List<Product> products = pageProducts.getContent();

        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageProducts.getTotalPages());

        return "admin/product/show";
    }

    @GetMapping("/admin/product/{productId}")
    public String getProductDetailPage(Model model, @PathVariable long productId) {
        Product product = this.productService.getProductById(productId);
        model.addAttribute("product", product);
        return "admin/product/detail";
    }

    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    @PostMapping("/admin/product/create")
    public String createProduct(Model model,
            @Valid @ModelAttribute("newProduct") Product product,
            BindingResult bindingResult,
            @RequestParam("imageUploadFile") MultipartFile file) {
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(">>>>>>>>>>>>>>>>>>" + error.getField() + " - " +
                    error.getDefaultMessage());
        }
        if (bindingResult.hasErrors()) {
            return "admin/product/create";
        }

        String productImageName = this.uploadService.handleSaveUploadFile(file, "product");

        product.setImage(productImageName);

        this.productService.handleSaveProduct(product);

        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/update/{productId}")
    public String getUpdateProductPage(Model model, @PathVariable long productId) {
        Product product = this.productService.getProductById(productId);
        model.addAttribute("newProduct", product);
        return "admin/product/update";
    }

    @PostMapping("/admin/product/update")
    public String updateProduct(Model model,
            @Valid @ModelAttribute("newProduct") Product product,
            BindingResult bindingResult,
            @RequestParam("imageUploadFile") MultipartFile file) {

        if (bindingResult.hasErrors()) {
            Product curProduct = this.productService.getProductById(product.getId());
            model.addAttribute("originImage", curProduct.getImage());
            return "admin/product/update";
        }

        Product currentProduct = this.productService.getProductById(product.getId());
        if (currentProduct != null) {
            if (!file.isEmpty()) {
                String img = this.uploadService.handleSaveUploadFile(file, "product");
                currentProduct.setImage(img);
            }
            currentProduct.setName(product.getName());
            currentProduct.setPrice(product.getPrice());
            currentProduct.setDetailDescription(product.getDetailDescription());
            currentProduct.setShortDescription(product.getShortDescription());
            currentProduct.setQuantity(product.getQuantity());
            currentProduct.setFactory(product.getFactory());
            currentProduct.setTarget(product.getTarget());
            this.productService.handleSaveProduct(currentProduct);
        }
        return "redirect:/admin/product";
    }

    @GetMapping("/admin/product/delete/{productId}")
    public String getDeleteProductPage(Model model, @PathVariable long productId) {
        model.addAttribute("id", productId);
        model.addAttribute("newProduct", new Product());
        return "admin/product/delete";
    }

    @PostMapping("/admin/product/delete")
    public String deleteProduct(Model model, @ModelAttribute("newProduct") Product product) {
        this.productService.handleDeleteProduct(product.getId());
        return "redirect:/admin/product";
    }
}
