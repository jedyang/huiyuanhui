package com.yunsheng.huiyuanhui.model;

public class Pay {
        private Integer cardId;
        private Double useMoney;
        private Integer usePoint;

        public Integer getCardId() {
            return cardId;
        }

        public void setCardId(Integer cardId) {
            this.cardId = cardId;
        }

        public Double getUseMoney() {
            return useMoney;
        }

        public void setUseMoney(Double useMoney) {
            this.useMoney = useMoney;
        }

        public Integer getUsePoint() {
            return usePoint;
        }

        public void setUsePoint(Integer usePoint) {
            this.usePoint = usePoint;
        }
    }