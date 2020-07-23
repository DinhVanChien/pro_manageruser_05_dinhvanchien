/**
 * Copyright(C) 2017 Luvina
 * CharacterEncodingFilter.java, 1/11/2017 DinhVanChien
 */
package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Constant;

/**
 * class CharacterEncodingFilter set UTF-8 cho dữ liêu gửi từ client lên server, và set UTF-8 cho dữ liệu trả từ server về client
 * @author DinhVanChien
 */
@WebFilter(
		filterName = Constant.CHARACTER_ENCODING_FILTER,
		urlPatterns = Constant.DO_FILTER)
public class CharacterEncodingFilter implements Filter {

    /**
     * Default constructor. 
     */
    public CharacterEncodingFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * Method doFilter thực hiện set UTF-8 cho dữ liêu gửi từ client lên server, và set UTF-8 cho dữ liệu trả từ server về client
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		req.setCharacterEncoding(Constant.SET_UTF8);
		resp.setCharacterEncoding(Constant.SET_UTF8);
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
