package vn.nguyendong.laptopshop.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import vn.nguyendong.laptopshop.service.validator.StrongPassword;

/*
 * @NotNull: Kiểm tra trường đó không được là null nhưng có thể là chuỗi rỗng
 * hoặc chỉ chứa khoảng trắng.
 * 
 * @NotEmpty: Kiểm tra trường đó không được là null và chiều dài của chuỗi phải
 * lớn hơn 0, nhưng nó có thể chứa khoảng trắng.
 * 
 * @NotBlank: Kiểm tra trường đó không được là null, chiều dài sau khi cắt
 * khoảng trắng phải lớn hơn 0, và không được chỉ chứa khoảng trắng.
 * 
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 2, message = "Password must be at least 2 characters long")
    @StrongPassword(message = "Mật khẩu phải dài ít nhất 8 ký tự và bao gồm kết hợp của chữ cái viết hoa, chữ cái viết thường, số, và ký tự đặc biệt.")
    private String password;

    @NotBlank(message = "Full name is required")
    @Size(min = 2, message = "Full name must be at least 2 characters long")
    private String fullName;

    private String address;

    private String phone;

    private String avatar;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    @OneToOne(mappedBy = "user")
    private Cart cart;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", password=" + password + ", fullName=" + fullName
                + ", address=" + address + ", phone=" + phone + ", avatar=" + avatar + ", role=" + role + ", orders="
                + orders + ", cart=" + cart + "]";
    }

}
