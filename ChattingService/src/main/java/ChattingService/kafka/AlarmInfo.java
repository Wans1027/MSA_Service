package ChattingService.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlarmInfo {
    List<Long> memberIdList;
    String serviceType;
    String title;
    String body;
}
