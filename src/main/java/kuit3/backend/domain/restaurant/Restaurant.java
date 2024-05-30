package kuit3.backend.domain.restaurant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@Getter
@NoArgsConstructor
@Entity(name="store")
public class Restaurant{
    @Id
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long store_id;

    @Column(nullable = false)
    private String store_name;

    private String delivery_time;

    private Long minimum_price;

    private String phone_number;

    private Long payment;

    private String origin_info;

    private String operating_time;

    private String closed_day;

    private String store_address;

    private String delivery_area;

    private String benefit_info;

    private String store_status;

    private String created_date;
    private String updated_date;

    @Builder
    public Restaurant(Long store_id, String store_name, String delivery_time, Long minimum_price, String phone_number, Long payment, String origin_info, String operating_time, String store_address, String closed_day, String delivery_area, String store_status, String benefit_info, String created_date, String updated_date) {
        this.store_id = store_id;
        this.store_name = store_name;
        this.delivery_time = delivery_time;
        this.minimum_price = minimum_price;
        this.phone_number = phone_number;
        this.payment = payment;
        this.origin_info = origin_info;
        this.operating_time = operating_time;
        this.store_address = store_address;
        this.closed_day = closed_day;
        this.delivery_area = delivery_area;
        this.store_status = store_status;
        this.benefit_info = benefit_info;
        this.created_date = created_date;
        this.updated_date = updated_date;
    }
}
