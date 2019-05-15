package com.koalacute.micro.koala.utils.logger;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


public class LoggerRequestFilter extends OncePerRequestFilter {

	private static final Logger  logger = LoggerFactory.getLogger(LoggerRequestFilter.class);
	private boolean includeResponsePayload = true;
	private int maxPayloadLength = 5000;
	private static AtomicLong logIndexObj = new AtomicLong(0L);
	private static final String FILTER_PAYLOAD_URL = "uploadImg";
	private static final String FILTER_PRINT_URL = "getImage";

	private String getContentAsString(byte[] buf, int maxLength, String charsetName) {
		if (buf == null || buf.length == 0) return "";
		int length = Math.min(buf.length, this.maxPayloadLength);
		try {
			return new String(buf, 0, length, charsetName);
		} catch (UnsupportedEncodingException ex) {
			return "Unsupported Encoding";
		}
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
					throws ServletException, IOException {
		BodyReaderHttpRequestWrapper wrappedRequest = new BodyReaderHttpRequestWrapper(request);
		ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);
		if("OPTIONS".equals(request.getMethod())){
			filterChain.doFilter(wrappedRequest, wrappedResponse);
			return;
		}
		long startTime = System.currentTimeMillis();
		Long id = logIndexObj.incrementAndGet();
		StringBuilder reqInfo = new StringBuilder().append("Inbound Message")
		.append(IOUtils.LINE_SEPARATOR)
		.append("-----------------------------")
		.append(IOUtils.LINE_SEPARATOR)
		.append("ID: ")
		.append(id).append(IOUtils.LINE_SEPARATOR)  // request ID
		.append("Address: ").append(request.getRequestURL()).append(IOUtils.LINE_SEPARATOR)
		.append("Encoding: ").append(request.getCharacterEncoding()).append(IOUtils.LINE_SEPARATOR)
		.append("Content-Type: ");
		if(request.getContentType() != null) {
			reqInfo.append(request.getContentType());
		}
		reqInfo.append(IOUtils.LINE_SEPARATOR).append("Headers: ").append("{");

		Enumeration<String> e = request.getHeaderNames();
		if(e != null){
			while(e.hasMoreElements()){  
				String name = (String) e.nextElement();  
				String value = request.getHeader(name); 
				reqInfo.append(name).append("=").append("[").append(value).append("]").append(", ");
			}
			reqInfo = new StringBuilder(reqInfo.substring(0, reqInfo.length() - 2));
		}
		reqInfo.append("}").append(IOUtils.LINE_SEPARATOR)
		.append("Payload: ").append(IOUtils.LINE_SEPARATOR);
		String queryString = request.getQueryString();
		if (queryString != null) {
			reqInfo.append("?").append(queryString);
		}
		if (request.getAuthType() != null) {
			reqInfo.append(", authType=")
			.append(request.getAuthType());
		}
		if (request.getUserPrincipal() != null) {
			reqInfo.append(", principalName=")
			.append(request.getUserPrincipal().getName());
		}


		// ========= Log request and response payload ("body") ========
		// We CANNOT simply read the request payload here, because then the InputStream would be consumed and cannot be read again by the actual processing/server.
		//    String reqBody = DoogiesUtil._stream2String(request.getInputStream());   // THIS WOULD NOT WORK!
		// So we need to apply some stronger magic here :-)
		//不能简单的读取request的body的payload信息，因为被读取一次，请求中的数据流就被消费完了，不能再被controller处理了
		//利用filter，wrapper一层，然后proceed，最后response完之后在把cached的body设置回原始响应。
		//spring提供了ContentCachingRequestWrapper以及ContentCachingResponseWrapper两个类，来解决这类问题
		if(request.getRequestURL().indexOf(FILTER_PAYLOAD_URL) < 0){
			BufferedReader reader = wrappedRequest.getReader();
			String str, requestBody = "";
			while((str = reader.readLine()) != null){
				requestBody += str;
			}
			if (requestBody.length() > 0) {
				reqInfo.append(requestBody).append(IOUtils.LINE_SEPARATOR);
			}else {
				Map<String, String[]> parameterMap = wrappedRequest.getParameterMap();
				if(parameterMap!=null&&parameterMap.size()>0) {
					for(Map.Entry<String, String[]> entry:parameterMap.entrySet()) {
						String key = entry.getKey();
						String[] values = entry.getValue();
						for(String  item:values) {
							requestBody += key+"="+item+"&";
						}
					}
					reqInfo.append(requestBody).append(IOUtils.LINE_SEPARATOR);
				}
			}
		}
		reqInfo.append("-----------------------------");
		logger.info(reqInfo + "");
		filterChain.doFilter(wrappedRequest, wrappedResponse);  // ======== This performs the actual request! 此处执行真正的请求

		long duration = System.currentTimeMillis() - startTime;
		// I can only log the request's body AFTER the request has been made and ContentCachingRequestWrapper did its work.
		//	   String requestBody = this.getContentAsString(wrappedRequest.get, this.maxPayloadLength, request.getCharacterEncoding());
		StringBuilder responseInfo = new StringBuilder().append("Outbound Message");
		responseInfo.append(IOUtils.LINE_SEPARATOR)
		.append("-----------------------------")
		.append(IOUtils.LINE_SEPARATOR)
		.append("ID: ")
		.append(id).append(IOUtils.LINE_SEPARATOR)  // request ID
		.append("Response-Code: ").append(response.getStatus()).append(IOUtils.LINE_SEPARATOR)
		.append("Content-Type: ").append(response.getContentType()).append(IOUtils.LINE_SEPARATOR)
		.append("Headers: ").append("{");

		Enumeration<String> e1 = request.getHeaderNames();
		if(e1 != null){
			while(e1.hasMoreElements()){  
				String name = (String) e1.nextElement();  
				String value = request.getHeader(name); 
				responseInfo.append(name).append("=").append("[").append(value).append("]").append(", ");
			}
			responseInfo = new StringBuilder(responseInfo.substring(0, responseInfo.length() - 2));
		}
		
		responseInfo.append("}").append(IOUtils.LINE_SEPARATOR);
		
		
		if (includeResponsePayload && request.getRequestURL().indexOf(FILTER_PRINT_URL) < 0) {
			byte[] buf = wrappedResponse.getContentAsByteArray();
			//			logger.debug("   Response body:\n"+getContentAsString(buf, this.maxPayloadLength, response.getCharacterEncoding()));
			responseInfo.append("Payload: ").append(getContentAsString(buf, this.maxPayloadLength, response.getCharacterEncoding())).append(IOUtils.LINE_SEPARATOR);
		}
		responseInfo.append("Return-Time: " + duration + "ms").append(IOUtils.LINE_SEPARATOR)
		.append("-----------------------------");
		logger.info(responseInfo + "");
		wrappedResponse.copyBodyToResponse();
		// IMPORTANT: copy content of response back into original response.通过这个设置回去，就可以使得终端消费者可以正常接收响应了。

	}

}
