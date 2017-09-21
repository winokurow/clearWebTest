package de.clearit.test.framework.webdriver;

import java.util.HashSet;
import java.util.Set;

import org.openqa.selenium.Cookie;

import de.clearit.test.framework.data.Browser;

public class WebDriverSettings {

	/** SUT URL. */
	private String url;

	/** ob auf lokalen Rechner ausgeführt werden. */
	private boolean local;

	/** ob Proxy benutzt werden. */
	private boolean useNoProxy;

	/** URL des Selenium Grid. */
	private String seleniumGridUrl;

	/** Grid Hint. */
	private String gridHint;

	/** Browser. */
	private Browser browser;

	/** Cookies zu setzen. */
	private Set<Cookie> cookies;

	/**
	 * @param url
	 * @param local
	 * @param useNoProxy
	 * @param seleniumGridUrl
	 * @param gridHint
	 * @param browser
	 * @param cookies
	 */
	private WebDriverSettings(String url, boolean local, boolean useNoProxy, String seleniumGridUrl, String gridHint,
			Browser browser, Set<Cookie> cookies) {
		super();
		this.url = url;
		this.local = local;
		this.useNoProxy = useNoProxy;
		this.seleniumGridUrl = seleniumGridUrl;
		this.gridHint = gridHint;
		this.browser = browser;
		this.cookies = cookies;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @param local
	 *            the local to set
	 */
	public void setLocal(boolean local) {
		this.local = local;
	}

	/**
	 * @param useNoProxy
	 *            the useNoProxy to set
	 */
	public void setUseNoProxy(boolean useNoProxy) {
		this.useNoProxy = useNoProxy;
	}

	/**
	 * @param seleniumGridUrl
	 *            the seleniumGridUrl to set
	 */
	public void setSeleniumGridUrl(String seleniumGridUrl) {
		this.seleniumGridUrl = seleniumGridUrl;
	}

	/**
	 * @param gridHint
	 *            the gridHint to set
	 */
	public void setGridHint(String gridHint) {
		this.gridHint = gridHint;
	}

	/**
	 * @param browser
	 *            the browser to set
	 */
	public void setBrowser(Browser browser) {
		this.browser = browser;
	}

	/**
	 * @param cookies
	 *            the cookies to set
	 */
	public void setCookies(Set<Cookie> cookies) {
		this.cookies = cookies;
	}

	/**
	 * @return the browser
	 */
	public Browser getBrowser() {
		return browser;
	}

	/**
	 * @return the gridHint
	 */
	public String getGridHint() {
		return gridHint;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the local
	 */
	public boolean isLocal() {
		return local;
	}

	/**
	 * @return the useNoProxy
	 */
	public boolean isUseNoProxy() {
		return useNoProxy;
	}

	/**
	 * @return the seleniumGridUrl
	 */
	public String getSeleniumGridUrl() {
		return seleniumGridUrl;
	}

	/**
	 * @return the cookies
	 */
	public Set<Cookie> getCookies() {
		return cookies;
	}

	/**
	 * Builder class.
	 */
	public static class WebDriverSettingsBuilder {
		/** SUT URL. */
		private String url;

		/** ob auf lokalen Rechner ausgeführt werden. */
		private boolean local;

		/** ob Proxy benutzt werden. */
		private boolean useNoProxy;

		/** URL des Selenium Grid. */
		private String seleniumGridUrl;

		/** Cookies zu setzen. */
		private Set<Cookie> cookies;

		/** Grid Hint. */
		private String gridHint;

		/** Browser. */
		private Browser browser;

		/**
		 * @param url
		 * @param local
		 * @param browser
		 */
		public WebDriverSettingsBuilder(String url, boolean local, Browser browser) {
			super();
			this.url = url;
			this.local = local;
			this.browser = browser;
			useNoProxy = true;
			seleniumGridUrl = "";
			cookies = new HashSet<Cookie>();
		}

		/**
		 * @param url
		 *            the url to set
		 */
		public WebDriverSettingsBuilder setUrl(String url) {
			this.url = url;
			return this;
		}

		/**
		 * @param local
		 *            the local to set
		 */
		public WebDriverSettingsBuilder setLocal(boolean local) {
			this.local = local;
			return this;
		}

		/**
		 * @param useNoProxy
		 *            the useNoProxy to set
		 */
		public WebDriverSettingsBuilder setUseNoProxy(boolean useNoProxy) {
			this.useNoProxy = useNoProxy;
			return this;
		}

		/**
		 * @param seleniumGridUrl
		 *            the seleniumGridUrl to set
		 */
		public WebDriverSettingsBuilder setSeleniumGridUrl(String seleniumGridUrl) {
			this.seleniumGridUrl = seleniumGridUrl;
			return this;
		}

		/**
		 * @param GridHint
		 *            the GridHint to set
		 */
		public WebDriverSettingsBuilder setGridHint(String gridHint) {
			this.gridHint = gridHint;
			return this;
		}

		/**
		 * @param cookies
		 *            the cookies to set
		 */
		public WebDriverSettingsBuilder setCookies(Set<Cookie> cookies) {
			this.cookies = cookies;
			return this;
		}

		/**
		 * @param browser
		 *            the browser to set
		 */
		public void setBrowser(Browser browser) {
			this.browser = browser;
		}

		public WebDriverSettings build() {
			return new WebDriverSettings(url, local, useNoProxy, seleniumGridUrl, gridHint, browser, cookies);
		}
	}

}
