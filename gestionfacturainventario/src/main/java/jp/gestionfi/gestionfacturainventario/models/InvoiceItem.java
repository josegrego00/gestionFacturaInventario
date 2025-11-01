package jp.gestionfi.gestionfacturainventario.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class InvoiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @Column(nullable = false)
    private Long amount;
    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal price;

    @ManyToOne
    private Invoice invoice;

    public InvoiceItem() {
    }

    public InvoiceItem(Product product, Long amount, BigDecimal price, Invoice invoice) {

        this.product = product;
        this.amount = amount;
        this.price = price;
        this.invoice = invoice;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public BigDecimal getSubtotal() {
        if (price == null || amount == null) {
            return BigDecimal.ZERO;
        }
        return price.multiply(BigDecimal.valueOf(amount));
    }

}
