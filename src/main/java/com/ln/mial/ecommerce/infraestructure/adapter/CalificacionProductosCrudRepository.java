package com.ln.mial.ecommerce.infraestructure.adapter;

import com.ln.mial.ecommerce.infraestructure.entity.CalificacionProductosEntity;
import com.ln.mial.ecommerce.infraestructure.entity.ProductosEntity;
import com.ln.mial.ecommerce.infraestructure.entity.UsuariosEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CalificacionProductosCrudRepository extends CrudRepository<CalificacionProductosEntity, Integer> {
    List<CalificacionProductosEntity> findByProduct(ProductosEntity productEntity);
    List<CalificacionProductosEntity> findByUser(UsuariosEntity userEntity);
}
