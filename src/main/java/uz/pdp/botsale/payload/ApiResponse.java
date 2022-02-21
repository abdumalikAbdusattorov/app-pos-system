package uz.pdp.botsale.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.awt.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private String message;
    private boolean success;

    @Enumerated(value = EnumType.STRING)
    private TrayIcon.MessageType messageType;

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;


    }
}
