package vn.nguyendong.laptopshop.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Product name is required")
    private String name;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0", inclusive = false, message = "Price must be greater than 0")
    private double price;

    // @NotBlank(message = "Product image is required")
    private String image;

    @NotBlank(message = "Detail description is required")
    @Size(min = 2, message = "Detail description must be at least 2 characters")
    private String detailDescription;

    @NotBlank(message = "Short description is required")
    @Size(min = 2, message = "Short description must be at least 2 characters")
    private String shortDescription;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be greater than or equal to 1")
    private long quantity;

    private long sold;

    private String factory;
    private String target;

    // // bidirectional
    // @OneToMany(mappedBy = "product")
    // private List<OrderDetail> orderDetails;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetailDescription() {
        return detailDescription;
    }

    public void setDetailDescription(String detailDescription) {
        this.detailDescription = detailDescription;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getSold() {
        return sold;
    }

    public void setSold(long sold) {
        this.sold = sold;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", price=" + price + ", image=" + image + ", detailDescription="
                + detailDescription + ", shortDescription=" + shortDescription + ", quantity=" + quantity + ", sold="
                + sold + ", factory=" + factory + ", target=" + target + "]";
    }

}
