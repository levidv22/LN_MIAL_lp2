package com.ln.mial.ecommerce.infraestructure.adapter;

import com.ln.mial.ecommerce.infraestructure.entity.PedidosEntity;
import com.ln.mial.ecommerce.infraestructure.entity.UsuariosEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import com.ln.mial.ecommerce.infraestructure.entity.StatusPedido;

public interface PedidosCrudRepository extends CrudRepository<PedidosEntity, Integer> {
    List<PedidosEntity> findByUser(UsuariosEntity userEntity);
    List<PedidosEntity> findByUserAndStatusPedido(UsuariosEntity userEntity, StatusPedido status);
    List<PedidosEntity> findByStatusPedido(StatusPedido status);
}
