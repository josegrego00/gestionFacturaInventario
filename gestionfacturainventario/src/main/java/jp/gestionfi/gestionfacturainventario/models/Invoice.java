package jp.gestionfi.gestionfacturainventario.models;

import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @Column(nullable = false)
    private LocalDateTime date;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true) // esto indica q esta relacionada con InvoiceItem con el atributo invoice
    private List<InvoiceItem> items;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal tax;

    @Column(nullable = false, precision = 14, scale = 2)
    private BigDecimal total;

    public Invoice() {
        this.items= new ArrayList<>();
    }

    public Invoice(Customer customer, LocalDateTime date, List<InvoiceItem> items, BigDecimal tax,
            BigDecimal total) {

        this.customer = customer;
        this.date = date;
        this.items = items;
        this.tax = tax;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void setItems(List<InvoiceItem> items) {
        this.items = items;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void calculateTotal() {
        if (items == null || items.isEmpty()) {
            this.total = BigDecimal.ZERO;
            return;

        }

        BigDecimal subtotal = BigDecimal.ZERO;

        for (InvoiceItem item : items) {
            subtotal = subtotal.add(item.getSubtotal());
        }

        BigDecimal taxAmount = subtotal.multiply(tax);
        this.total = subtotal.add(taxAmount);
    }

}
