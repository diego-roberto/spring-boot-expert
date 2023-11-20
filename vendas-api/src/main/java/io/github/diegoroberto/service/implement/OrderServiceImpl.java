package io.github.diegoroberto.service.implement;

import io.github.diegoroberto.config.internationalization.InternationalizationConfig;
import io.github.diegoroberto.domain.entity.Client;
import io.github.diegoroberto.domain.entity.Order;
import io.github.diegoroberto.domain.entity.OrderItem;
import io.github.diegoroberto.domain.entity.Product;
import io.github.diegoroberto.domain.enums.OrderStatus;
import io.github.diegoroberto.domain.repository.ClientRepository;
import io.github.diegoroberto.domain.repository.OrderItemRepository;
import io.github.diegoroberto.domain.repository.OrderRepository;
import io.github.diegoroberto.domain.repository.ProductRepository;
import io.github.diegoroberto.exception.BusinessException;
import io.github.diegoroberto.exception.OrderNotFoundException;
import io.github.diegoroberto.rest.dto.OrderDTO;
import io.github.diegoroberto.rest.dto.OrderInfoDTO;
import io.github.diegoroberto.rest.dto.OrderItemDTO;
import io.github.diegoroberto.rest.dto.OrderItemInfoDTO;
import io.github.diegoroberto.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;

    private final ModelMapper modelMapper;

    private final InternationalizationConfig i18n;


    @Override
    @Transactional
    public Long save(OrderDTO dto) {
        Long clientId = dto.getClient();
        Client client = clientRepository
                .findById(clientId)
                .orElseThrow(() -> new BusinessException(i18n.getMessage("msg.client.invalid-id")));

        Order order = new Order();
        order.setTotal(dto.getTotal());
        order.setOrderDate(LocalDate.now());
        order.setClient(client);
        order.setStatus(OrderStatus.REALIZADO);

        List<OrderItemDTO> itemsDTO = dto.getItems();
        List<OrderItem> orderItems = convertItems(order, convertToOrderItemList(itemsDTO));
        orderRepository.save(order);
        orderItemRepository.saveAll(orderItems);
        order.setItems(orderItems);
        return order.getId();
    }

    @Override
    public OrderInfoDTO getFullOrder(Long id) {
        return orderRepository.findByIdFetchItems(id)
                .map(order -> {
                    OrderInfoDTO orderInfoDTO = convertToOrderInfoDTO(order);
                    orderInfoDTO.setItems(convertToOrderItemInfoDTOList(order.getItems()));
                    return orderInfoDTO;
                })
                .orElseThrow(() -> new ResponseStatusException(
                                            HttpStatus.NOT_FOUND,
                                            i18n.getMessage("msg.order.not-found"))
                );
    }

    @Override
    @Transactional
    public void updateStatus(Long id, OrderStatus status) {
        orderRepository
                .findById(id)
                .map( order -> {
                    order.setStatus(status);
                    return orderRepository.save(order);
                }).orElseThrow(() -> new OrderNotFoundException(i18n.getMessage("msg.order.not-found")));
    }

    private OrderInfoDTO convertToOrderInfoDTO(Order order) {
        return modelMapper.map(order, OrderInfoDTO.class);
    }

    private List<OrderItemInfoDTO> convertToOrderItemInfoDTOList(List<OrderItem> items) {
        TypeToken<List<OrderItemInfoDTO>> typeToken = new TypeToken<List<OrderItemInfoDTO>>() {};
        return modelMapper.map(items, typeToken.getType());
    }

    private List<OrderItem> convertToOrderItemList(List<OrderItemDTO> dtos){
        return dtos.stream().map(i -> modelMapper.map(i, OrderItem.class)).collect(Collectors.toList());
    }

    private List<OrderItem> convertItems(Order order, List<OrderItem> items){
        if(items.isEmpty()){
            throw new BusinessException(i18n.getMessage("msg.order.empty"));
        }

        return items
                .stream()
                .map(dto -> {
                    Long productId = dto.getProduct().getId();
                    Product product = productRepository
                            .findById(productId)
                            .orElseThrow(() -> new BusinessException(i18n.getMessage("msg.product.invalid")+": "+ productId));

                    OrderItem orderItem = modelMapper.map(dto, OrderItem.class);
                    orderItem.setOrder(order);
                    orderItem.setProduct(product);
                    return orderItem;
                }).collect(Collectors.toList());
    }
}