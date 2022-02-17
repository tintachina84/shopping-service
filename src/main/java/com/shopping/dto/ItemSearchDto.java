package com.shopping.dto;

import com.shopping.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemSearchDto {

    /**
     * <pre>
     * all: 상품 등록일 전체
     * 1d: 최근 하루 동안 등록된 상품
     * 1w: 최근 일주일 동안 등록된 상품
     * 1m: 최근 한달 동안 등록된 상품
     * 6m: 최근 6개월 동안 등록된 상품
     * </pre>
     */
    private String searchDateType;

    /**
     * <pre>
     * 상품의 판매 상태
     * </pre>
     */
    private ItemSellStatus searchSellStatus;

    /**
     * <pre>
     * itemNm: 상품명
     * createdBy: 상품 등록자 아이디
     * </pre>
     */
    private String searchBy;

    /**
     * <pre>
     * 조회 검색어
     * </pre>
     */
    private String searchQuery = "";
}
