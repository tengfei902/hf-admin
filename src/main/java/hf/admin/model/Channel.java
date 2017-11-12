package hf.admin.model;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class Channel {
    private Long id;

    private String channelCode;

    private String channelName;

    private String channelType;

    private BigDecimal feeRate;

    private String mchId;

    private String cipherCode;

    private String url;

    private Integer status;

    private String statusDesc;

    private String channelTypeDesc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public void setFeeRate(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getCipherCode() {
        return cipherCode;
    }

    public void setCipherCode(String cipherCode) {
        this.cipherCode = cipherCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getChannelTypeDesc() {
        return channelTypeDesc;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public void setChannelTypeDesc(String channelTypeDesc) {
        this.channelTypeDesc = channelTypeDesc;
    }

    public enum ChannelStatus {
        VALID(0,"开启"),INVALID(99,"关闭");

        private int value;
        private String desc;

        ChannelStatus(int value,String desc) {
            this.value = value;
            this.desc = desc;
        }

        public int getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }

        public static ChannelStatus parse(int value) {
            for(ChannelStatus channelStatus:ChannelStatus.values()) {
                if(channelStatus.value == value) {
                    return channelStatus;
                }
            }
            return null;
        }
    }

    public enum ChannelType {
        WE_CODE("1","微信扫码"),
        WE_H5("2","微信H5"),
        ZFB("3","支付宝扫码"),
        ZFB_H5("4","支付宝H5"),
        BANK("5","网银跳转"),
        BANK_DIR("6","网银直连"),
        BAIDU("7","百度钱包"),
        QQ("8","QQ钱包"),
        JD("9","京东钱包");

        private String value;
        private String desc;

        ChannelType(String value,String desc) {
            this.value = value;
            this.desc = desc;
        }

        public String getValue() {
            return value;
        }

        public String getDesc() {
            return desc;
        }

        public static ChannelType parse(String value) {
            for(ChannelType channelType:ChannelType.values()) {
                if(StringUtils.equals(value,channelType.value)) {
                    return channelType;
                }
            }
            return null;
        }
    }
}
