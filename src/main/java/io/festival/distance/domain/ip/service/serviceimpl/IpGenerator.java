package io.festival.distance.domain.ip.service.serviceimpl;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class IpGenerator {

    private String[] headerType = {"X-Forwarded-For", "Proxy-Client-IP",
        "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR",
        "X-Real-IP", "REMOTE_ADDR"};

    private String memberIpAddr;

    @Transactional
    public String generateMemberIp(HttpServletRequest request) {
        for (String header : headerType) {
            memberIpAddr = request.getHeader(header);
            if (memberIpAddr != null) {
                break;
            }
        }
        if(memberIpAddr ==null){
            memberIpAddr = request.getRemoteAddr();
        }
        return memberIpAddr;
    }
}
