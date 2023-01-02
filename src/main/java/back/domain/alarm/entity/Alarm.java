package back.domain.alarm.entity;

import back.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alarm_id")
    private Long alarmId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private User user;

    @Enumerated
    private AlarmType type;

    @Column(name = "is_read")
    private Boolean isRead;

    @Builder
    public Alarm(User user, AlarmType type, Boolean isRead) {
        this.user = user;
        this.type = type;
        this.isRead = isRead;
    }

    static public Alarm create(User user, AlarmType type){
        return Alarm.builder()
                .user(user)
                .type(type)
                .isRead(false)
                .build();
    }
}
