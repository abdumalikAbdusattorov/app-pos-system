package uz.pdp.botsale.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import uz.pdp.botsale.entity.enums.RoleName;

import javax.persistence.*;
import java.awt.*;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OrderBy
    @CreationTimestamp
    @Column(nullable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp updatedAt;

    private String message;
    private boolean success;

    private RoleName roleName;

    @Enumerated(value = EnumType.STRING)
    private TrayIcon.MessageType messageType;

    public Notification(String message, boolean success, RoleName roleName, TrayIcon.MessageType messageType) {
        this.message = message;
        this.success = success;
        this.roleName = roleName;
        this.messageType = messageType;
    }

    public Notification(String message, boolean success, TrayIcon.MessageType messageType) {
        this.message = message;
        this.success = success;
        this.messageType = messageType;
    }
}
