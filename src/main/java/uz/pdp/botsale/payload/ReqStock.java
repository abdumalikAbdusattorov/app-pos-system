package uz.pdp.botsale.payload;

import lombok.Data;
import uz.pdp.botsale.entity.StockDetail;

import java.sql.Timestamp;
import java.util.List;

@Data
public class ReqStock {
    private Integer id;
    private String name;
    private Double percent;
    private Timestamp startData;
    private Timestamp endData;
    private List<ReqStockDetail> reqStockDetails;
    private Integer marketId;
}
