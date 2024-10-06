package com.ln.mial.ecommerce.infraestructure.adapter;

import jakarta.transaction.Transactional;
import com.ln.mial.ecommerce.infraestructure.entity.DetallePedidosEntity;
import com.ln.mial.ecommerce.infraestructure.entity.PedidosEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.ln.mial.ecommerce.app.repository.DetallePedidosRepository;

@Repository
public class DetallePedidosRepositoryImpl implements DetallePedidosRepository {
    private final DetallePedidosCrudRepository orderDetailsCrudRepository;

    public DetallePedidosRepositoryImpl(DetallePedidosCrudRepository orderDetailsCrudRepository) {
        this.orderDetailsCrudRepository = orderDetailsCrudRepository;
    }

    @Override
    public List<DetallePedidosEntity> getOrderDetails() {
        return (List<DetallePedidosEntity>) orderDetailsCrudRepository.findAll();
    }

    @Override
    public DetallePedidosEntity getOrderDetailById(Integer id) {
        return orderDetailsCrudRepository.findById(id).orElse(null);
    }

    @Override
    public List<DetallePedidosEntity> getOrderDetailsByOrder(PedidosEntity orderEntity) {
        return orderDetailsCrudRepository.findByOrder(orderEntity);
    }

    @Override
    public DetallePedidosEntity saveOrderDetail(DetallePedidosEntity orderDetailsEntity) {
        return orderDetailsCrudRepository.save(orderDetailsEntity);
    }

    @Override
    @Transactional
    public boolean deleteOrderDetailById(Integer id) {
        orderDetailsCrudRepository.deleteById(id);
        return true;
    }
}
