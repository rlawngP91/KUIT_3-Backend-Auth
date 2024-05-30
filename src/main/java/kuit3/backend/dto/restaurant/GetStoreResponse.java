package kuit3.backend.dto.restaurant;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
public class GetStoreResponse {
    private String store_name;
    private long delivery_time;
    private String phone_number;
    private String operating_time;
    private String store_status;

    @QueryProjection
    public GetStoreResponse(String store_name, long delivery_time, String phone_number, String operating_time) {
        this.store_name = store_name;
        this.delivery_time = delivery_time;
        this.phone_number = phone_number;
        this.operating_time = operating_time;
    }
}
