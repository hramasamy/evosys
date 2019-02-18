function MapEventListener(name) {
    this.name = name;

    var CHECK_UPDATE_INTERVAL = 1000;
    var CHECK_UPDATE_RESPONSE_INTERVAL = 1000;
    var HTTP_RESPONSE_READY_STATE = 4;

    this.updateUrl = null;
    this.eventFrameName = null;

    this.scheduleCheckUpdate = scheduleCheckUpdate;
    this.scheduleCheckUpdateResponse = scheduleCheckUpdateResponse;
    this.checkUpdate = checkUpdate;
    this.checkUpdateResponse = checkUpdateResponse;
    this.abort = abort;

    function scheduleCheckUpdate() {
        window.setTimeout(this.name + ".checkUpdate()", CHECK_UPDATE_INTERVAL);
    }

    function scheduleCheckUpdateResponse() {
        window.setTimeout(this.name + ".checkUpdateResponse()", CHECK_UPDATE_RESPONSE_INTERVAL);
    }

    function checkUpdate() {
        this.xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        this.xmlHttp.open("GET",this.updateUrl, true);
        this.xmlHttp.send();

        this.scheduleCheckUpdateResponse();
    }

    function checkUpdateResponse() {
        if (this.xmlHttp.readyState == HTTP_RESPONSE_READY_STATE) {
            var eventFrameDocument = window.frames[this.eventFrameName].document;
            eventFrameDocument.write(this.xmlHttp.responseText);
            eventFrameDocument.close();

            this.scheduleCheckUpdate();
        } else {
            this.scheduleCheckUpdateResponse();
        }
    }

    function abort() {
        if (this.xmlHttp) {
            this.xmlHttp.abort();
        }
    }
}
