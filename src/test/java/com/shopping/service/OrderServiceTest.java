package com.shopping.service;

import com.shopping.constant.ItemSellStatus;
import com.shopping.dto.OrderDto;
import com.shopping.entity.Item;
import com.shopping.entity.Member;
import com.shopping.entity.Order;
import com.shopping.entity.OrderItem;
import com.shopping.repository.ItemRepository;
import com.shopping.repository.MemberRepository;
import com.shopping.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("주문 테스트")
    public void order() {
        Item item = this.saveItem();
        Member member = this.saveMember();

        OrderDto orderDto = new OrderDto();
        orderDto.setCount(10);
        orderDto.setItemId(item.getId());

        Long orderId = this.orderService.order(orderDto, member.getEmail());
        Order order = this.orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        List<OrderItem> orderItems = order.getOrderItems();
        int totalPrice = orderDto.getCount() * item.getPrice();

        assertEquals(totalPrice, order.getTotalPrice());
    }

    private Item saveItem() {
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);

        return this.itemRepository.save(item);
    }

    private Member saveMember() {
        Member member = new Member();
        member.setEmail("test@test.com");

        return this.memberRepository.save(member);
    }
}
