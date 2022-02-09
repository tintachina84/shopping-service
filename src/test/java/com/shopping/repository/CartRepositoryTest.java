package com.shopping.repository;

import com.shopping.dto.MemberFormDto;
import com.shopping.entity.Cart;
import com.shopping.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
    public void findCartAndMemberTest() {
        Member member = this.createMember();
        this.memberRepository.save(member);

        Cart cart = new Cart();
        cart.setMember(member);
        this.cartRepository.save(cart);

        this.entityManager.flush(); // 영속성 컨텍스트에서 DB로 반영
        this.entityManager.clear(); // 영속성 컨텍스트에서 조회하지 않고 DB에서 조회하기 위해 영속성 컨텍스트를 비워준다

        Cart savedCart = this.cartRepository.findById(cart.getId()).orElseThrow(EntityNotFoundException::new);

        assertEquals(savedCart.getMember().getId(), member.getId());
    }

    private Member createMember() {
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@eamil.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword("1234");

        return Member.createMember(memberFormDto, this.passwordEncoder);
    }
}
