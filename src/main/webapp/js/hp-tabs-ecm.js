/*	$Revision: 1.17 $ */
var ibmHPPrototypeRedirect = location.pathname;
var ibmHPPrototypeServerLocation = location.hostname;
var ibmHPPrototypeParam;
ibmHPPrototypeParam = location.search;
if (ibmHPPrototypeServerLocation == "www.ibm.com"
		&& ibmHPPrototypeRedirect.match("/us/en/")) {
	if (ibmHPPrototypeParam) {
		window.location = "/us/en/sandbox/ver2/index.html"
				+ ibmHPPrototypeParam
	} else {
		window.location = "/us/en/sandbox/ver2/"
	}
}
(function() {
	var A = {
		maxTabs : 5,
		init : function() {
			var C = document.getElementById("ibm-home-tasks"), D = [], G = 0, B = [], F, E = document
					.getElementById("ibm-home-task-nav");
			for (G = 0; G < A.maxTabs; G++) {
				F = document.getElementById("ibm-tab-content-" + G);
				if (F !== null) {
					B[B.length] = F
				}
			}
			A.tabs = E.getElementsByTagName("li");
			for (G = 0; G < A.tabs.length; G++) {
				if (G === 0) {
					A.tabs.item(G).isFirst = true
				} else {
					if (G === (A.tabs.length - 1)) {
						A.tabs.item(G).isLast = true
					}
				}
			}
			for (G = 0; G < B.length; G++) {
				if (B[G].nodeName.toLowerCase() === "div") {
					D[D.length] = B[G]
				}
			}
			for (G = 1; G < D.length; G++) {
				D[G].style.display = "none"
			}
			B = jQuery("div.ibm-tab-content-submenu", C).get();
			for (G = 0; G < B.length; G++) {
				A.setMenuStyles(B[G])
			}
			A.restoreActiveTabFromCookie()
		},
		setMenuStyles : function() {
			if (arguments.length < 1) {
				return
			}
			var B = arguments[0];
			B.style.backgroundColor = "#FFFFFF";
			B.style.border = "#CCC solid 1px";
			B.style.display = "none";
			B.style.position = "absolute";
			B.style.width = "208px";
			B.style.zIndex = "9"
		},
		deactivateAll : function() {
			var D = document.getElementById("ibm-home-tasks"), E, F;
			if (D === null) {
				return
			}
			var C = jQuery("div.ibm-active", D).get();
			var B = C.length;
			for (E = 0; E < B; E++) {
				F = C[E];
				ibmCommon.deleteClassName(F, "ibm-active");
				if (F.nodeName.toLowerCase() === "div") {
					F.style.display = "none"
				}
			}
			B = A.tabs.length;
			for (E = 0; E < B; E++) {
				F = A.tabs[E];
				if (F.isFirst) {
					F.className = "ibm-first-tab"
				} else {
					if (F.isLast) {
						F.className = "ibm-last-tab"
					} else {
						F.className = ""
					}
				}
			}
		},
		focusContent : function(G) {
			var E = document.getElementById(G);
			var C = jQuery("a.ibm-access", E).get();
			var B = C.length;
			for ( var D = 0; D < B; D++) {
				var F = C[D];
				if (F.nodeName.toLowerCase() === "a") {
					F.focus();
					return
				}
			}
		},
		setActive : function(D, F) {
			var C = null, E = "", B = D.parentNode;
			A.deactivateAll();
			E = B.nodeName.toLowerCase();
			while (E !== "li" && E !== "body" && E !== "html") {
				B = B.parentNode
			}
			if (B.isFirst) {
				B.className = "ibm-active-first-tab"
			} else {
				if (B.isLast) {
					B.className = "ibm-active-last-tab"
				} else {
					B.className = "ibm-active"
				}
			}
			A.setActivePrevious(B);
			A.setActiveNext(B);
			C = document.getElementById(F);
			C.className += " ibm-active";
			C.style.display = "block";
			A.focusContent(F);
			if (B.id) {
				A.setLastTabCookie(B.id)
			}
		},
		setActiveNext : function(C) {
			var B = A.getNextTab(C);
			if (B === null) {
				return
			}
			if (B.isLast) {
				B.className = "ibm-active-next-last-tab"
			} else {
				B.className = "ibm-active-next-tab"
			}
		},
		setActivePrevious : function(C) {
			var B = A.getPreviousTab(C);
			if (B === null) {
				return
			}
			if (B.isFirst) {
				B.className = "ibm-active-first-previous-tab"
			} else {
				B.className = "ibm-active-previous-tab"
			}
		},
		delay : 400,
		delayedSetActive : function(B, C) {
			B._timerId = window.setTimeout(function() {
				A.setActive(B, C)
			}, A.delay)
		},
		cancelSetActive : function(B) {
			if (B._timerId) {
				window.clearTimeout(B._timerId)
			}
			B._timerId = null
		},
		delayedHideHPT : function(C) {
			if (!ibmCommon.isDOM) {
				return
			}
			var B = document.getElementById(C);
			if (B === null) {
				return
			}
			B._timerId = window.setTimeout(function() {
				A.hideHPT(C)
			}, A.delay)
		},
		getNextTab : function(C) {
			if (typeof C.nextSibling === "undefined") {
				return null
			}
			var B = C.nextSibling;
			while (B !== null) {
				if (B.nodeName.toLowerCase() === "li") {
					return B
				}
				B = B.nextSibling
			}
			return null
		},
		getPreviousTab : function(C) {
			if (typeof C.previousSibling === "undefined") {
				return null
			}
			var B = C.previousSibling;
			while (B !== null) {
				if (B.nodeName.toLowerCase() === "li") {
					return B
				}
				B = B.previousSibling
			}
			return null
		},
		hideAllHPT : function() {
			if (A.hptCache === null) {
				A.hptCache = jQuery("div.ibm-tab-content-submenu",
						document.documentElement).get()
			}
			for ( var B = 0; B < A.hptCache.length; B++) {
				A.delayedHideHPT(A.hptCache[B].id)
			}
		},
		hideHPT : function(C) {
			if (!ibmCommon.isDOM) {
				return
			}
			var B = document.getElementById(C);
			if (B === null) {
				return
			}
			B.style.display = "none";
			if (B._timerId) {
				B._timerId = null
			}
		},
		setPosition : function(C) {
			if (!C._triggerId) {
				return
			}
			var B = document.getElementById(C._triggerId);
			var D = jQuery(B).position();
			C.style.top = (D.top - C.offsetHeight + 18) + "px";
			if (ibmCommon.meta.cc == "il") {
				C.style.left = (D.left - C.offsetWidth) + "px"
			} else {
				C.style.left = (D.left + B.offsetWidth) + "px"
			}
		},
		showHPT : function(B, E) {
			if (!ibmCommon.isDOM) {
				return
			}
			var D = document.getElementById(E);
			if (D === null) {
				return
			}
			var C = D.getElementsByTagName("a");
			if (C.length < 1) {
				window.location = B.getAttribute("href");
				return
			}
			if (D._timerId) {
				window.clearTimeout(D._timerId);
				D._timerId = null
			}
			if (B !== null) {
				if (!B.id || B.id === "" || B.id === null) {
					B.id = ibmCommon.generateId()
				}
				D._triggerId = B.id
			}
			D.style.display = "block";
			A.setPosition(D)
		},
		hptCache : null,
		hideSubmenuMarker : function() {
			var D = document.getElementById("ibm-interest-dd1");
			if (D === null) {
				return
			}
			var C = D.getElementsByTagName("a");
			if (C.length > 0) {
				return
			}
			var B = document.getElementById("ibm-interest-dd1-trigger");
			B.className = B.className.replace(
					/ibm-tab-content-submenu-trigger/, "")
		},
		cancelDelayedHideHPT : function(C) {
			if (!ibmCommon.isDOM) {
				return
			}
			var B = document.getElementById(C);
			if (B && B._timerId) {
				window.clearTimeout(B._timerId)
			}
		},
		showAccountsTab : function() {
			var C, B;
			if (typeof window.SA_Message !== "string") {
				return
			}
			C = window.SA_Message.replace(/audience=/, "");
			if (C.indexOf("all") !== 0 && C.indexOf("ibm") !== 0) {
				B = jQuery("li#ibm-home-tab-my-ibm > a:first");
				if (B.length < 1) {
					return
				}
				if (!A.restoreActiveTabFromCookie()) {
					A.setActive(B.get(0), B.attr("href").replace(/#/, ""))
				}
			}
		},
		getLastTabCookie : function() {
			var E = -1, B = "", D = document.cookie, C = 0, F = "hplasttab=";
			E = D.indexOf(F);
			if (E < 0) {
				return null
			}
			C = E + F.length;
			B = D.substring(C).replace(/;.*$/, "");
			return decodeURIComponent(B)
		},
		restoreActiveTabFromCookie : function() {
			var C, B, D = A.getLastTabCookie();
			if (D !== null) {
				B = document.getElementById(D);
				if (B !== null) {
					C = B.getElementsByTagName("a");
					if (C.length > 0) {
						C = C.item(0);
						D = C.getAttribute("href").replace(/^[^#]*#+/, "");
						A.setActive(C, D);
						return true
					}
				}
			}
			return false
		},
		setLastTabCookie : function(C) {
			try {
				if (document.cookie.split(";").length < 17
						|| document.cookie.indexOf("hplasttab=") != -1) {
					document.cookie = "hplasttab=" + encodeURIComponent(C)
				}
			} catch (B) {
			}
		}
	};
	window.ibmHomePageTabs = A;
	window.IBMHomePageTabs = A
})();