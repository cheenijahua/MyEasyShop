package exmaple.easyshop.model;

/**
 * Created by Administrator on 2016/11/23.
 */
//bom云服务器接口测试
//"createdAt": YYYY-mm-dd HH:ii:ss,    // 用户注册时间
//        "objectId": objectId,                // 用户唯一Id
//        "sessionToken": sessionToken         // 用来认证更新或删除用户的请

public class Result {
    private String createdAt,objectId,sessionToken;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    @Override
    public String toString() {
        return "Result{" +
                "createAt='" + createdAt + '\'' +
                ", objectId='" + objectId + '\'' +
                ", sessionToken='" + sessionToken + '\'' +
                '}';
    }
}
