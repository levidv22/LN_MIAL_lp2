package com.ln.mial.ecommerce.app.service;

import java.util.List;
import com.ln.mial.ecommerce.infraestructure.entity.AlmacenEntity;
import com.ln.mial.ecommerce.infraestructure.entity.ProductosEntity;
import com.ln.mial.ecommerce.app.repository.AlmacenRepository;

public class AlmacenService {

    private final AlmacenRepository stockRepository;

    public AlmacenService(AlmacenRepository stockRepository) {
        this.stockRepository = stockRepository;
    }
    
    //es un puente de todos los metodos del repository que conectan con el controller

    public AlmacenEntity saveStock(AlmacenEntity stockEntity) {
        return stockRepository.saveStock(stockEntity);
    }

    public List<AlmacenEntity> getStockByProductEntity(ProductosEntity productosEntity) {
        return stockRepository.getStockByProductEntity(productosEntity);
    }

    public boolean deleteStockById(Integer id) {
        return stockRepository.deleteStockById(id);
    }
    
    public AlmacenEntity getStockByProduct(ProductosEntity product) {
        List<AlmacenEntity> stocks = stockRepository.getStockByProductEntity(product);
        return stocks.isEmpty() ? null : stocks.get(0);  // Devuelve el primer registro si existe
    }

    public AlmacenEntity updateStock(AlmacenEntity existingStock, int newEntrada) {
        existingStock.setEntradas(existingStock.getEntradas() + newEntrada);
        existingStock.setBalance(existingStock.getBalance() + newEntrada);
        return stockRepository.saveStock(existingStock);
    }

}
