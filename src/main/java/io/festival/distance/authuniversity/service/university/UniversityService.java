package io.festival.distance.authuniversity.service.university;

import static io.festival.distance.authuniversity.domain.University.getUniversity;

import io.festival.distance.authuniversity.dto.response.SchoolResponse;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UniversityService {
    public List<SchoolResponse> findSchools(String schoolName){
        return getUniversity(schoolName).stream()
            .map(SchoolResponse::toSchoolResponse)
            .toList();
    }
}
