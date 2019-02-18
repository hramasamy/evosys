function debug(aString) {
    var debugWnd = window.open("", "debug");
    debugWnd.document.writeln("<br>" + aString);
}