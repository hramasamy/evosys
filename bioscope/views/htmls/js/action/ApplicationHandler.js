function ApplicationHandler(name) {
    this.name = name;

    this.initialize = initialize;
    this.getMapTabs = getMapTabs;
    this.changePassword = changePassword;
    this.changeRole = changeRole;
    this.logout = logout;
    this.finalize = finalize;

    function initialize() {
        var mapTabs = this.getMapTabs();
        if (mapTabs) {
            HtmlUtils.findWindowObject("changeRoleLink").style.display = "inline";
        } else {
            setTimeout(this.name + ".initialize()", 2000);
        }
    }

    function getMapTabs() {
        return HtmlUtils.findWindowObject('mapTabs');
    }

    function changePassword() {
        window.open(window.contextPath + "/map/mapEvent.do?userAction=changePassword&ts=" + new Date().getTime(), "ChagePassword", "width=300,height=200");
    }

    function changeRole() {
        this.donotLogout = true;
        window.top.location.href = window.contextPath + "/cogencyAction.do?forward=mainLayout&ts=" + new Date().getTime();
    }

    function logout() {
        this.donotLogout = true;
        window.top.location.href = window.contextPath + "/user/userEvent.do?userAction=logoff&path=/pages/user/logon.jsp?ts=" + new Date().getTime();
    }

    function finalize() {
        if (this.popupWindow && !this.popupWindow.closed && this.popupWindow.location.href.search(/reservedpopup\.html$/) != -1) {
            this.popupWindow.close();
        }

        // Do not log out unexpected
        if (!this.donotLogout) {
            this.logout();
        }
    }
}