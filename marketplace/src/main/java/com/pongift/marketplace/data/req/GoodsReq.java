package com.pongift.marketplace.data.req;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsReq {
    private long goodsSeq;
    private long storeSeq;
    private String goodsId;
    @Size(min = 1, max = 10)
    private List<String> categories;
    @Length(max = 50)
    private String goodsNm;
    @Min(0)
    private Integer stockCnt;
    @Min(0)
    @Max(Integer.MAX_VALUE - 1)
    private Integer salePrice;
    @Min(0)
    @Max(Integer.MAX_VALUE - 1)
    private Integer retailPrice;
    @Min(0)
    @Max(Integer.MAX_VALUE - 1)
    private Integer discountPrice;
    private String exhibitSt;
    private String expiryGb;
    private String html;
    private String storeName;
    private String storeId;
    private String goodsImage;
    private String infoUrl;
    private String storeUrl;
    private String saleEndDate;
    private Boolean enabled;
}
