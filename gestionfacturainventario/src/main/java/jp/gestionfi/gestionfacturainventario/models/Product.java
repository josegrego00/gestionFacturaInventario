package jp.gestionfi.gestionfacturainventario.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable= false) // esto hace q la columna en la base de datos no sea nula
    private String sku; // este es el codigo del producto (Podria decirse como el codigo de barra)

    @Column(nullable= false)
    private String name;
    
    @Column(nullable= false)
    private BigDecimal price;
    
    @Column(nullable= false)
    private String unit;

    @Column(nullable= false)
    private Long stock;

    

    public Product() {
    }

    public Product(String sku, String name, BigDecimal price, String unit, Long stock) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.stock = stock;
    }
    public Long getId() {
        return id;
    }
    public String getSku() {
        return sku;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public Long getStock() {
        return stock;
    }
    public void setStock(Long stock) {
        this.stock = stock;
    }

    //aplicando el GRASP experto esto con la idea que solo esta clase comprende.

     public void adjustStock(long delta) {
        long nuevo = (this.stock == null ? 0L : this.stock) + delta; //valida que el Stock no este ni en 0 ni en null
        if (nuevo < 0) {
            throw new IllegalStateException("Stock insuficiente para SKU: " + sku);
        }
        this.stock = nuevo;
    }

    public boolean decreaseIfAvailable(long quantity) {
        if (quantity <= 0) return false;
        long actual = (this.stock == null ? 0L : this.stock);
        if (actual < quantity) return false;
        this.stock = actual - quantity;
        return true;
    }

}
