package com.shopping.service;

import com.shopping.constant.ItemSellStatus;
import com.shopping.dto.CartItemDto;
import com.shopping.entity.CartItem;
import com.shopping.entity.Item;
import com.shopping.entity.Member;
import com.shopping.repository.CartItemRepository;
import com.shopping.repository.ItemRepository;
import com.shopping.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class CartServiceTest {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Test
    @DisplayName("장바구니 담기 테스트")
    public void addCart() {
        Item item = this.saveItem();
        Member member = this.saveMember();

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setCount(5);
        cartItemDto.setItemId(item.getId());

        Long cartItemId = this.cartService.addCart(cartItemDto, member.getEmail());

        CartItem cartItem = this.cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);

        assertEquals(item.getId(), cartItem.getItem().getId());
        assertEquals(cartItemDto.getCount(), cartItem.getCount());
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
