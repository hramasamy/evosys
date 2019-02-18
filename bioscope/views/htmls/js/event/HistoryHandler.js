function HistoryHandler() {
    this.getMenuHolder = getMenuHolder;

    this.displayMenu = displayMenu;
    this.hideMenu = hideMenu;
    this.clear = clear;
    this.getNotificationsHolder = getNotificationsHolder;
    this.setNotifications = setNotifications;
    this.debug = debug;

    function getMenuHolder() {
        return window.document.getElementById("notificationsMenu");
    }

    function displayMenu() {
        var menuHolder = this.getMenuHolder();
        menuHolder.style.display = "inline"

        menuHolder.style.left = window.event.clientX;
        menuHolder.style.top = window.event.clientY;
    }

    function hideMenu() {
        this.getMenuHolder().style.display = "none";
    }

    function clear() {
        this.setNotifications("", true);
        this.hideMenu();
    }

    function getNotificationsHolder() {
        return window.document.getElementById("notifications");
    }

    function setNotifications(notifications, overwrite) {
        var notificationsHolder = this.getNotificationsHolder();
        var currentValue = HtmlUtils.getValue(notificationsHolder) + "\r\n";

        if (overwrite) {
            currentValue = "";
        }

        HtmlUtils.setValue(notificationsHolder, currentValue + notifications);
    }

    function debug(info, overwrite) {
        this.setNotifications("<br>" + info, overwrite);
    }
}