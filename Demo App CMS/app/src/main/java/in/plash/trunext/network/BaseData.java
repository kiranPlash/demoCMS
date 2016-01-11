package in.plash.trunext.network;


import in.plash.trunext.Constants.Constants;

public class BaseData {
    private String api;
    private String url = Constants.SERVER_URL_DEV2;
    private Object headerObj;
    private Object RequestBody;


    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public Object getHeaderObj() {
        return headerObj;
    }

    public void setHeaderObj(Object headerObj) {
        this.headerObj = headerObj;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getRequestBody() {
        return RequestBody;
    }

    public void setRequestBody(Object requestBody) {
        RequestBody = requestBody;
    }
}

