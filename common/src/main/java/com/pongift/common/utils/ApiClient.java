package com.pongift.common.utils;

import com.google.common.io.ByteSource;
import com.pongift.common.code.ApiMessageCode;
import com.pongift.common.error.ApiException;

import java.io.IOException;
import java.io.InputStream;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 * Created by dplee on 2020/04/10.
 */
@Slf4j
public class ApiClient extends Exception {
    String XML = "xml";
    String JSON = "json";
    String apiKey = "";
    String authorization = "";

    public ApiClient() {
    }

    public ApiClient(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getKey() {
        return this.apiKey;
    }

    public String getAuthorization() {
        return this.authorization;
    }

    public String defaultCall(String method, String requestURL, String message) throws Exception {
        String result = call(method, XML, requestURL, message);
        return result;
    }

    public String pongiftplus(String method, String requestURL, String message) throws Exception {
        return call(method, JSON, requestURL, message);
    }

    public String naver(String method, String requestURL, String message) throws Exception {
        String result = call(method, XML, requestURL, message);
        return result;
    }

    public String auction(String method, String requestURL, String message) throws Exception {
        String result = call(method, XML, requestURL, message);
        return result;
    }

    public String wemakeprice(String method, String requestURL, String message) throws Exception {
        String result = call(method, JSON, requestURL, message);
        return result;
    }

    public String ssg(String method, String requestURL, String authorization, String message) throws Exception {
        this.authorization = authorization;
        String result = call(method, JSON, requestURL, message);
        return result;
    }

    public String marketPlace(String method, String requestUrl, String message) throws Exception {
        String result = call(method, JSON, requestUrl, message);
        return result;
    }


    public String call(String method, String type, String requestURL, String message) throws Exception {
        String outResult = "";
        HttpClient client = HttpClientBuilder.create().build(); // ApiClient 생성
        HttpResponse response = null;
        String _apiKey = this.getKey();
        String _authorization = this.getAuthorization();

        if (method.equals("GET")) {
            HttpGet getRequest = new HttpGet(requestURL); //GET 메소드 URL 생성
            if (StringUtils.isNotEmpty(_apiKey)) {
                getRequest.setHeader("apiKey", _apiKey);
            }
            if (StringUtils.isNotEmpty(_authorization)) {
                getRequest.setHeader("Authorization", _authorization);
            }

            if (type.equals("xml")) {
                getRequest.setHeader("Content-type", "text/xml; charset=UTF-8");
                getRequest.setHeader("Accept", "application/xml");
            } else if (type.equals("json")) {
                // wemakeprice
                getRequest.setHeader("Content-Type", "application/json; charset=UTF-8");
                getRequest.setHeader("Accept", "application/json");
            }
            response = client.execute(getRequest);

        } else if (method.equals("POST")) {
            HttpPost postRequest = new HttpPost(requestURL); //POST 메소드 URL
            if (StringUtils.isNotEmpty(_apiKey)) {
                postRequest.setHeader("apiKey", _apiKey);
            }
            if (StringUtils.isNotEmpty(_authorization)) {
                postRequest.setHeader("Authorization", _authorization);
            }

            if (type.equals("xml")) {
                // auction
                postRequest.setHeader("Content-type", "text/xml; charset=UTF-8");
                postRequest.setHeader("Accept", "application/xml");
            } else if (type.equals("json")) {
                // wemakeprice
                postRequest.setHeader("Content-Type", "application/json; charset=UTF-8");
                postRequest.setHeader("Accept", "application/json");
            }

            //postRequest.setEntity(new StringEntity(message)); //json 메시지 입력
            postRequest.setEntity(new StringEntity(message.toString(), "UTF-8")); //json 메시지 입력
            response = client.execute(postRequest);
        }else if (method.equals("PUT")) {
            HttpPut putRequest = new HttpPut(requestURL); //PUT 메소드 URL
            if (StringUtils.isNotEmpty(_apiKey)) {
                putRequest.setHeader("apiKey", _apiKey);
            }
            if (StringUtils.isNotEmpty(_authorization)) {
                putRequest.setHeader("Authorization", _authorization);
            }

            if (type.equals("xml")) {
                // auction
                putRequest.setHeader("Content-type", "text/xml; charset=UTF-8");
                putRequest.setHeader("Accept", "application/xml");
            } else if (type.equals("json")) {
                // wemakeprice
                putRequest.setHeader("Content-Type", "application/json; charset=UTF-8");
                putRequest.setHeader("Accept", "application/json");
            }

            //postRequest.setEntity(new StringEntity(message)); //json 메시지 입력
            putRequest.setEntity(new StringEntity(message.toString(), "UTF-8")); //json 메시지 입력
            response = client.execute(putRequest);
        }

        //Response 출력
        if (response.getStatusLine().getStatusCode() == 200) {
            ResponseHandler<String> handler = new BasicResponseHandler();
            outResult = handler.handleResponse(response);
            log.info("success : {}", outResult);

        } else {
            InputStream is = response.getEntity().getContent();
            ByteSource byteSource = new ByteSource() {
                @Override
                public InputStream openStream() throws IOException {
                    return is;
                }
            };
            outResult = byteSource.asCharSource(Charsets.UTF_8).read();
            String errMsg = getFaultMessage(outResult);
            throw new ApiException(false, errMsg);
        }

        return outResult;
    }

    private String getFaultMessage(String str) {
        String message = "";
        String start = "<faultstring>";
        String end = "</faultstring>";
        if (StringUtils.isNotEmpty(str)) {
            int sidx = str.indexOf(start);
            int eidx = str.indexOf(end);
            if (sidx > 0 && eidx > 0) {
                message = str.substring(sidx, eidx).replaceAll(start, "");
                log.error("ApiClient Error - getFaultMessage:" + message);
            } else {
                message = str;
                log.error("ApiClient Error - getFaultMessage:" + message);
            }
        } else {
            message = ApiMessageCode.API_ERROR_MSG_0002.getValue();
        }

        return message;
    }


}
