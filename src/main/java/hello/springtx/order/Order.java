package hello.springtx.order;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Slf4j
@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

    @Id @GeneratedValue
    private Long id;

    private String username;// 정상, 예외, 잔고부족
    private String payStatus; // 대기, 완료

    public Order() {
    }

    public Order(Long id, String username, String payStatus) {
        this.id = id;
        this.username = username;
        this.payStatus = payStatus;
    }
}
