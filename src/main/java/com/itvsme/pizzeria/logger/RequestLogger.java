package com.itvsme.pizzeria.logger;

import com.itvsme.pizzeria.model.RequestLog;
import com.itvsme.pizzeria.repository.RequestLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.Instant;

@Component
public class RequestLogger extends HandlerInterceptorAdapter
{
    private RequestLogRepository requestLogRepository;

    public RequestLogger(RequestLogRepository requestLogRepository)
    {
        this.requestLogRepository = requestLogRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        RequestLog requestLog = new RequestLog();
        requestLog.setPreHandleTime(Timestamp.from(Instant.now()));
        requestLog.setUri(request.getRequestURI());
        requestLog.setStatus(response.getStatus());

        requestLogRepository.save(requestLog);

        return true;
    }
}
