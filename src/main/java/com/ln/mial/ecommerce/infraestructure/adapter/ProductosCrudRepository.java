package com.ln.mial.ecommerce.infraestructure.adapter;

import com.ln.mial.ecommerce.infraestructure.entity.CategoriasEntity;
import com.ln.mial.ecommerce.infraestructure.entity.ProductosEntity;
import com.ln.mial.ecommerce.infraestructure.entity.UsuariosEntity;
import org.springframework.data.repository.CrudRepository;

public interface ProductosCrudRepository extends CrudRepository<ProductosEntity, Integer>{
    Iterable<ProductosEntity> findByUserEntity(UsuariosEntity userEntity);
    Iterable<ProductosEntity> findByCategory(CategoriasEntity category);
}
