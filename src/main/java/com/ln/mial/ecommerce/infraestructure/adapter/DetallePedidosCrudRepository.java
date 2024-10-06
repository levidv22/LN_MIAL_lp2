package com.ln.mial.ecommerce.infraestructure.adapter;

import com.ln.mial.ecommerce.infraestructure.entity.DetallePedidosEntity;
import com.ln.mial.ecommerce.infraestructure.entity.PedidosEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DetallePedidosCrudRepository extends CrudRepository<DetallePedidosEntity, Integer> {
    List<DetallePedidosEntity> findByOrder(PedidosEntity orderEntity);
}
