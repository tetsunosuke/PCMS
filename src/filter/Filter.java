package filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 *@author Akihiro Nakamura
 *
 */
@WebFilter("/*")
public class Filter implements javax.servlet.Filter{

	/*(非 Javadoc)
	 *@see javax.servlet.Filter#destroy()
	 *終了処理
	 */
	public void destroy(){

	}

	/*(非 Javadoc)
	 *@see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 *フィルタ処理
	 */
	public void doFilter(ServletRequest request,ServletResponse response,FilterChain chain)throws IOException,ServletException{
		request.setCharacterEncoding("UTF-8");

		chain.doFilter(request,response);

	}

	/*(非 Javadoc)
	 *@see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 *初期化処理
	 */
	public void init (FilterConfig config) throws ServletException{

	}
}