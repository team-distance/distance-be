package io.festival.distance.domain.image.dto.request;

import java.util.List;
import java.util.stream.Collectors;

public record FileListRequest(List<FileRequest> fileRequests) {
    public List<String> toStringList(){
        return fileRequests.stream()
            .map(Record::toString)
            .collect(Collectors.toList());
    }
}
