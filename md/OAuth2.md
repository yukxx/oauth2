# OAuth2
##### 认证流程（网图）
![](./img/7.png)
`org.springframework.security.web.FilterChainProxy`
是filter链路代理入口类
```java
/**
* filter入口
*/
@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		boolean clearContext = request.getAttribute(FILTER_APPLIED) == null;
		if (clearContext) {
			try {
				request.setAttribute(FILTER_APPLIED, Boolean.TRUE);
				doFilterInternal(request, response, chain);
			}
			finally {
				SecurityContextHolder.clearContext();
				request.removeAttribute(FILTER_APPLIED);
			}
		}
		else {
			doFilterInternal(request, response, chain);
		}
	}
```
doFilterInternal() 方法
```java
private void doFilterInternal(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		FirewalledRequest fwRequest = firewall
				.getFirewalledRequest((HttpServletRequest) request);
		HttpServletResponse fwResponse = firewall
				.getFirewalledResponse((HttpServletResponse) response);
        // **此方法获取所有filter链路**
		List<Filter> filters = getFilters(fwRequest);

		if (filters == null || filters.size() == 0) {
			if (logger.isDebugEnabled()) {
				logger.debug(UrlUtils.buildRequestUrl(fwRequest)
						+ (filters == null ? " has no matching filters"
								: " has an empty filter list"));
			}

			fwRequest.reset();

			chain.doFilter(fwRequest, fwResponse);

			return;
		}

		VirtualFilterChain vfc = new VirtualFilterChain(fwRequest, chain, filters);
		vfc.doFilter(fwRequest, fwResponse);
	}
```
采用`jwt`请求`/oauth/token`端点时经过的filter链
![](./img/8.png)
其中`org.springframework.security.oauth2.provider.client.ClientCredentialsTokenEndpointFilter`过滤器的`attemptAuthentication()`方法
```java
@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {

		if (allowOnlyPost && !"POST".equalsIgnoreCase(request.getMethod())) {
			throw new HttpRequestMethodNotSupportedException(request.getMethod(), new String[] { "POST" });
		}

// 获取请求参数中的客户端信息
		String clientId = request.getParameter("client_id");
		String clientSecret = request.getParameter("client_secret");

		// If the request is already authenticated we can assume that this
		// filter is not needed
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated()) {
			return authentication;
		}

		if (clientId == null) {
			throw new BadCredentialsException("No client credentials presented");
		}

		if (clientSecret == null) {
			clientSecret = "";
		}

// 把数据封装成UsernamePasswordAuthenticationToken对象
		clientId = clientId.trim();
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(clientId,
				clientSecret);

		return 
		//获取核心权限认证器 AuthenticationManager(默认为ProviderManager）
		this.getAuthenticationManager().authenticate(authRequest);

	}
```
`org.springframework.security.authentication.AuthenticationManager`为顶级权限认证接口，
它的默认实现类为`org.springframework.security.authentication.ProviderManager`
##### 1.验证 client 端filter为 `ClientCredentialsTokenEndpointFilter`
##### 2.验证 user 端为`AbstractUserDetailsAuthenticationProvider`
（唯一子类`DaoAuthenticationProvider`）
##### 3.user信息验证错误时，抛不出自己的错误信息原因为
```java
try {
				user = retrieveUser(username,
						(UsernamePasswordAuthenticationToken) authentication);
			}
			catch (UsernameNotFoundException notFound) {
				logger.debug("User '" + username + "' not found");
        // 该默认值为=true 所以所有的错误信息都被隐藏了
				if (hideUserNotFoundExceptions) {
					throw new BadCredentialsException(messages.getMessage(
							"AbstractUserDetailsAuthenticationProvider.badCredentials",
							"Bad credentials"));
				}
				else {
					throw notFound;
				}
			}
```
可以自定义Provider解决
```java
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
    // DaoAuthenticationProvider 是AbstractUserDetailsAuthenticationProvider的唯一子类
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // 设置加载用户服务
        provider.setUserDetailsService(userDetailsService);
        // 使用BCrypt 进行密码的hash
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        // 禁止隐藏用户未找到异常
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }
```
