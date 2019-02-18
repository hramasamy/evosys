/**
 * To use it, need the following Calendar.html to hold it
 * <html>
 *  <head>
 *  <script src="Calendar.js"></script>
 *  <script>
 *  var calendar = new Calendar(new Date());
 *  </script>
 *  </head>
 *  <body onload="calendar.updateUI()">
 *  </body>
 *  </html>
 * the opener : window.open("Calendar.html?dateListener=anId", "calendar", "height=200,width=400,status=no,toolbar=no,menubar=no,location=no");
 */

function Calendar(aDate) {
    var MONTH_NAMES = ["January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"];
    var LAST_DAYS = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    var WEEK_DAYS = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

    this.month = aDate.getMonth();
    this.year = aDate.getYear();

    this.selectDate = selectDate;
    this.changeMonth = changeMonth;
    this.changeYear = changeYear;
    this.toPreviousMonth = toPreviousMonth;
    this.toNextMonth = toNextMonth;

    this.getMonthSelect = getMonthSelect;
    this.getYearSelect = getYearSelect;
    this.getDateListenerId = getDateListenerId;

    this.toString = toString;
    this.updateUI = updateUI;

    function toString() {
        var result = ""
                + "<table border='0' bgcolor='#e6dae5'>"
                + "<tr>"
                    + "<td width=250>"
                        + "<font style='color: #b3853f;font-family: tahoma, verdana, arial, sans-serif;font-size: 9pt;font-style: normal;font-weight: bold;' text-decoration:none;>" + MONTH_NAMES[this.month] + " " + this.year + "</font></td>"
                    + "<td width=50% align=right></td></tr>"
                + "<tr>"
                    + "<td>"
                        + "<select style='color:#000000;font-family: tahoma, verdana, arial, sans-serif; font-size:8pt;font-style:normal; font-weight:normal;' id='monthSelect' onChange='calendar.changeMonth();'>";

        for (var i = 0; i < 12; i++) {
            result +=         "<option value='" + i + "' " + (i == this.month ? "selected" : "") + ">" + MONTH_NAMES[i] + "</option>";
        }

        result +=          "</select>"
                        + "<select style='color:#000000;font-family: tahoma, verdana, arial, sans-serif; font-size:8pt;font-style:normal; font-weight:normal;' id='yearSelect' onChange='calendar.changeYear();'>";

        for (var i = 2000; i < 2010; i++) {
            result +=         "<option value='" + i + "' " + (i == this.year ? "selected" : "") + ">" + i + "</option>";
        }

        result +=          "</select></td></tr>"
                + "<tr>"
                    + "<td colspan=2>";

        var firstDay = new Date(this.year, this.month, 1);
        var startDay = firstDay.getDay();

        LAST_DAYS[1] = ((this.year % 4 == 0) && (this.year % 100 != 0)) || (this.year % 400 == 0) ? 29 : 28;

        result +=         "<table cellspacing=1 cellpadding=0 border=0 bgcolor='#999999'>"
                              + "<tr>";

        for (var i = 0; i < 7; i++) {
            result +=               "<td bgcolor='#719eda' width=30 align=center valign=middle><font size=-1 color=#000000 face=tahoma><b>" + WEEK_DAYS[i] + "</b></font></td>";
        }

        result +=               "</tr>"
                              + "<tr align=center valign=middle>";

        var column = 0;
        var lastMonth = this.month - 1;
        if (lastMonth == -1) {
            lastMonth = 11;
        }

        for (var i = 0; i < startDay; i++, column++) {
            result +=               "<td bgcolor=#ffffff width=30 height=20><font size=-1 color=#ffffff face=tahoma>" + (LAST_DAYS[lastMonth] - startDay + i + 1) + "</font></td>";
        }

        for (i = 1; i <= LAST_DAYS[this.month]; i++, column++) {
            result +=               "<td bgcolor=#ffffff width=30 height=20><a href='javascript:window.calendar.selectDate(" + i + ");void(0);'><font size=-1 face=tahoma color=#000000'>" + i + "</font></a>" + "</td>";
            if (column == 6) {
                result +=       "</tr>"
                              + "<tr align=center valign=middle>";
                column = -1;
            }
        }

        if (column > 0) {
            for (i=1; column<7; i++, column++) {
                result +=           "<td bgcolor=#ffffff width=30 height=20><font size=-1 color=#ffffff face=tahoma>" + i + "</font></td>";
            }
        }

        result +=               "</tr>"
                              + "<tr align=center valign=middle>"
                                  + "<td bgcolor=#eeeeee colspan=7><font size=-1 color=#0000ff face=tahoma><a href='javascript:calendar.toPreviousMonth()'><- </a> &nbsp;&nbsp; <a href='javascript:calendar.toNextMonth()'>-></a> </font></td>"
                              + "</tr>"
                        + "</table>"
                    + "</td>"
                + "</tr>"
                + "</table>";

        return result;
    }

    function selectDate(day) {
        var dateListener = window.opener.document.getElementById(this.getDateListenerId());
        if (dateListener) {
            dateListener.renderer.dateSelected(this.year, this.month, day);
        }

        window.close();
    }

    function changeMonth() {
        this.month = this.getMonthSelect().value;
        this.updateUI();
    }

    function changeYear() {
        this.year = this.getYearSelect().value;
        this.updateUI();
    }

    function toPreviousMonth() {
        var monthSelect = this.getMonthSelect();
        var yearSelect = this.getYearSelect();

        if (monthSelect.selectedIndex == 0) {
            this.month = 11;
            this.year--;
        }  else {
            this.month--;
        }

        this.updateUI();
    }

    function toNextMonth() {
        var monthSelect = this.getMonthSelect();
        var yearSelect = this.getYearSelect();

        if (monthSelect.selectedIndex == 11) {
            this.month = 0;
            this.year++;
        } else {
            this.month++;
        }

        this.updateUI();
    }

    function getMonthSelect() {
        return window.document.getElementById("monthSelect");
    }

    function getYearSelect() {
        return window.document.getElementById("yearSelect");
    }

    function updateUI() {
        window.document.body.innerHTML = this.toString();
    }

    function getDateListenerId() {
        var result = StringUtils.getParameter(window.location.href, "dateListener") || "dateListener";
        result = result.replace(/%20/g, " ");
        return result;
    }
}