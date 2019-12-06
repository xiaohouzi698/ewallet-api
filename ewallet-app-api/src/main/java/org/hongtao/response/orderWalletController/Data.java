/**
 * Copyright 2019 bejson.com
 */
package org.hongtao.response.orderWalletController;

/**
 * Auto-generated: 2019-11-06 13:52:25
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Data {

    private OrderWalletDto orderWalletDto;
    private boolean isNeedUpdateBankCard;
    private boolean isExpandTime;
    private int applyStatus;
    public void setOrderWalletDto(OrderWalletDto orderWalletDto) {
        this.orderWalletDto = orderWalletDto;
    }
    public OrderWalletDto getOrderWalletDto() {
        return orderWalletDto;
    }

    public void setIsNeedUpdateBankCard(boolean isNeedUpdateBankCard) {
        this.isNeedUpdateBankCard = isNeedUpdateBankCard;
    }
    public boolean getIsNeedUpdateBankCard() {
        return isNeedUpdateBankCard;
    }

    public void setIsExpandTime(boolean isExpandTime) {
        this.isExpandTime = isExpandTime;
    }
    public boolean getIsExpandTime() {
        return isExpandTime;
    }

    public void setApplyStatus(int applyStatus) {
        this.applyStatus = applyStatus;
    }
    public int getApplyStatus() {
        return applyStatus;
    }

}