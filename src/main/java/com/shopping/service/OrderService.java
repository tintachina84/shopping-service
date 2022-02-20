package com.shopping.service;

import com.shopping.dto.OrderDto;
import com.shopping.entity.Item;
import com.shopping.entity.Member;
import com.shopping.entity.Order;
import com.shopping.entity.OrderItem;
import com.shopping.repository.ItemRepository;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public Long order(OrderDto orderDto, String email) {
        Item item = this.itemRepository.findById(orderDto.getItemId())
                .orElseThrow(EntityNotFoundException::new);
        Member member = this.memberRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItemList.add(orderItem);
        Order order = Order.createOrder(member, orderItemList);
        this.orderRepository.save(order);

        return order.getId();
    }
}
