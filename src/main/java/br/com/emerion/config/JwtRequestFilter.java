package br.com.emerion.config;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.filter.OncePerRequestFilter;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
@CrossOrigin(origins = "*")
public class JwtRequestFilter extends OncePerRequestFilter {

	@Value("${user_webservice.host}")
	private String host;

	@Value("${user_webservice.port}")
	private String port;

	@Value("${user_webservice.context}")
	private String context;

	public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		String jwtToken = "";

		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}

		if (jwtToken != null && !jwtToken.isEmpty()) {

			ResponseEntity<String> responseEntity = new JwtRequestFilter()
					.post(this.host + ":" + this.port + "/" + this.context + "/validate-token", jwtToken);

			if ((responseEntity.getStatusCodeValue() != 401)
					&& (SecurityContextHolder.getContext().getAuthentication() == null)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						"usuario_sistema", null, new ArrayList<>());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				
			}

		}

		chain.doFilter(request, response);

	}

	ResponseEntity<String> post(String url, String json) throws IOException {
		OkHttpClient client = new OkHttpClient();
		RequestBody body = RequestBody.create(json, JSON);
		Request request = new Request.Builder().header("Authorization", "Bearer " + json).url(url).post(body).build();
		try (Response response = client.newCall(request).execute()) {
			return new ResponseEntity<>(response.body().string(), HttpStatus.resolve(response.code()));
		}
	}

}
